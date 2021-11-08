<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Testing | Add users on course</title>
    <%@include file="/jsp/jspf/head.jspf" %>
</head>
<body>
<%@include file="/jsp/jspf/header.jspf" %>
<jsp:useBean id="error" scope="request" class="java.lang.String"/>
<c:if test="${not empty error}">
    <h4 style="color: darkred"><c:out value="${error}"/></h4>
</c:if>
<form action="<c:url value="/controller"/>" method="post">
    <input type="hidden" name="command" value="find_user">
    <label for="login">
        <input id="login" type="text" name="login" placeholder="test name">
    </label>
    <input type="submit" value="Find">
</form>
</body>
</html>
