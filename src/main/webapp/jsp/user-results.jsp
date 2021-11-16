<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="page_content"/>
<html>
<head>
    <title>Testing | Results</title>
    <%@include file="/jsp/jspf/head.jspf" %>
</head>
<body>
<%@include file="/jsp/jspf/header.jspf" %>

<div class="container" style="padding-top: 60px">
    <c:if test="${not empty results}">
        <h1 class="text-center">${user.login} results</h1>
        <table class="table table-hover table-striped table-bordered">
            <thead>
            <tr>
                <th scope="col">Id</th>
                <th scope="col">Test title</th>
                <th scope="col">Your score</th>
                <th scope="col">Max score</th>
                <th scope="col">Test started</th>
                <th scope="col">Test ended</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${results}" var="result">
                <tr>
                    <td>${result.id}</td>
                    <td>${result.test.title}</td>
                    <td>${result.score}</td>
                    <td>${result.test.maxScore}</td>
                    <td>${result.testStarted}</td>
                    <td>${result.testEnded}</td>
                </tr>
            </c:forEach>
            </tbody>
        </table>

    </c:if>

    <c:if test="${empty results}">
        <h1 align="center">You haven't passed any test yet</h1>
    </c:if>
</div>
</body>
</html>
