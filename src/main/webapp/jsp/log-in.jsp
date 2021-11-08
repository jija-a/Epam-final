<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Testing | Log in</title>
    <%@include file="/jsp/jspf/head.jspf" %>
</head>
<body>
<%@include file="/jsp/jspf/header.jspf" %>
<jsp:useBean id="error" scope="request" class="java.lang.String"/>
<div class="login">
    <div class="login-triangle"></div>

    <h2 class="login-header">Sign In</h2>

    <form class="login-container" action="<c:url value="/controller"/>" method="get">
        <c:if test="${not empty error}">
            <h4 align="center" style="color: darkred"><c:out value="${error}"/></h4>
        </c:if>
        <p><input name="login" type="text" placeholder="Login"></p>
        <p><input name="password" type="password" placeholder="Password"></p>
        <p><input type="submit" value="SIGN IN"></p>
        <input type="hidden" name="command" value="login">
    </form>
</div>
</body>
</html>
