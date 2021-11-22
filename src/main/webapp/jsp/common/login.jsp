<!DOCTYPE html>

<html lang="en">
<head>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/login.css">

    <title><fmt:message key="head.title.project_name"/> | <fmt:message key="head.title.login"/></title>

</head>

<body>
<div class="sidenav">
    <div class="sidebar-main-text">
        <h2><fmt:message key="head.title.project_name"/></h2>
        <p><fmt:message key="p.login.info"/></p>
    </div>
</div>
<div class="main">
    <div class="col-md-6 col-sm-12">
        <div class="login-form">

            <c:if test="${not empty error}">
                <div class="alert alert-danger alert-dismissible fade show">
                    <strong><fmt:message key="label.warn"/></strong> <c:out value="${error}"/>
                </div>
            </c:if>

            <c:if test="${not empty success}">
                <div class="alert alert-success alert-dismissible fade show">
                    <strong><fmt:message key="label.success"/></strong>
                    <h6 class="text-success" id="success"><c:out value="${success}"/></h6>
                    <c:remove var="success" scope="session"/>
                </div>
            </c:if>

            <form action="<c:url value="/controller"/>" method="POST">
                <input type="hidden" name="command" value="login">
                <div class="form-group">
                    <label><fmt:message key="label.login"/></label>
                    <input type="text" class="form-control" name="login" required
                           placeholder="<fmt:message key="input.placeholder.login"/>"
                           minlength="1">
                </div>
                <div class="form-group pass">
                    <label><fmt:message key="label.password"/></label>
                    <input type="password" class="form-control" id="pw1" name="password"
                           placeholder="<fmt:message key="input.placeholder.password"/>"
                           minlength="1" required>
                </div>
                <div class="buttons">
                    <button type="submit" class="btn btn-black"><fmt:message key="input.value.login"/></button>
                    <a href="<c:url value="/controller?command=to_registration_page"/>" type="submit"
                       class="btn btn-secondary"><fmt:message key="input.value.register"/></a>
                </div>
            </form>
        </div>
    </div>
</div>

</body>
</html>


