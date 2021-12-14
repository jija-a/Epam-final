<!DOCTYPE html>
<html lang="en">
<head>
    <title><fmt:message key="head.title.project_name"/> | <fmt:message key="label.marks"/></title>
</head>
<body class="d-flex flex-column h-100">
<%@include file="jspf/header.jspf" %>
<main class="flex-shrink-0">
    <h1 class="text-center"><fmt:message key="label.marks"/></h1>
    <div class="container">
        <%@include file="jspf/error-success.jspf" %>
        <c:choose>
            <c:when test="${not empty attendances}">
                <table class="table caption-top table-bordered table-hover">
                    <caption><fmt:message key="label.marks"/></caption>
                    <thead>
                    <tr>
                        <th scope="col">#</th>
                        <th scope="col"><fmt:message key="label.name"/></th>
                        <th scope="col"><fmt:message key="label.status"/></th>
                        <th scope="col"><fmt:message key="label.mark"/></th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${attendances}" var="attendance" varStatus="st">
                        <tr>
                            <form action="<c:url value="/controller"/>" method="post">
                                <input type="hidden" name="command" value="update_attendance">
                                <input type="hidden" name="attendance_id" value="${attendance.id}">
                                <td>${st.count}</td>
                                <td>${attendance.student.lastName} ${attendance.student.firstName}</td>
                                <td>
                                    <select name="att_status_id">
                                        <c:forEach items="${statuses}" var="status">
                                            <c:if test="${attendance.status eq status}">
                                                <option value="${status.id}" selected>${status.name}</option>
                                            </c:if>
                                            <c:if test="${attendance.status ne status}">
                                                <option value="${status.id}">${status.name}</option>
                                            </c:if>
                                        </c:forEach>
                                    </select>
                                </td>
                                <td>
                                    <c:choose>
                                        <c:when test="${attendance.mark ne 0}">
                                            <input type="number" name="mark" min="1" max="12" maxlength="2"
                                                   value="${attendance.mark}"/>
                                        </c:when>
                                        <c:otherwise>
                                            <input type="number" name="mark" min="1" max="12" maxlength="2"
                                                   placeholder="No mark"/>
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                                <td>
                                    <input type="submit" class="btn btn-warning"
                                           value="<fmt:message key="label.update"/>"/>
                                </td>
                            </form>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </c:when>
            <c:otherwise>
                <h1 class="text-center text-muted"><fmt:message key="error.not.found"/></h1>
            </c:otherwise>
        </c:choose>
    </div>
</main>
<%@include file="jspf/footer.jspf" %>
</body>
</html>
