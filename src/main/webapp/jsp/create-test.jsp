<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="page_content"/>
<html>
<head>
    <title>Testing | Create test</title>
    <%@include file="/jsp/jspf/head.jspf" %>
</head>
<body>
<%@include file="/jsp/jspf/header.jspf" %>
<jsp:useBean id="error" scope="request" class="java.lang.String"/>
<c:if test="${not empty error}">
    <h4 style="color: darkred"><c:out value="${error}"/></h4>
</c:if>
<form action="<c:url value="/controller"/>" method="post">
    <input type="hidden" name="command" value="create_test">
    <label for="login">
        <input id="login" type="text" name="login" placeholder="test name">
    </label>
    <label for="attempts">
        <input id="attempts" type="number" name="attempts" placeholder="attempts">
    </label>
    <input type="submit" value="create test">
</form>
</body>
</html>
