<!DOCTYPE html>
<html lang="en">
<head>
    <title><fmt:message key="head.title.project_name"/> | <fmt:message key="head.title.courses_list"/></title>
</head>
<body class="d-flex flex-column h-100">
<%@include file="jspf/header.jspf" %>
<main class="flex-shrink-0">
    <h1 class="text-center"><fmt:message key="head.title.courses_list"/></h1>
    <div class="container">
        <%@include file="jspf/error-success.jspf" %>
        <c:choose>
            <c:when test="${user.role.id == 0}">
                <form action="<c:url value="/controller?"/>" id="searchForm" method="get"></form>
                <input type="hidden" name="command" value="show_courses" form="searchForm">
                <%@include file="jspf/search.jspf" %>
                <c:choose>
                    <c:when test="${not empty courses}">
                        <%@include file="jspf/admin-course-buttons.jspf" %>
                    </c:when>
                    <c:otherwise>
                        <h1 class="text-muted text-center"><fmt:message key="error.not.found"/></h1>
                    </c:otherwise>
                </c:choose>
            </c:when>
            <c:when test="${user.role.id == 1}">
                <form action="<c:url value="/controller?"/>" id="searchForm"
                      method="get"></form>
                <input type="hidden" name="command" value="show_teacher_courses" form="searchForm">
                <%@include file="jspf/teacher-course-buttons.jspf" %>
            </c:when>
            <c:when test="${user.role.id == 2}">
                <form action="<c:url value="/controller?"/>" id="searchForm"
                      method="get"></form>
                <input type="hidden" name="command" value="show_available_courses" form="searchForm">
                <%@include file="jspf/search.jspf" %>
                <c:choose>
                    <c:when test="${not empty courses}">
                        <%@include file="jspf/student-course-buttons.jspf" %>
                    </c:when>
                    <c:otherwise>
                        <h1 class="text-muted text-center"><fmt:message key="error.not.found"/></h1>
                    </c:otherwise>
                </c:choose>
            </c:when>
        </c:choose>
        <div class="container">
            <nav aria-label="Page navigation example">
                <%@include file="jspf/pagination.jspf" %>
            </nav>
        </div>
    </div>
</main>
<%@include file="jspf/footer.jspf" %>
</body>
</html>
