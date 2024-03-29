<!DOCTYPE html>
<html lang="en">
<head>
    <title><fmt:message key="head.title.project_name"/> | <fmt:message key="head.title.course_categories"/></title>
</head>
<body class="d-flex flex-column h-100">
<%@include file="jspf/header.jspf" %>
<main class="flex-shrink-0">
    <h1 class="text-center"><fmt:message key="head.title.course_categories"/></h1>
    <div class="container">
        <%@include file="jspf/error-success.jspf" %>
        <form action="<c:url value="/controller"/>" id="searchForm" method="get"></form>
        <input type="hidden" name="command" value="show_course_categories" form="searchForm">
        <%@include file="jspf/search.jspf" %>
        <table class="table caption-top table-bordered table-hover">
            <caption><fmt:message key="head.title.course_categories"/></caption>
            <thead>
            <tr>
                <th scope="col">#</th>
                <th scope="col"><fmt:message key="label.course.category.name"/></th>
                <th scope="col"><fmt:message key="label.action"/></th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="category" varStatus="st" items="${course_categories}">
                <tr>
                    <form action="<c:url value="/controller"/>" method="post">
                        <input type="hidden" name="command" value="update_category">
                        <input type="hidden" name="category_id" value="${category.id}">
                        <th scope="row">
                            <%@include file="jspf/entity-number.jspf" %>
                        </th>
                        <td>
                            <input type="text"
                                   maxlength="128"
                                   minlength="3"
                                   name="category_name"
                                   pattern="^(?:[\p{L}]+)(?:[\p{L}0-9 _]*)$"
                                   value="${category.name}"
                                   placeholder="<fmt:message key="label.course.category.name"/>">
                        </td>
                        <td>
                            <input class="btn btn-warning" type="submit" value="<fmt:message
                                    key="label.update"/>"/>
                            <a class="btn btn-danger"
                               href="<c:url value="/controller?command=delete_category&category_id=${category.id}"/>"><fmt:message
                                    key="label.delete"/></a>
                        </td>
                    </form>
                </tr>
            </c:forEach>
            <tr>
                <form action="<c:url value="/controller"/>" method="post">
                    <input type="hidden" name="command" value="create_category">
                    <td>#</td>
                    <td>
                        <input type="text"
                               maxlength="128"
                               minlength="3"
                               name="category_name"
                               pattern="^(?:[\p{L}]+)(?:[\p{L}0-9 _]*)$"
                               placeholder="<fmt:message key="label.course.category.name"/>"
                               required>
                    </td>
                    <td>
                    <td><input class="btn btn-success" type="submit" value="<fmt:message
                                    key="label.create"/>"/>
                    </td>
                </form>
            </tr>
            </tbody>
        </table>
        <div class="container">
            <nav aria-label="Page navigation example">
                <%@include file="jspf/pagination.jspf" %>
            </nav>
        </div>
    </div>
</main>
<%@include file="jspf/footer.jspf" %>
</body>
</html>
