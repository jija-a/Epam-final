<!DOCTYPE html>

<html lang="en">
<head>

    <title><fmt:message key="head.title.project_name"/> | <fmt:message key="head.title.users_list"/></title>

</head>

<c:if test="${not empty sessionScope.course}">
    <c:remove var="course" scope="session"/>
</c:if>

<body class="d-flex flex-column h-100">

<%@include file="../jspf/header.jspf" %>

<main class="flex-shrink-0">
    <div class="container-fluid">
        <%@include file="../jspf/error-success.jspf"%>
        <c:choose>
            <c:when test="${not empty users}">
                <form action="<c:url value="/controller"/>" id="searchForm">
                    <input type="hidden" name="command" value="show_course_users">
                    <input type="hidden" name="course_id" value="${course.id}">
                    <%@include file="../jspf/search.jspf" %>
                    <table class="table caption-top table-bordered table-hover">
                        <caption><fmt:message key="head.title.users_list"/></caption>
                        <thead>
                        <tr>
                            <th scope="col">#</th>
                            <th scope="col"><fmt:message key="label.login"/></th>
                            <th scope="col"><fmt:message key="label.name"/></th>
                            <th scope="col"><fmt:message key="label.role"/></th>
                            <th scope="col"><fmt:message key="label.action"/></th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach var="student" varStatus="st" items="${users}">
                            <tr>
                                <th scope="row">${st.count}</th>
                                <td><c:out value="${student.login}"/></td>
                                <td><c:out value="${student.firstName} ${student.lastName}"/></td>
                                <td><c:out value="${student.role.name}"/></td>
                                <td>
                                        <%--TEACHER--%>
                                    <c:if test="${user.role.id == 1 && course.owner.id == sessionScope.user.id}">
                                        <%@include file="../jspf/teacher-course-users-buttons.jspf" %>
                                    </c:if>
                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                    <div class="container">
                        <nav aria-label="Page navigation example">
                            <%@include file="../jspf/pagination.jspf" %>
                        </nav>
                    </div>
                </form>
            </c:when>
            <c:otherwise>
                <h1 class="text-center"><fmt:message key="title.users.not.found"/></h1>
            </c:otherwise>
        </c:choose>
    </div>
</main>

<%@include file="../jspf/footer.jspf" %>

</body>
</html>
