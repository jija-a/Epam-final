<!DOCTYPE html>

<html lang="en">
<head>

    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/login.css">

    <title><fmt:message key="head.title.project_name"/> | <fmt:message key="head.title.registration"/></title>
</head>

<body>
<div class="sidenav">
    <div class="sidebar-main-text">
        <h2><fmt:message key="head.title.project_name"/><br><fmt:message key="head.title.registration"/></h2>
        <p><fmt:message key="p.register.info"/></p>
    </div>
</div>
<div class="main">
    <div class="col-md-6 col-sm-12">
        <div class="register-form">
            <c:if test="${not empty error}">
                <div class="alert alert-danger alert-dismissible fade show">
                    <strong><fmt:message key="label.warn"/></strong> <c:out value="${error}"/>
                </div>
            </c:if>

            <c:if test="${not empty errors}">
                <div class="alert alert-danger alert-dismissible fade show">
                    <strong><fmt:message key="label.warn"/></strong>
                    <ul>
                        <c:forEach var="error" items="${errors}">
                            <li><p>${error}</p></li>
                        </c:forEach>
                    </ul>
                </div>
            </c:if>

            <div id="error" style="display: none" class="alert alert-danger alert-dismissible fade show">
                <strong><fmt:message key="label.warn"/></strong><fmt:message
                    key="error.conf_psw"/>
            </div>

            <form action="<c:url value="/controller"/>" method="POST">
                <input type="hidden" name="command" value="register">
                <div class="form-group">
                    <label><fmt:message key="label.login"/></label>
                    <input type="text" class="form-control" name="login" required
                           placeholder="<fmt:message key="input.placeholder.login"/>"
                           pattern="^[\w-]{3,25}$">
                </div>
                <div class="form-group">
                    <label><fmt:message key="label.password"/></label>
                    <input type="password" class="form-control" id="pw1" name="password"
                           placeholder="<fmt:message key="input.placeholder.password"/>"
                           pattern="^(?=.*\d)(?=.*[a-z])(?=.*[A-Z])(?!.*\s).*$"
                           minlength="8"
                           maxlength="16" required>
                </div>
                <div class="form-group">
                    <label><fmt:message key="label.confirm_psw"/></label>
                    <input type="password" class="form-control" id="pw2" name="confirmation_password"
                           placeholder="<fmt:message key="input.placeholder.confirmation_password"/>"
                           pattern="^(?=.*\d)(?=.*[a-z])(?=.*[A-Z])(?!.*\s).*$"
                           minlength="8"
                           maxlength="16" required>
                </div>
                <div class="form-group">
                    <label><fmt:message key="label.name"/></label>
                    <input type="text" class="form-control" name="first_name"
                           placeholder="<fmt:message key="input.placeholder.first_name"/>"
                           pattern="^[a-zA-Z]{3,25}$" required>
                </div>
                <div class="form-group">
                    <label><fmt:message key="label.last_name"/></label>
                    <input type="text" class="form-control" name="last_name"
                           placeholder="<fmt:message key="input.placeholder.last_name"/>"
                           pattern="^[a-zA-Z]{3,25}$" required>
                </div>
                <div class="buttons">
                    <input type="radio" name="user_role" value="1" required> <fmt:message key="input.radio.teacher"/>
                    <input type="radio" name="user_role" value="2" required> <fmt:message key="input.radio.student"/>
                </div>
                <div class="buttons">
                    <button type="submit" class="btn btn-black" id="inputBtn"><fmt:message
                            key="input.value.register"/></button>
                    <a href="<c:url value="/controller?command=to_login_page"/>" type="submit"
                       class="btn btn-secondary"><fmt:message
                            key="input.value.cancel"/></a>
                </div>
            </form>
        </div>
    </div>
</div>

<script src="${pageContext.request.contextPath}/js/auth.js"></script>
</body>
</html>
