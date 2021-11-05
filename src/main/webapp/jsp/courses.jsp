<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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
</head>
<body>

<table width="80%" align="center">
    <tr>
        <td class="tHead"><strong>Id</strong></td>
        <td class="tHead"><strong>Title</strong></td>
        <td class="tHead"><strong>Owner name</strong></td>
        <td class="tHead"><strong>Owner lastname</strong></td>
        <td class="tHead"><strong>Category</strong></td>
        <td class="tHead"><strong>Action</strong></td>
    </tr>
    <jsp:useBean id="courses" scope="request" type="java.util.List"/>
    <c:forEach items="${courses}" var="course">
        <tr>
            <td>${course.id}</td>
            <td>${course.name}</td>
            <td>${course.owner.firstName}</td>
            <td>${course.owner.lastName}</td>
            <td>${course.category.name}</td>
            <td>
                <form action="<c:url value="/controller"/>" method="get">
                    <input type="hidden" name="command" value="show_course_users">
                    <input type="hidden" name="course_id" value="${course.id}">
                    <input type="submit" value="Find course users">
                </form>
                <form action="<c:url value="/controller"/>" method="get">
                    <input type="hidden" name="command" value="show_course_tests">
                    <input type="hidden" name="course_id" value="${course.id}">
                    <input type="submit" value="Find course tests">
                </form>
            </td>
        </tr>
    </c:forEach>
</table>

</body>
</html>
