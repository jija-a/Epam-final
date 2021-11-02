<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="users" scope="request" type="java.util.List"/>
<html>
<head>
    <title>Testing | Course users</title>
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
        <td class="tHead"><strong>Login</strong></td>
        <td class="tHead"><strong>First Name</strong></td>
        <td class="tHead"><strong>Last Name</strong></td>
        <td class="tHead"><strong>Role</strong></td>
        <td class="tHead"><strong>Action</strong></td>
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
                    <input style="display: block; margin: auto;" type="submit" value="Remove from course">
                </form>
            </td>
        </tr>
    </c:forEach>
</table>

</body>
</html>
