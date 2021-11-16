<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="page_content"/>
<html>
<head>
    <title>Testing | Courses</title>
    <%@include file="/jsp/jspf/head.jspf" %>
</head>
<body>
<%@include file="/jsp/jspf/header.jspf" %>

<c:if test="${not empty courses}">
    <div class="container" style="padding-top: 60px">
        <h1 class="text-center">Courses</h1>
        <c:if test="${not empty message}">
            <h3 class="text-center text-success">${message}</h3>
        </c:if>
        <table class="table table-hover">
            <thead>
            <tr>
                <th scope="col">Id</th>
                <th scope="col">Title</th>
                <th scope="col">Owner</th>
                <th scope="col">Category</th>
                <th scope="col">Action</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${courses}" var="course">
                <tr>
                    <td>${course.id}</td>
                    <td>${course.name}</td>
                    <td>${course.owner.firstName} ${course.owner.lastName}</td>
                    <td>${course.category.name}</td>
                    <td><a href="<c:url value="/controller?command=to_course&course_id=${course.id}"/>"
                           class="btn btn-primary ">To course</a>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</c:if>

<c:if test="${empty courses}">
    <h1 align="center">Courses not found</h1>
</c:if>

</body>
</html>
