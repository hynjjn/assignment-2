package com.survey.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Handles the "OUTCOME" link (GET): shows current results without recording
 * a vote, so no fruit is highlighted as chosen.
 */
@WebServlet("/outcome")
public class OutcomeServlet extends SurveyServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        showResults(request, response, null);
    }
}
