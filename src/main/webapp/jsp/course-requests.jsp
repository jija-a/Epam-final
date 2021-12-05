<!DOCTYPE html>
<html lang="en">
<head>
    <title><fmt:message key="head.title.project_name"/> | <fmt:message key="head.title.course_requests"/></title>
</head>
<body class="d-flex flex-column h-100">
<%@include file="jspf/header.jspf" %>
<main class="flex-shrink-0">
    <div class="container">
        <%@include file="jspf/error-success.jspf" %>
        <form action="<c:url value="/controller"/>" id="searchForm" method="get"></form>
        <c:choose>
            <c:when test="${user.role.id == 1}">
                <%@include file="jspf/teacher-course-requests.jspf" %>
            </c:when>
            <c:when test="${user.role.id == 2}">
                <%@include file="jspf/student-course-requests.jspf" %>
            </c:when>
        </c:choose>
    </div>
</main>
<%@include file="jspf/footer.jspf" %>
</body>
</html>
