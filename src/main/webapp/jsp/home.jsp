<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Testing | Home</title>
</head>
<body>
<jsp:include page="jspf/header.jspf"/>

<h1 align="center">Welcome!</h1>
<form action="<c:url value="/controller"/>" method="get">
    <input type="hidden" name="command" value="find_course_users">
    <input type="submit" value="Find course users">
    <input type="text" placeholder="Course name" name="course_name">
</form>
<jsp:include page="jspf/footer.jspf"/>
</body>
</html>
