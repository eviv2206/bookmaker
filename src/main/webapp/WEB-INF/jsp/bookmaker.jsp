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
    <script type="text/javascript">
        function submitForm(selectedOption) {
            let form = document.getElementById("betForm");
            let commandInput = document.createElement("input");
            commandInput.type = "hidden";
            commandInput.name = "command";
            commandInput.value = selectedOption;
            form.appendChild(commandInput);
            form.submit();
        }
    </script>
    <style>
        body {
            box-sizing: border-box;
        }

        .item {
            display: flex;
            flex-direction: column;
            width: fit-content;
            border: 1px solid black;
            min-width: 300px;
            margin-bottom: 10px;
        }

        .form {
            display: flex;
            flex-direction: column;
            width: fit-content;
            gap: 3px;
        }
    </style>
</head>
<body>
<%@include file="/header.jsp" %>

<h1><fmt:message key="bookmaker_bet_label"/></h1>

<form id="betForm" action="controller?page=bookmaker.jsp" method="post">
    <select id="command" name="command" onchange="submitForm(this.value)">
        <option disabled selected value><fmt:message key="admin_panel_choose"/></option>
        <option value="GET_ALL_USER_BET_COMMAND" ${param.command eq 'GET_ALL_USER_BET_COMMAND' ? 'selected' : ''}>
            <fmt:message key="bookmaker_bet_get"/></option>
    </select>
</form>
<c:choose>
    <c:when test="${param.command eq 'GET_ALL_USER_BET_COMMAND'}">
        <c:forEach var="bet" varStatus="loop" items="${requestScope.userBets}">
            <div class="item">
                <p><fmt:message key="bookmaker_bet_date"/>: ${bet.date}</p>
                <p><fmt:message key="bookmaker_bet_amount"/>: ${bet.betAmount}</p>
                <p><fmt:message key="bookamker_bet_winningAmount"/> ${bet.winningAmount}</p>
                <p><fmt:message key="bookmaker_bet_score"/>: ${bet.score}</p>
                <p><fmt:message
                        key="bookmaker_bet_coefficient"/>: ${requestScope.betTypeEvents[loop.index].coefficient}</p>
                <p><fmt:message key="bookmaker_bet_betType"/>: ${requestScope.betTypes[loop.index].name}</p>
                <p><fmt:message key="bookmaker_bet_event"/>: ${requestScope.events[loop.index].name}</p>
                <p><fmt:message key="bookmaker_bet_event_startTime"/>: ${requestScope.events[loop.index].start_time}</p>
                <p><fmt:message key="bookmaker_bet_event_result"/>: ${requestScope.events[loop.index].result}</p>
            </div>
        </c:forEach>
        <label for="addUserBet"><fmt:message key="admin_panel_add_userBet"/></label>
        <form action="controller?page=admin.jsp" method="post" class="form" id="addUserBet">
            <div class="item">
                <input type="hidden" name="command" value="ADD_USER_BET_COMMAND">
                <select required name="betTypeEventId">
                    <option disabled selected value><fmt:message key="admin_panel_choose"/></option>
                    <c:forEach var="betTypeEvent" varStatus="loop" items="${requestScope['allBetTypeEvents']}">
                        <option value="${betTypeEvent.betTypeEventId}">${requestScope['allEvents'][loop.index].name}
                            - ${requestScope['allBetTypes'][loop.index].name} - ${betTypeEvent.coefficient}</option>
                    </c:forEach>
                </select>
                <input type="text" name="score" placeholder="0-0">
                <input type="number" step="0.01" name="betAmount" placeholder="0.00">
                <button type="submit"><fmt:message key="admin_panel_add"/></button>
            </div>
        </form>

    </c:when>
</c:choose>
</body>
</html>
