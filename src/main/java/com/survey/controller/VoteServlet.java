package com.survey.controller;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Handles a submitted vote (POST from opinion-poll.html), records it, then
 * shows the result page highlighting the chosen fruit.
 */
@WebServlet("/vote")
public class VoteServlet extends SurveyServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String fruit = request.getParameter("fruit");

        if (fruit == null || fruit.isEmpty()) {
            // No selection: send the user back to the poll.
            response.sendRedirect(request.getContextPath() + "/opinion-poll.html");
            return;
        }

        try {
            dao.addVote(fruit);
        } catch (SQLException e) {
            throw new ServletException("Failed to record vote", e);
        }

        showResults(request, response, fruit);
    }
}
