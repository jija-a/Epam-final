<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Testing | Home</title>
    <%@include file="/jsp/jspf/head.jspf" %>
</head>
<body>
<%@include file="/jsp/jspf/header.jspf" %>

<h1 align="center">Welcome!</h1>

<form action="<c:url value="/controller"/>" method="get">
    <input type="hidden" name="command" value="show_courses">
    <input type="text" placeholder="Course name" name="course_name">
    <input type="submit" class="block" value="Find courses">
</form>

<jsp:include page="jspf/footer.jspf"/>
</body>
</html>
