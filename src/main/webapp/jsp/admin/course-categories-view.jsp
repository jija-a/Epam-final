<!DOCTYPE html>

<html lang="en">
<head>

    <title><fmt:message key="head.title.project_name"/> | <fmt:message key="head.title.users_list"/></title>

</head>

<body class="d-flex flex-column h-100">

<%@include file="../../jspf/header.jspf" %>

<main class="flex-shrink-0">
    <div class="container">
        <c:if test="${not empty error}">
            <div class="alert alert-danger alert-dismissible fade show">
                <strong><fmt:message key="label.warn"/></strong> <c:out value="${error}"/>
            </div>
        </c:if>

        <c:if test="${not empty success}">
            <div class="alert alert-success alert-dismissible fade show">
                <strong><fmt:message key="label.success"/></strong>
                <h6 class="text-success" id="success"><c:out value="${success}"/></h6>
                <c:remove var="success" scope="session"/>
            </div>
        </c:if>
        <c:choose>
            <c:when test="${not empty course_categories}">
                <form action="<c:url value="/controller"/>">
                    <input type="hidden" name="command" value="show_course_categories">
                    <%@include file="../../jspf/search.jspf" %>
                    <table class="table caption-top">
                        <caption><fmt:message key="head.title.courses_list"/></caption>
                        <thead>
                        <tr>
                            <th scope="col">#</th>
                            <th scope="col"><fmt:message key="label.course.category.name"/></th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach var="category" varStatus="st" items="${course_categories}">
                            <tr>
                                <th scope="row">${st.count}</th>
                                <td><c:out value="${category.name}"/></td>
                                <td>
                                    <a class="btn btn-primary"
                                       href="<c:url value="/controller?command=update_category&category_id=${category.id}"/>"><fmt:message
                                            key="label.update"/></a>
                                    <a class="btn btn-danger"
                                       href="<c:url value="/controller?command=delete_category&category_id=${category.id}"/>"><fmt:message
                                            key="label.delete"/></a>
                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                    <div class="container">
                        <nav aria-label="Page navigation example">
                            <%@include file="../../jspf/pagination.jspf" %>
                        </nav>
                    </div>
                </form>
            </c:when>
            <c:otherwise>
                <h1 class="text-center"><fmt:message key="title.course.category.not.found"/></h1>
            </c:otherwise>
        </c:choose>
    </div>
</main>

<%@include file="../../jspf/footer.jspf" %>

</body>
</html>
