<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="page_content"/>
<html>
<head>
    <title><fmt:message key="title.project_name"/> | <fmt:message key="title.course_users"/></title>
    <style type="text/css">
        table, td, tr {
            border: 1px dashed black;
            margin: 0 auto;
            text-align: center;
        }

        .tHead {
            background-color: aquamarine;
        }
    </style>
    <%@include file="/jsp/jspf/head.jspf" %>
</head>
<body>
<%@include file="/jsp/jspf/header.jspf" %>

<c:if test="${not empty users}">
    <h1 align="center"><fmt:message key="title.course_users"/> "${course.name}"</h1>
    <table width="80%" align="center">
        <tr>
            <td class="tHead"><strong><fmt:message key="label.id"/></strong></td>
            <td class="tHead"><strong><fmt:message key="title.login"/></strong></td>
            <td class="tHead"><strong><fmt:message key="label.first_name"/></strong></td>
            <td class="tHead"><strong><fmt:message key="label.last_name"/></strong></td>
            <td class="tHead"><strong><fmt:message key="label.role"/></strong></td>
            <td class="tHead"><strong><fmt:message key="label.action"/></strong></td>
        </tr>
        <c:forEach items="${users}" var="user">

            <tr>
                <td>${user.id}</td>
                <td>${user.login}</td>
                <td>${user.firstName}</td>
                <td>${user.lastName}</td>
                <td>${user.role.name}</td>
                    <td>
                        <form action="<c:url value="/controller"/>" method="post">
                            <input type="hidden" name="command" value="remove_user_from_course">
                            <input type="hidden" name="user_id" value="${user.id}">
                            <input type="hidden" name="course_id" value="${course.id}">
                            <input style="display: block; margin: auto;" type="submit" value="Remove from course">
                        </form>
                    </td>
            </tr>
        </c:forEach>
    </table>
</c:if>
<c:if test="${empty users}">
    <h1 align="center"><fmt:message key="label.no_course_users"/> "${course.name}"</h1>
    <c:if test="${sessionScope.user.role.id == 1}">
    <form action="<c:url value="/controller"/>" method="get">
        <input type="hidden" name="command" value="to_add_users_on_course_page">
        <input type="hidden" name="courseId" value="${course.id}">
        <input type="submit" value="Add users">
    </form>
    </c:if>
</c:if>
</body>
</html>
