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
        <form action="<c:url value="/controller"/>" id="searchForm"></form>
        <input type="hidden" name="command" value="show_tests" form="searchForm">
        <table class="table caption-top table-bordered table-hover">
            <caption>
                <fmt:message key="head.title.users_list"/>
            </caption>
            <thead>
            <tr>
                <th scope="col">#</th>
                <th scope="col"><fmt:message key="label.question"/></th>
                <th scope="col"><fmt:message key="label.answers"/></th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="question" items="${result.test.questions}" varStatus="st">
                <tr>
                    <td>${st.count}</td>
                    <td><p align="left">${question.title}</p></td>
                    <td>
                        <%@include file="../jspf/student-results-answers.jspf" %>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</main>

<%@include file="../jspf/footer.jspf" %>

</body>
</html>
