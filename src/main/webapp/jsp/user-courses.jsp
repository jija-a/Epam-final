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
        <form action="<c:url value="/controller"/>" id="searchForm" method="get"></form>
        <input type="hidden" name="command" value="show_student_courses" form="searchForm">
        <c:choose>
            <c:when test="${not empty course_user}">
                <table class="table caption-top table-bordered table-hover">
                    <caption><fmt:message key="head.title.courses_list"/></caption>
                    <thead>
                    <tr>
                        <th scope="col">#</th>
                        <th scope="col"><fmt:message key="label.course.name"/></th>
                        <th scope="col"><fmt:message key="label.course.owner.name"/></th>
                        <th scope="col"><fmt:message key="label.course.type"/></th>
                        <th scope="col"><fmt:message key="label.rating"/></th>
                        <th scope="col"><fmt:message key="label.attendance"/></th>
                        <th scope="col"><fmt:message key="label.action"/></th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="course_user" varStatus="st" items="${course_user}">
                        <tr>
                            <th scope="row">
                                <%@include file="jspf/entity-number.jspf" %>
                            </th>
                            <td><c:out value="${course_user.course.name}"/></td>
                            <td><c:out
                                    value="${course_user.course.owner.firstName} ${course_user.course.owner.lastName}"/></td>
                            <td><c:out value="${course_user.course.category.name}"/></td>
                            <td>
                                <c:choose>
                                    <c:when test="${course_user.rating eq null}">
                                        -
                                    </c:when>
                                    <c:otherwise>
                                        <c:out value="${course_user.rating}"/>
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <td>
                                <c:choose>
                                    <c:when test="${course_user.rating eq null}">
                                        -
                                    </c:when>
                                    <c:otherwise>
                                        <c:out value="${course_user.attendancePercent}"/>
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <td>
                                <div class="btn-group" role="group" aria-label="Basic mixed styles example">
                                    <a href="<c:url value="/controller?command=show_course_lessons&course_id=${course_user.course.id}"/>"
                                       class="btn btn-primary" type="submit">
                                        <fmt:message key="label.view.lessons"/></a>
                                    <a href="<c:url value="/controller?command=leave_course&course_id=${course_user.course.id}"/>"
                                       class="btn btn-danger" type="submit">
                                        <fmt:message key="label.leave"/></a>
                                </div>
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
    </div>
</main>
<%@include file="jspf/footer.jspf" %>
</body>
</html>
