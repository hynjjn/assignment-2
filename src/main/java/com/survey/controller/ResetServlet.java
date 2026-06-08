package com.survey.controller;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Handles the "RESET" link (GET): re-initialises every fruit to the original
 * seed (1 vote each → 25%), then redirects to the outcome page so the user sees
 * the restored state. Uses redirect-after-action so a page refresh does not
 * reset the poll again.
 */

// ResetServlet: 사용자가 RESET을 누르면, Reset DAO를 call 한 후 /outcome으로 redirect
@WebServlet("/reset")
public class ResetServlet extends SurveyServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            dao.resetVotes();
        } catch (SQLException e) {
            throw new ServletException("Failed to reset survey", e);
        }
        response.sendRedirect(request.getContextPath() + "/outcome");
    }
}
