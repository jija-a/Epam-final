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
                            <th scope="row">${st.count}</th>
                            <td>
                                <c:out value="${course.name}"/>
                            </td>
                            <td><c:out value="${course.owner.firstName} ${course.owner.lastName}"/></td>
                            <td>
                                <c:out value="${course.category.name}"/>
                            </td>
                            <td>
                                <div class="btn-group" role="group" aria-label="Basic mixed styles example">
                                    <a class="btn btn-primary"
                                       href="<c:url value="/controller?command=show_available_tests&course_id=${course.id}"/>">
                                        <fmt:message key="label.view.tests"/></a>
                                    <a class="btn btn-danger"
                                       href="<c:url value="/controller?command=leave_course&course_id=${course.id}"/>">
                                        <fmt:message key="label.leave"/>
                                    </a>
                                </div>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </c:when>
        </c:choose>
    </div>
</main>

<%@include file="../jspf/footer.jspf" %>

</body>
</html>
