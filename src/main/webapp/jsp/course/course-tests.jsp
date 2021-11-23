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
            <c:when test="${not empty tests}">
                <form action="<c:url value="/controller"/>" id="searchForm"></form>
                <input type="hidden" name="command" value="show_tests" form="searchForm">
                <input type="hidden" name="course_id" value="${course.id}" form="searchForm">
                <table class="table caption-top table-bordered table-hover">
                    <caption><fmt:message key="head.title.users_list"/></caption>
                    <thead>
                    <tr>
                        <th scope="col">#</th>
                        <th scope="col"><fmt:message key="label.title"/></th>
                        <th scope="col"><fmt:message key="label.attempts"/></th>
                        <th scope="col"><fmt:message key="label.date.start"/></th>
                        <th scope="col"><fmt:message key="label.date.end"/></th>
                        <th scope="col"><fmt:message key="label.time.to_answer"/></th>
                        <th scope="col"><fmt:message key="label.score.max"/></th>
                        <c:if test="${sessionScope.user.role.id == 1}">
                            <th scope="col"><fmt:message key="label.action"/></th>
                        </c:if>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="test" varStatus="st" items="${tests}">
                        <c:if test="${user.role.id == 1}">
                            <form>
                                <%@include file="../jspf/teacher-course-tests-view.jspf" %>
                            </form>
                        </c:if>
                        <c:if test="${user.role.id == 2}">
                            <%@include file="../jspf/student-course-tests-view.jspf" %>
                        </c:if>
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
                <h1 class="text-center">There is no tests on course</h1>
                <div class="row justify-content-md-center">
                    <div class="col col-lg-2">
                        <button class="btn btn-success">Create</button>
                    </div>
                </div>
            </c:otherwise>
        </c:choose>
    </div>
</main>

<%@include file="../jspf/footer.jspf" %>

</body>
</html>
