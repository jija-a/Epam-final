<!DOCTYPE html>
<html lang="en">
<head>
<title><fmt:message key="head.title.project_name"/></title>
</head>
<body>
<c:if test="${sessionScope.user != null}">
    <jsp:forward page="/controller?command=to_home_page"/>
</c:if>
<c:if test="${sessionScope.user == null}">
    <jsp:forward page="/controller?command=to_login_page"/>
</c:if>
</body>
</html>
