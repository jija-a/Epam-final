<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Testing | Sign Up</title>
    <%@include file="/jsp/jspf/head.jspf" %>
</head>
<body>
<%@include file="/jsp/jspf/header.jspf" %>

<div class="login">
    <div class="login-triangle"></div>

    <h2 class="login-header">Sign Up</h2>

    <form class="login-container" action="<c:url value="/controller"/>" method="post">
        <c:if test="${not empty error}">
            <h4 align="center" style="color: darkred"><c:out value="${error}"/></h4>
        </c:if>
        <input type="hidden" name="command" value="register_user">
        <p><input id="login" type="text" name="login" placeholder="login"></p>
        <p><input id="password" type="password" name="password" placeholder="password"></p>
        <p><input id="first_name" type="text" name="first_name" placeholder="first name"></p>
        <p><input id="last_name" type="text" name="last_name" placeholder="last name"></p>
        <hr>
        <label>
            <input type="radio" name="user_role" value="1">I am a teacher
            <input type="radio" name="user_role" value="2">I am a student
        </label>
        <hr>
        <input type="submit" value="Sign Up">
    </form>
</div>
</body>
</html>
