<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Testing | Course tests</title>
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
        <td class="tHead"><strong>Attempts</strong></td>
        <td class="tHead"><strong>Start Date</strong></td>
        <td class="tHead"><strong>End Date</strong></td>
        <td class="tHead"><strong>Time to answer</strong></td>
        <td class="tHead"><strong>Max score</strong></td>
        <td class="tHead"><strong>Action</strong></td>
    </tr>
    <jsp:useBean id="tests" scope="request" type="java.util.List"/>
    <c:forEach items="${tests}" var="test">
        <tr>
            <td>${test.id}</td>
            <td>${test.title}</td>
            <td>${test.attempts}</td>
            <td>${test.startDate}</td>
            <td>${test.endDate}</td>
            <td>${test.timeToAnswer}</td>
            <td>${test.maxScore}</td>
            <td>
                <form action="<c:url value="/controller"/>" method="post">
                    <input type="hidden" name="command" value="remove_test_from_course">
                    <input type="hidden" name="test_id" value="${test.id}">
                    <input style="display: block; margin: auto;" type="submit" value="Remove from course">
                </form>
            </td>
        </tr>
    </c:forEach>
</table>

</body>
</html>
