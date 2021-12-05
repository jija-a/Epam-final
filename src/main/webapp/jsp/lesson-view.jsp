<!DOCTYPE html>

<html lang="en">
<head>

    <title><fmt:message key="head.title.project_name"/> | <fmt:message key="label.view.lessons"/></title>

</head>

<body class="d-flex flex-column h-100">

<%@include file="jspf/header.jspf" %>

<main class="flex-shrink-0">
    <div class="container">
        <form action="<c:url value="/controller"/>" id="searchForm" method="get"></form>
        <%@include file="jspf/error-success.jspf" %>
        <c:choose>
            <c:when test="${user.role.id == 1}">
                <%@include file="jspf/teacher-lesson-buttons.jspf" %>
            </c:when>
            <c:when test="${user.role.id == 2}">
                <%@include file="jspf/student-lesson-buttons.jspf" %>
            </c:when>
        </c:choose>
        <%@include file="jspf/pagination.jspf" %>
    </div>
</main>

<script src="${pageContext.request.contextPath}/js/timeZone.js"></script>
<%@include file="jspf/footer.jspf" %>

</body>
</html>
