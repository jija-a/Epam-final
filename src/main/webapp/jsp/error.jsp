<!DOCTYPE html>
<html lang="en">
<head>
<title><fmt:message key="head.title.project_name"/> | <fmt:message key="label.warn"/></title>
<style type="text/css">
    * {
        transition: all 0.6s;
    }

    html {
        height: 100%;
    }

    body {
        font-family: 'Lato', sans-serif;
        color: #888;
        margin: 0;
    }

    #main {
        display: table;
        width: 100%;
        height: 100vh;
        text-align: center;
    }

    .fof {
        display: table-cell;
        vertical-align: middle;
    }

    .fof h1 {
        font-size: 50px;
        display: inline-block;
        padding-right: 12px;
        animation: type .5s alternate infinite;
    }

    @keyframes type {
        from {
            box-shadow: inset -3px 0 0 #888;
        }
        to {
            box-shadow: inset -3px 0 0 transparent;
        }
    }
</style>

</head>
<body>

<div id="main">
    <div class="fof">
        <h1>Error ${pageContext.errorData.statusCode}</h1>
        <c:if test="${not empty error}">
            <c:out value="${error}"/>
            <c:remove var="error" scope="session"/>
        </c:if>
        <h2><a href="<c:url value="/controller?command=to_home_page"/>">Go home</a></h2>
    </div>
</div>

</body>
</html>
