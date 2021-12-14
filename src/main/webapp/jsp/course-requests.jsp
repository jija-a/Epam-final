<!DOCTYPE html>
<html lang="en">
<head>
    <title><fmt:message key="head.title.project_name"/> | <fmt:message key="head.title.course_requests"/></title>
</head>
<body class="d-flex flex-column h-100">
<%@include file="jspf/header.jspf" %>
<main class="flex-shrink-0">
    <h1 class="text-center"><fmt:message key="head.title.course_requests"/></h1>
    <div class="container">
        <%@include file="jspf/error-success.jspf" %>
        <form action="<c:url value="/controller"/>" id="searchForm" method="get"></form>
        <c:choose>
            <c:when test="${user.role.id == 1}">
                <c:choose>
                    <c:when test="${not empty users}">
                        <%@include file="jspf/teacher-course-requests.jspf" %>
                    </c:when>
                    <c:otherwise>
                        <h1 class="text-center text-muted"><fmt:message key="error.not.found"/></h1>
                    </c:otherwise>
                </c:choose>
            </c:when>
            <c:when test="${user.role.id == 2}">
                <c:choose>
                    <c:when test="${not empty courses}">
                        <%@include file="jspf/student-course-requests.jspf" %>
                    </c:when>
                    <c:otherwise>
                        <h1 class="text-center text-muted"><fmt:message key="error.not.found"/></h1>
                    </c:otherwise>
                </c:choose>
            </c:when>
        </c:choose>
    </div>
</main>
<%@include file="jspf/footer.jspf" %>
</body>
</html>
