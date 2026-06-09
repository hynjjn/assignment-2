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

        // No selection: send the user back to the poll.
        if (fruit == null || fruit.isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/opinion-poll.html");
            return;
        }

        try {
            dao.addVote(fruit);

            // [5] 없는 과일 이름이 들어왔을 때 처리:
            //     addVote()는 행이 수정됐으면 true, 아니면 false 를 반환한다.
            //     아래처럼 반환값을 받아서 false면 투표 화면으로 되돌려보낸다.
            //     (위 dao.addVote(fruit); 한 줄은 주석 처리하고 아래로 교체)
            // boolean ok = dao.addVote(fruit);
            // if (!ok) {
            //     response.sendRedirect(request.getContextPath() + "/opinion-poll.html");
            //     return;
            // }
        } catch (SQLException e) {
            throw new ServletException("Failed to record vote", e);
        }

        showResults(request, response, fruit);
    }
}
