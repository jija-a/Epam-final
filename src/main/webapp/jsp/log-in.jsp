<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<fmt:setLocale value="${cookie['lang'].value}"/>
<fmt:setBundle basename="page_content"/>
<html>
<head>
    <title><fmt:message key="title.project_name"/> | <fmt:message key="title.login"/></title>
    <%@include file="/jsp/jspf/head.jspf" %>

    <!--Custom CSS-->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/signin.css">
</head>
<body>

<%@include file="jspf/header.jspf" %>

<div class="login-form"  style="padding-top: 60px">
    <form action="<c:url value="/controller"/>" method="post">
        <input type="hidden" name="command" value="login">
        <h2 class="text-center"><fmt:message key="title.log_in"/></h2>
        <c:if test="${not empty error}">
            <h4 class="text-danger text-center"><c:out value="${error}"/></h4>
        </c:if>
        <div class="form-group">
            <input type="text" class="form-control" placeholder="<fmt:message key="placeholder.login"/> "
                   minlength="3" maxlength="255" required="required" name="login">
        </div>
        <div class="form-group">
            <input type="password" class="form-control" placeholder="<fmt:message key="placeholder.password"/> "
                   minlength="8" maxlength="255" required="required" name="password">
        </div>
        <div class="form-group">
            <button type="submit" class="btn btn-primary btn-block"><fmt:message key="title.log_in"/></button>
        </div>
        <div class="clearfix">
            <label class="pull-left checkbox-inline"><input type="checkbox"><fmt:message key="title.remember_me"/>
            </label>
            <a href="#" class="pull-right"><fmt:message key="title.forgot_password"/></a>
        </div>
    </form>
    <p class="text-center"><a href="<c:url value="/controller?command=to_register_page"/>"><fmt:message
            key="title.create_an_account"/></a></p>
</div>
</body>
</html>
