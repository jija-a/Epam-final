<jsp:useBean id="course" scope="request" type="by.alex.testing.domain.Course"/>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Testing | Courses</title>
    <style type="text/css">
        form table, td, tr {
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

<form action="<c:url value="/controller"/>" method="post">
    <table width="80%" align="center">
        <tr>
            <td class="tHead"><strong>Id</strong></td>
            <td class="tHead"><strong>Title</strong></td>
            <td class="tHead"><strong>Category</strong></td>
            <td class="tHead"><strong>Action</strong></td>
        </tr>
        <tr>
            <td>${course.id}</td>
            <td><input type="text" name="course_name" value="${course.name}"></td>
            <td>
                <select name="course_category_id">
                    <jsp:useBean id="course_categories" scope="request" type="java.util.List"/>
                    <c:forEach var="category" items="${course_categories}">
                        <c:if test="${course.category.name == category.name}">
                            <option value="${category.id}" selected>${category.name}</option>
                        </c:if>
                        <c:if test="${course.category.name != category.name}">
                            <option value="${category.id}">${category.name}</option>
                        </c:if>
                    </c:forEach>
                </select>
            </td>
            <td>
                <input type="hidden" name="command" value="update_course">
                <input type="hidden" name="course_id" value="${course.id}">
                <input type="submit" value="UPDATE">
            </td>
        </tr>
    </table>
</form>

</body>
</html>
