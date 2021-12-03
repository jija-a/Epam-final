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
            <c:when test="${not empty results}">
                <form action="<c:url value="/controller"/>" id="searchForm"></form>
                <input type="hidden" name="command" value="show_tests" form="searchForm">
                <table class="table caption-top table-bordered table-hover">
                    <caption>
                        <fmt:message key="head.title.users_list"/>
                    </caption>
                    <thead>
                    <tr>
                        <th scope="col">#</th>
                        <th scope="col"><fmt:message key="label.name"/></th>
                        <th scope="col"><fmt:message key="label.date.start"/></th>
                        <th scope="col"><fmt:message key="label.date.end"/></th>
                        <th scope="col"><fmt:message key="label.attempt"/></th>
                        <th scope="col"><fmt:message key="label.score"/></th>
                        <c:if test="${sessionScope.user.role.id == 1}">
                            <th scope="col"><fmt:message key="label.action"/></th>
                        </c:if>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="result" varStatus="st" items="${results}">
                        <tr>
                            <td>${st.count}</td>
                            <td>${result.user.lastName} ${result.user.firstName}</td>
                            <td><crt:formatDate value="${result.testEnded}" pattern="dd-MM-yyyy hh:mm"/></td>
                            <td>${result.test.timeToAnswer}</td>
                            <td>${result.attempt} / ${result.test.attempts}</td>
                            <td>${result.score}</td>
                            <c:if test="${result.attempt ne 0}">
                                <td><a class="btn btn-primary"
                                       href="<c:url value="/controller?command=show_student_answers&result_id=${result.id}"/>">
                                    Info
                                </a></td>
                            </c:if>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
                <%--<div class="container">
                    <nav aria-label="Page navigation example">
                        <%@include file="../jspf/pagination.jspf" %>
                    </nav>
                </div>--%>
            </c:when>
            <c:otherwise>
                <div class="row justify-content-md-center">
                    <h1 class="text-center">There is no test results</h1>
                </div>
            </c:otherwise>
        </c:choose>
    </div>
</main>

<%@include file="../jspf/footer.jspf" %>

</body>
</html>
