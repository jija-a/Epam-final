<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="page_content"/>
<html>
<head>
    <title><fmt:message key="title.project_name"/> | <fmt:message key="title.login"/></title>
    <%@include file="/jsp/jspf/head.jspf" %>

    <!--Custom CSS-->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/signin.css">
</head>
<body class="text-center">

<%@include file="jspf/header.jspf" %>

<div class="container-fluid login-form" style="padding-top: 60px">
    <form action="<c:url value="/controller"/>" method="post">
        <input type="hidden" name="command" value="register">
        <h2 class="text-center"><fmt:message key="title.registration"/></h2>
        <div class="container-fluid">
            <c:if test="${not empty error}">
                <h4 class="text-danger text-center"><c:out value="${error}"/></h4>
            </c:if>
            <c:if test="${not empty errors}">
                <ul>
                    <c:forEach items="${errors}" var="error">
                        <li class="text-danger">${error}</li>
                    </c:forEach>
                </ul>
            </c:if>
        </div>
        <div class="form-group">
            <input type="text" class="form-control" placeholder="<fmt:message key="placeholder.login"/> "
                   minlength="3" maxlength="255" required="required" name="login">
        </div>
        <div class="form-group">
            <input type="password" class="form-control" placeholder="<fmt:message key="placeholder.password"/> "
                   minlength="8" maxlength="255" required="required" name="password">
        </div>
        <div class="form-group">
            <input type="password" class="form-control" placeholder="<fmt:message key="placeholder.password"/> "
                   minlength="8" maxlength="255" required="required" name="conf_password">
        </div>
        <div class="form-group">
            <input type="text" class="form-control" placeholder="<fmt:message key="placeholder.first_name"/> "
                   minlength="3" maxlength="255" required="required" name="first_name">
        </div>
        <div class="form-group">
            <input type="text" class="form-control" placeholder="<fmt:message key="placeholder.last_name"/> "
                   minlength="3" maxlength="255" required="required" name="last_name">
        </div>
        <div class="form-group">
            <input type="radio" class="form-control" required="required" name="user_role" value="1"><fmt:message
                key="input.i_am_a_teacher"/>
            <input type="radio" class="form-control" required="required" name="user_role" value="2"><fmt:message
                key="input.i_am_a_student"/>
        </div>
        <div class="form-group">
            <button type="submit" class="btn btn-primary btn-block"><fmt:message key="input.sign_up"/></button>
        </div>
    </form>
</div>

</body>
</html>
