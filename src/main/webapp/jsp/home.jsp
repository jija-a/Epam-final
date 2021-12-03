<!DOCTYPE html>

<html lang="en">
<head>

    <title><fmt:message key="head.title.project_name"/> | <fmt:message key="head.title.registration"/></title>

    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/home.css">
</head>

<body class="d-flex flex-column h-100">

<%@include file="jspf/header.jspf" %>

<!-- Begin page content -->
<c:if test="${user.role.id == 2}">
    <jsp:forward page="controller?command=show_available_courses"/>
</c:if>
<c:if test="${user.role.id == 1}">
    <jsp:forward page="/controller?command=show_teacher_courses"/>
</c:if>
<c:if test="${user.role.id == 0}">
    <jsp:forward page="/controller?command=show_courses"/>
</c:if>

<%@include file="jspf/footer.jspf" %>

</body>
</html>
