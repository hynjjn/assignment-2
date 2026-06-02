<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>JSP를 이용한 여론조사</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
    <div class="card">
        <h2 class="outcome-title">Survey Outcome</h2>

        <div class="chart">
            <c:forEach var="fruit" items="${fruits}">
                <div class="row">
                    <span class="label label-${fn:toLowerCase(fruit.name)}">
                        ${fruit.name}:
                    </span>
                    <span class="pct">${fruit.percentage}%</span>
                    <span class="bar-track">
                        <span class="bar" style="width: ${fruit.percentage}%;"></span>
                    </span>
                </div>
            </c:forEach>
        </div>

        <c:if test="${not empty chosen}">
            <p class="chose">You chose <span class="chosen-name">${chosen}</span>.</p>
        </c:if>
        <p class="count">${total} participant(s) join the survey.</p>

        <p class="home"><a href="opinion.html">Home</a></p>
    </div>

    <div class="ground"></div>
</body>
</html>
