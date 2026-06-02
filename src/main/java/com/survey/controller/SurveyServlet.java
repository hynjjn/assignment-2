package com.survey.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.survey.model.Fruit;
import com.survey.model.FruitDAO;

/**
 * Shared controller behaviour: load the poll results, compute each fruit's
 * percentage, stash everything on the request, and forward to the result view.
 */
public abstract class SurveyServlet extends HttpServlet {

    protected final FruitDAO dao = new FruitDAO();

    private static final String RESULT_VIEW = "/WEB-INF/views/result.jsp";

    /**
     * Reads current results, computes percentages relative to the total, and
     * forwards to result.jsp.
     *
     * @param chosen the fruit the user just voted for, or null when only viewing
     */
    protected void showResults(HttpServletRequest request, HttpServletResponse response,
                               String chosen)
            throws ServletException, IOException {
        try {
            List<Fruit> fruits = dao.findAllOrderByVotesDesc();

            int total = 0;
            for (Fruit f : fruits) {
                total += f.getVotes();
            }
            for (Fruit f : fruits) {
                int pct = (total == 0) ? 0 : Math.round(f.getVotes() * 100f / total);
                f.setPercentage(pct);
            }

            request.setAttribute("fruits", fruits);
            request.setAttribute("total", total);
            request.setAttribute("chosen", chosen);

            request.getRequestDispatcher(RESULT_VIEW).forward(request, response);

        } catch (SQLException e) {
            throw new ServletException("Failed to load survey results", e);
        }
    }
}
