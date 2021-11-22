<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<body>
<c:if test="${sessionScope.user != null}">
    <jsp:forward page="/controller?command=to_home_page"/>
</c:if>
<c:if test="${sessionScope.user == null}">
    <jsp:forward page="/controller?command=to_login_page"/>
</c:if>
</body>
</html>
