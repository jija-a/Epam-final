<!DOCTYPE html>
<html lang="en">
<head>
    <title><fmt:message key="head.title.project_name"/> | <fmt:message key="head.title.users_list"/></title>
</head>
<c:if test="${not empty sessionScope.course}">
    <c:remove var="course" scope="session"/>
</c:if>
<body class="d-flex flex-column h-100">
<%@include file="jspf/header.jspf" %>
<main class="flex-shrink-0">
    <h1 class="text-center"><fmt:message key="head.title.users_list"/></h1>
    <div class="container">
        <%@include file="jspf/error-success.jspf" %>
        <form action="<c:url value="/controller"/>" id="searchForm" method="get">
            <input type="hidden" name="command" value="show_course_users">
            <%@include file="jspf/search.jspf" %>
            <c:choose>
                <c:when test="${not empty course_users}">
                    <table class="table caption-top table-bordered table-hover">
                        <caption><fmt:message key="head.title.users_list"/></caption>
                        <thead>
                        <tr>
                            <th scope="col">#</th>
                            <th scope="col"><fmt:message key="label.login"/></th>
                            <th scope="col"><fmt:message key="label.name"/></th>
                            <th scope="col"><fmt:message key="label.rating"/></th>
                            <th scope="col"><fmt:message key="label.attendance"/>, %</th>
                            <th scope="col"><fmt:message key="label.action"/></th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach var="courseUser" varStatus="st" items="${course_users}">
                            <tr>
                                <th scope="row">
                                    <%@include file="jspf/entity-number.jspf" %>
                                </th>
                                <td><c:out value="${courseUser.user.login}"/></td>
                                <td><c:out value="${courseUser.user.firstName} ${courseUser.user.lastName}"/></td>
                                <td><c:out value="${courseUser.rating}"/></td>
                                <td><c:out value="${courseUser.attendancePercent}"/></td>
                                <td>
                                    <%@include file="jspf/teacher-course-users-buttons.jspf" %>
                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </c:when>
                <c:otherwise>
                    <h1 class="text-center text-muted"><fmt:message key="error.not.found"/></h1>
                </c:otherwise>
            </c:choose>
            <div class="container">
                <nav aria-label="Page navigation example">
                    <%@include file="jspf/pagination.jspf" %>
                </nav>
            </div>
        </form>
    </div>
</main>
<%@include file="jspf/footer.jspf" %>
</body>
</html>
