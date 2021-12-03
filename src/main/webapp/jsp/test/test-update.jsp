<!DOCTYPE html>

<html lang="en">
<head>

    <title><fmt:message key="head.title.project_name"/> | <fmt:message key="head.title.users_list"/></title>

</head>

<body class="d-flex flex-column h-100">

<%@include file="../jspf/header.jspf" %>

<main class="flex-shrink-0">
    <form action="<c:url value="/controller"/>" method="get">
        <div class="container-fluid">
            <div class="row justify-content-md-center">
                <div class="text-center">
                    <h1>${test.title}</h1>
                </div>
            </div>
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
                <c:forEach var="question" items="${test.questions}" varStatus="st">
                    <tr>
                        <td>${st.count}</td>
                        <td><textarea name="question_title">${question.title}</textarea></td>
                        <td>
                            <table class="table table-bordered table-hover">
                                <thead>
                                <tr>
                                    <th scope="col">Answer</th>
                                    <th scope="col">IsRight</th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:set var="rightQuestionsQty" scope="page" value="0"/>
                                <c:forEach items="${question.answers}" var="answer">
                                    <c:if test="${answer.isRight eq true}">
                                        <c:set var="rightQuestionsQty" value="${rightQuestionsQty + 1}"/>
                                    </c:if>
                                </c:forEach>
                                <c:forEach items="${question.answers}" var="answer">
                                    <tr>
                                        <td><input type="text" name="title" value="${answer.title}"/></td>
                                        <td>
                                            <c:choose>
                                                <c:when test="${rightQuestionsQty > 1}">
                                                    <c:choose>
                                                        <c:when test="${answer.isRight eq true}">
                                                            <input type="checkbox" name="is_right_${question.id}"
                                                                   checked="checked">
                                                        </c:when>
                                                        <c:otherwise>
                                                            <input type="checkbox" name="is_right_${question.id}">
                                                        </c:otherwise>
                                                    </c:choose>
                                                </c:when>
                                                <c:otherwise>
                                                    <c:choose>
                                                        <c:when test="${answer.isRight eq true}">
                                                            <input type="radio" name="is_right_${question.id}"
                                                                   checked="checked">
                                                        </c:when>
                                                        <c:otherwise>
                                                            <input type="radio" name="is_right_${question.id}">
                                                        </c:otherwise>
                                                    </c:choose>
                                                </c:otherwise>
                                            </c:choose>
                                        </td>
                                    </tr>
                                </c:forEach>
                                </tbody>
                            </table>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
            <div class="row justify-content-md-center">
                <div class="text-center">
                    <input type="hidden" name="command" value="update_test_questions">
                    <button type="submit" class="btn btn-success">Update</button>
                </div>
            </div>
        </div>
    </form>
</main>

<%@include file="../jspf/footer.jspf" %>

</body>
</html>
