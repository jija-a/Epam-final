<!DOCTYPE html>

<html lang="en">
<head>

    <title><fmt:message key="head.title.project_name"/> | <fmt:message key="head.title.users_list"/></title>

</head>

<body class="d-flex flex-column h-100">

<%@include file="../jspf/header.jspf" %>

<main class="flex-shrink-0">
    <div class="container-fluid">
        <%@include file="../jspf/error-success.jspf" %>
        <c:choose>
            <c:when test="${not empty courses}">
                <form action="<c:url value="/controller"/>" id="searchForm" method="get"></form>
                <c:choose>
                    <c:when test="${user.role.id == 1}">
                        <input type="hidden" name="command" value="show_teacher_courses" form="searchForm">
                    </c:when>
                    <c:otherwise>
                        <input type="hidden" name="command" value="show_courses" form="searchForm">
                        <%@include file="../jspf/search.jspf" %>
                    </c:otherwise>
                </c:choose>

                <table class="table caption-top table-bordered table-hover">
                    <caption><fmt:message key="head.title.courses_list"/></caption>
                    <thead>
                    <tr>
                        <th scope="col">#</th>
                        <th scope="col"><fmt:message key="label.course.name"/></th>
                        <th scope="col"><fmt:message key="label.course.owner.name"/></th>
                        <th scope="col"><fmt:message key="label.course.type"/></th>
                        <th scope="col"><fmt:message key="label.action"/></th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="course" varStatus="st" items="${courses}">
                        <tr>
                            <form action="<c:url value="/controller"/>" method="post">
                                <input type="hidden" name="command" value="update_course">
                                <input type="hidden" name="course_id" value="${course.id}">
                                <th scope="row">${st.count}</th>
                                <td>
                                    <c:choose>
                                        <c:when test="${user.role.id == 1}">
                                            <input type="text" maxlength="150" minlength="5" value="${course.name}"
                                                   name="course_name">
                                        </c:when>
                                        <c:otherwise>
                                            <c:out value="${course.name}"/>
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                                <td><c:out value="${course.owner.firstName} ${course.owner.lastName}"/></td>
                                <td>
                                    <c:choose>
                                        <c:when test="${user.role.id == 1}">
                                            <%@include file="../jspf/teacher-category-select.jspf" %>
                                        </c:when>
                                        <c:otherwise>
                                            <c:out value="${course.category.name}"/>
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                                <td>
                                    <c:choose>
                                        <%--ADMIN--%>
                                        <c:when test="${user.role.id == 0}">
                                            <%@include file="../jspf/admin-course-buttons.jspf" %>
                                        </c:when>
                                        <%--TEACHER--%>
                                        <c:when test="${user.role.id == 1}">
                                            <%@include file="../jspf/teacher-course-buttons.jspf" %>
                                        </c:when>
                                        <%--STUDENT--%>
                                        <c:when test="${user.role.id == 2}">
                                            <%@include file="../jspf/student-course-buttons.jspf" %>
                                        </c:when>
                                    </c:choose>
                                </td>
                            </form>
                        </tr>
                    </c:forEach>
                    <tr>
                        <c:if test="${user.role.id == 1}">
                            <%@include file="../jspf/teacher-course-creation.jspf" %>
                        </c:if>
                    </tr>
                    </tbody>
                </table>
                <div class="container">
                    <nav aria-label="Page navigation example">
                        <%@include file="../jspf/pagination.jspf" %>
                    </nav>
                </div>
            </c:when>
            <c:otherwise>
                <c:choose>
                    <c:when test="${user.role.id == 1}">
                        <table class="table caption-top">
                            <caption><fmt:message key="head.title.courses_list"/></caption>
                            <thead>
                            <tr>
                                <th scope="col">#</th>
                                <th scope="col"><fmt:message key="label.course.name"/></th>
                                <th scope="col"><fmt:message key="label.course.owner.name"/></th>
                                <th scope="col"><fmt:message key="label.course.type"/></th>
                                <th scope="col"><fmt:message key="label.action"/></th>
                            </tr>
                            </thead>
                            <tbody>
                            <tr>
                                <%@include file="../jspf/teacher-course-creation.jspf" %>
                            </tr>
                            </tbody>
                        </table>
                    </c:when>
                    <c:otherwise>
                        <h1 class="text-center"><fmt:message key="title.courses.not.found"/></h1>
                    </c:otherwise>
                </c:choose>
            </c:otherwise>
        </c:choose>
    </div>
</main>

<%@include file="../jspf/footer.jspf" %>

</body>
</html>
