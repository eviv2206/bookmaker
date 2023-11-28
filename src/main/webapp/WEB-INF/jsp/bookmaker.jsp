<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<fmt:setLocale value='${sessionScope.language}'/>
<fmt:setBundle basename="messages"/>
<c:set value='${sessionScope.curruser}' var="user"/>
<c:if test="${empty user}">
    <c:redirect url="/controller?page=login.jsp"/>
</c:if>

<html>
<head>
    <title>BOOKMAKER</title>
</head>
<body>
    <%@include file="/header.jsp"%>

    <h1><fmt:message key="bookmaker_bet_label"/></h1>

    <form action="/controller?page=bookmaker.jsp" method="post">
        <input type="hidden" name="action" value="GET_ALL_USER_BETS_COMMAND">
        <button type="submit"><fmt:message key="bookmaker_bet_get"/></button>
    </form>
    <c:forEach var="bet" varStatus="loop" items="${sessionScope.userBets}">
        <p>${bet.date}</p>
        <p>${bet.amount}</p>
        <p>${bet.winningAmount}</p>
        <p>${sessionScope.betTypeEvents[loop.index].coefficient}</p>
        <p>${sessionScope.betTypes[loop.index].name}</p>
    </c:forEach>
</body>
</html>
