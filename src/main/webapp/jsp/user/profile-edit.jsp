<!DOCTYPE html>

<html lang="en">
<head>

    <title><fmt:message key="head.title.project_name"/> | <fmt:message key="head.title.users_list"/></title>

    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/profile-edit.css">
</head>

<body class="d-flex flex-column h-100">

<%@include file="../jspf/header.jspf" %>

<main class="flex-shrink-0">
    <div class="container rounded bg-white mt-5 mb-5">
        <div class="row">
            <div class="col-md-3 border-right">
                <div class="d-flex flex-column align-items-center text-center p-3 py-5">
                    <img class="rounded-circle mt-5"
                         width="150px"
                         src="https://st3.depositphotos.com/15648834/17930/v/600/depositphotos_179308454-stock-illustration-unknown-person-silhouette-glasses-profile.jpg"
                         alt="img">
                    <span class="font-weight-bold">${user.firstName} ${user.lastName}</span>
                    <span class="text-black-50">${user.login}</span><span> </span></div>
            </div>
            <div class="col-md-5 border-right">
                <div class="p-3 py-5">
                    <div class="d-flex justify-content-between align-items-center mb-3">
                        <h4 class="text-right">Profile Settings</h4>

                        <%@include file="../jspf/error-success.jspf" %>

                        <div id="error" style="display:none;" class="alert alert-danger alert-dismissible fade show">
                            <strong><fmt:message key="label.warn"/></strong><fmt:message
                                key="error.conf_psw"/>
                        </div>

                    </div>
                    <form action="<c:url value="/controller?"/>" method="post">
                        <input type="hidden" name="command" value="update_profile">
                        <div class="row mt-2">
                            <div class="col-md-6"><label class="labels"><fmt:message key="label.name"/></label>
                                <input type="text" class="form-control" name="first_name"
                                       placeholder="<fmt:message key="input.placeholder.first_name"/>"
                                       pattern="^\D[^0-9]{3,25}$"
                                       value="${user.firstName}"
                                       required>
                            </div>
                            <div class="col-md-6"><label class="labels"><fmt:message
                                    key="input.placeholder.last_name"/></label>
                                <input type="text" class="form-control" name="last_name"
                                       placeholder="<fmt:message key="input.placeholder.last_name"/>"
                                       pattern="^\D[^0-9]{3,25}$"
                                       value="${user.lastName}"
                                       required>
                            </div>
                        </div>
                        <div class="row mt-3">
                            <div class="col-md-12"><label class="labels"><fmt:message
                                    key="input.placeholder.login"/></label>
                                <input type="text" class="form-control" name="login" disabled
                                       placeholder="<fmt:message key="input.placeholder.login"/>"
                                       pattern="^[a-zA-Z]{3,25}$"
                                       value="${user.login}"
                                       required>
                            </div>
                        </div>
                        <div class="row mt-3">
                            <div class="col-md-6"><label class="labels"><fmt:message
                                    key="input.placeholder.password"/></label>
                                <input type="password" class="form-control" id="pw1" name="password"
                                       placeholder="<fmt:message key="input.placeholder.password"/>"
                                       pattern=".*{,16}"
                                       maxlength="16"
                                       required>
                            </div>
                            <div class="col-md-6"><label class="labels"><fmt:message
                                    key="input.placeholder.confirmation_password"/></label>
                                <input type="password" class="form-control" id="pw2" name="confirmation_password"
                                       placeholder="<fmt:message key="input.placeholder.confirmation_password"/>"
                                       pattern=".*{,16}"
                                       maxlength="16"
                                       required>
                            </div>
                        </div>
                        <div class="mt-5 text-center">
                            <button class="btn btn-primary profile-button" type="submit">Save Profile</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</main>

<%@include file="../jspf/footer.jspf" %>

<script src="${pageContext.request.contextPath}/js/auth.js"></script>

</body>
</html>
