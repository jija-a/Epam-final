<!DOCTYPE html>

<html lang="en">
<head>

    <title><fmt:message key="head.title.project_name"/> | <fmt:message key="head.title.users_list"/></title>

</head>

<body class="d-flex flex-column h-100">

<%@include file="../jspf/header.jspf" %>

<main class="flex-shrink-0">
    <div class="container-fluid">
        <form action="<c:url value="/controller"/>" id="searchForm" method="get"></form>
        <input type="hidden" name="command" value="show_lessons" form="searchForm">
        <%@include file="../jspf/error-success.jspf" %>
        <table class="table caption-top table-bordered table-hover">
            <caption><fmt:message key="head.title.courses_list"/></caption>
            <thead>
            <tr>
                <th scope="col">#</th>
                <th scope="col">title</th>
                <th scope="col">start</th>
                <th scope="col">end</th>
                <th scope="col">action</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${lessons}" var="lesson" varStatus="st">
                <tr>
                    <%@include file="../jspf/teacher-lesson-buttons.jspf" %>
                </tr>
            </c:forEach>
            <tr>
                <%@include file="../jspf/teacher-lesson-create.jspf" %>
            </tr>
            </tbody>
        </table>
        <%@include file="../jspf/pagination.jspf" %>
    </div>
</main>

<script src="${pageContext.request.contextPath}/js/timeZone.js"></script>
<%@include file="../jspf/footer.jspf" %>

</body>
</html>
