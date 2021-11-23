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
            <c:when test="${not empty users}">
                <form action="<c:url value="/controller"/>" id="searchForm" method="get"></form>
                <input type="hidden" name="command" value="show_requests" form="searchForm">

                <table class="table caption-top table-bordered table-hover">
                    <caption><fmt:message key="head.title.courses_list"/></caption>
                    <thead>
                    <tr>
                        <th scope="col">#</th>
                        <th scope="col"><fmt:message key="label.course.name"/></th>
                        <th scope="col"><fmt:message key="label.name"/></th>
                        <th scope="col"><fmt:message key="label.action"/></th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="courseUser" varStatus="st" items="${users}">
                        <tr>
                            <th scope="row">${st.count}</th>
                            <td>${courseUser.course.name}</td>
                            <td>${courseUser.user.firstName} ${courseUser.user.lastName}</td>
                            <td>
                                <a class="btn btn-success"
                                   href="<c:url value="/controller?command=accept_request&user_id=${courseUser.user.id}&course_id=${courseUser.course.id}"/>"><fmt:message
                                        key="label.accept"/></a>
                                <a class="btn btn-danger"
                                   href="<c:url value="/controller?command=decline_request&user_id=${courseUser.user.id}&course_id=${courseUser.course.id}"/>"><fmt:message
                                        key="label.decline"/></a>
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
            </c:when>
            <c:otherwise>
                <h1 class="text-center">No Requests</h1>
            </c:otherwise>
        </c:choose>
    </div>
</main>

<%@include file="../jspf/footer.jspf" %>

</body>
</html>
