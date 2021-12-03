<!DOCTYPE html>

<html lang="en">
<head>

    <title><fmt:message key="head.title.project_name"/> | <fmt:message key="head.title.users_list"/></title>

</head>

<body class="d-flex flex-column h-100">

<%@include file="../jspf/header.jspf" %>

<main class="flex-shrink-0">
    <div class="container">
        <%@include file="../jspf/error-success.jspf" %>
        <c:choose>
            <c:when test="${not empty users}">
                <form action="<c:url value="/controller"/>" id="searchForm"></form>
                <input type="hidden" name="command" value="show_users" form="searchForm">
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
                    <c:forEach var="user" varStatus="st" items="${users}">
                        <tr>
                            <th scope="row">${st.count}</th>
                            <td><c:out value="${user.login}"/></td>
                            <td><c:out value="${user.firstName} ${user.lastName}"/></td>
                            <td><c:out value="${user.role.name}"/></td>
                            <td>
                                <c:if test="${sessionScope.user.role.id == 0}">
                                    <c:choose>
                                        <c:when test="${user.role.id == 0}">
                                            <a class="btn btn-danger disabled"
                                               href="#"><fmt:message
                                                    key="label.delete"/></a>
                                        </c:when>
                                        <c:otherwise>
                                            <a class="btn btn-danger"
                                               href="<c:url value="/controller?command=delete_user&user_id=${user.id}"/>"><fmt:message
                                                    key="label.delete"/></a>
                                        </c:otherwise>
                                    </c:choose>
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
