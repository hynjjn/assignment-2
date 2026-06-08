// FruitDAO - Data Access Object

package com.survey.model;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Data-access object for the {@code fruit} table. All SQL lives here so the
 * controllers never touch JDBC directly (MVC separation).
 */
public class FruitDAO {

    // Returns all fruits ordered by
    // 1. vote count (desc--high->low), if tied: 2. name (asc)
    public List<Fruit> findAllOrderByVotesDesc() throws SQLException {
        String sql = "SELECT id, name, votes FROM fruit ORDER BY votes DESC, name ASC";
        // String sql = "SELECT id, name, votes FROM fruit ORDER BY name ASC"; (이름순)
        List<Fruit> fruits = new ArrayList<>();

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                fruits.add(new Fruit(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getInt("votes")));
            }
        }
        return fruits;
    }

    
    // Adds one vote for the given fruit.
    // return true if a row was updated (i.e. the fruit name was valid)
    
    public boolean addVote(String fruitName) throws SQLException {
        String sql = "UPDATE fruit SET votes = votes + 1 WHERE name = ?";

        // prepared Statement -> placeholder, safe from SQL injection
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, fruitName);
            return ps.executeUpdate() > 0;
        }
    }

    // execute the schema.sql to reset
    public void resetVotes() throws SQLException {
        try (Connection conn = DBConnection.getConnection();
             Statement st = conn.createStatement()) {

            for (String statement : loadSchemaStatements()) {
                st.execute(statement);
            }
        }
    }

    // Reads schema.sql from the classpath -> send to splitStatements(): splits it into runnable statements.
    private List<String> loadSchemaStatements() throws SQLException {
        try (InputStream in = FruitDAO.class.getClassLoader()
                .getResourceAsStream("schema.sql")) {
            if (in == null) {
                throw new SQLException("schema.sql not found on classpath");
            }
            String script = new String(in.readAllBytes(), StandardCharsets.UTF_8);
            return splitStatements(script);
        } catch (IOException e) {
            throw new SQLException("Failed to read schema.sql", e);
        }
    }

    // Strips '--' line comments and splits the script on ';' into statements.
    // JDBC는 한 번에 하나의 statement만 실행 가능 -> splitStatements()로 5개 모두 실행
    private List<String> splitStatements(String script) {
        StringBuilder cleaned = new StringBuilder();
        for (String line : script.split("\\r?\\n")) {
            String trimmed = line.trim();
            if (trimmed.isEmpty() || trimmed.startsWith("--")) {
                continue;
            }
            cleaned.append(line).append('\n');
        }

        List<String> statements = new ArrayList<>();
        for (String part : cleaned.toString().split(";")) {
            if (!part.trim().isEmpty()) {
                statements.add(part.trim());
            }
        }
        return statements;
    }
}
