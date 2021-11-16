<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="page_content"/>
<html>
<head>
    <title><fmt:message key="title.project_name"/> | <fmt:message key="title.home"/></title>
    <%@include file="/jsp/jspf/head.jspf" %>

    <style type="text/css">
        .row-striped:nth-of-type(odd) {
            background-color: #efefef;
            border-left: 4px #000000 solid;
        }

        .row-striped:nth-of-type(even) {
            background-color: #ffffff;
            border-left: 4px #efefef solid;
        }

        .row-striped {
            padding: 15px 0;
        }

        .badge {
            font-size: 35px;
        }
    </style>

</head>
<body>
<%@include file="/jsp/jspf/header.jspf" %>

<!-- Main jumbotron for a primary marketing message or call to action -->
<c:choose>
    <c:when test="${user == null}">
        <div class="jumbotron">
            <div class="container">
                <h1><fmt:message key="label.welcome"/>!</h1>
                <p><fmt:message key="p.site_description"/></p>
                <p><a class="btn btn-primary btn-lg" href="<c:url value="/controller?command=to_register_page"/>"
                      role="button"><fmt:message key="title.registration"/> &raquo;</a></p>
            </div>
        </div>
    </c:when>
    <c:otherwise>
        <div class="container" style="padding-top: 60px">
            <c:choose>
                <c:when test="${not empty tests}">
                    <h1 class="text-center"><fmt:message key="label.upcoming_events"/></h1>
                    <c:forEach var="entry" items="${tests}">
                        <div class="row row-striped">
                            <div class="col-md-2 text-right">
                                <h1><span class="badge badge-secondary">${entry.key.endDate.getDayOfMonth()}</span></h1>
                                <h2>${entry.key.endDate.getMonth()}</h2>
                            </div>
                            <div class="col-md-10">
                                <h3 class="text-uppercase"><strong>${entry.key.title}</strong></h3>
                                <ul class="list-inline">
                                    <li class="list-inline-item">${entry.key.startDate.getDayOfWeek()}
                                            ${entry.key.startDate.getHour()}:${entry.key.startDate.getMinute()}
                                        - ${entry.key.endDate.getDayOfWeek()} ${entry.key.endDate.getHour()}:${entry.key.endDate.getMinute()}
                                    </li>
                                    <li class="list-inline-item">${entry.value}
                                    </li>
                                </ul>
                                <a href="<c:url value="/controller?command=to_test&test_id="/>"
                                   class="btn btn-primary"><fmt:message key="label.to_test"/></a>
                                <a href="<c:url value="/controller?command=to_course&course_id="/>"
                                   class="btn btn-info"><fmt:message key="label.to_course"/></a>
                            </div>
                        </div>
                    </c:forEach>
                    <c:if test="${tests.size == 10}">
                        <h4 class="text-center" style="padding-bottom: 30px;">
                            <a href="<c:url value="/controller?command=show_user_courses"/>"><fmt:message
                                    key="label.view_more"/>&raquo;</a>
                        </h4>
                    </c:if>
                </c:when>
                <c:otherwise>
                    <h1 class="text-center"><fmt:message key="label.upcoming_events_is_empty"/></h1>
                </c:otherwise>
            </c:choose>
        </div>
    </c:otherwise>
</c:choose>

<jsp:include page="jspf/footer.jspf"/>

</body>
</html>
