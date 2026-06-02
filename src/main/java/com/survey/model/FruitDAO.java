package com.survey.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Data-access object for the {@code fruit} table. All SQL lives here so the
 * controllers never touch JDBC directly (MVC separation).
 */
public class FruitDAO {

    /** Returns all fruits ordered by vote count, highest first. */
    public List<Fruit> findAllOrderByVotesDesc() throws SQLException {
        String sql = "SELECT id, name, votes FROM fruit ORDER BY votes DESC, name ASC";
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

    /**
     * Adds one vote for the given fruit. A user may vote repeatedly, so this
     * simply increments the counter every call.
     *
     * @return true if a row was updated (i.e. the fruit name was valid)
     */
    public boolean addVote(String fruitName) throws SQLException {
        String sql = "UPDATE fruit SET votes = votes + 1 WHERE name = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, fruitName);
            return ps.executeUpdate() > 0;
        }
    }
}
