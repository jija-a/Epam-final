<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="courses" scope="request" type="java.util.List"/>
<html>
<head>
    <title>Testing | Courses</title>
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

<c:if test="${not empty courses}">
    <h1 align="center">${user.login} courses</h1>

    <table width="80%" align="center">
        <tr>
            <td class="tHead"><strong>Id</strong></td>
            <td class="tHead"><strong>Title</strong></td>
            <td class="tHead"><strong>Owner name</strong></td>
            <td class="tHead"><strong>Owner lastname</strong></td>
            <td class="tHead"><strong>Category</strong></td>
            <td class="tHead"><strong>Action</strong></td>
        </tr>
        <c:forEach items="${courses}" var="course">
            <tr>
                <td>${course.id}</td>
                <td>${course.name}</td>
                <td>${course.owner.firstName}</td>
                <td>${course.owner.lastName}</td>
                <td>${course.category.name}</td>
                <td>
                    <form action="<c:url value="/controller"/>" method="post">
                        <input type="hidden" name="command" value="remove_user_from_course">
                        <input type="hidden" name="course_id" value="${course.id}">
                        <input style="display: block; margin: auto;" type="submit" value="Exit course">
                    </form>
                </td>
            </tr>
        </c:forEach>
    </table>
</c:if>

<c:if test="${empty courses}">
    <h1 align="center">You are not signed on any course</h1>
</c:if>

</body>
</html>
