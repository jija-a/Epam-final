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
        <form action="<c:url value="/controller"/>" id="searchForm" method="get"></form>
        <input type="hidden" name="command" value="show_courses" form="searchForm">
        <%@include file="../jspf/search.jspf" %>

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
                            <c:out value="${course.name}"/>
                        </td>
                        <td><c:out value="${course.owner.firstName} ${course.owner.lastName}"/></td>
                        <td>
                            <c:out value="${course.category.name}"/>
                        </td>
                        <td>
                            <div class="btn-group" role="group" aria-label="Basic mixed styles example">
                                <a class="btn btn-primary"
                                   href="<c:url value="/controller?command=request_sign_on_course&course_id=${course.id}"/>">
                                    <fmt:message key="label.request"/></a>
                            </div>
                        </td>
                    </form>
                </tr>
            </c:forEach>
            </tbody>
        </table>
        <div class="container">
            <nav aria-label="Page navigation example">
                <%@include file="../jspf/pagination.jspf" %>
            </nav>
        </div>
    </div>
</main>

<%@include file="../jspf/footer.jspf" %>

</body>
</html>
