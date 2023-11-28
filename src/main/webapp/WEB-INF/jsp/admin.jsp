<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<fmt:setLocale value='${sessionScope.language}'/>
<fmt:setBundle basename="messages"/>
<c:set value='${sessionScope.curruser}' var="user"/>
<c:if test="${empty user}">
    <c:redirect url="/controller?page=login.jsp"/>
</c:if>
<c:set value='${sessionScope.curruser.privileges}' var="privileges"/>
<c:if test="${privileges == false}">
    <c:redirect url="/controller?page=login.jsp"/>
</c:if>


<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title><fmt:message key="admin_panel_title"/></title>

    <script type="text/javascript">
        function submitForm(selectedOption) {
            let form = document.getElementById("adminForm");
            let commandInput = document.createElement("input");
            commandInput.type = "hidden";
            commandInput.name = "command";
            commandInput.value = selectedOption;
            form.appendChild(commandInput);
            form.submit();
        }

        let currentDate = new Date();

        let formattedDate = currentDate.toISOString().slice(0, 16);

        document.getElementById("start_date").setAttribute("min", formattedDate);
    </script>
</head>
<body>

<%@include file="/header.jsp" %>

<h1><fmt:message key="admin_panel_heading"/></h1>

<form id="adminForm" action="controller?page=admin.jsp" method="POST">
    <select id="command" name="command" onchange="submitForm(this.value)">
        <option disabled selected value><fmt:message key="admin_panel_choose"/></option>
        <option value="GET_ALL_EVENTS_COMMAND" ${param.command eq 'GET_ALL_EVENTS_COMMAND' ? 'selected' : ''}><fmt:message
                key="admin_panel_view_events"/></option>
        <option value="GET_ALL_USERS_BET_COMMAND" ${param.command eq 'GET_ALL_USERS_BET_COMMAND' ? 'selected' : ''}>
            <fmt:message key="admin_panel_view_bets"/></option>
        <option value="GET_ALL_TOURNAMENTS_COMMAND" ${param.command eq 'GET_ALL_TOURNAMENTS_COMMAND' ? 'selected' : ''}>
            <fmt:message key="admin_panel_view_tournaments"/></option>
        <option value="GET_ALL_BET_TYPE_COMMAND" ${param.command eq 'GET_ALL_BET_TYPE_COMMAND' ? 'selected' : ''}>
            <fmt:message key="admin_panel_view_betTypes"/></option>
        <option value="GET_ALL_PARTICIPANTS_COMMAND" ${param.command eq 'GET_ALL_PARTICIPANTS_COMMAND' ? 'selected' : ''}>
            <fmt:message key="admin_panel_view_participants"/></option>
        <option value="GET_ALL_SPORT_TYPE_COMMAND" ${param.command eq 'GET_ALL_SPORT_TYPE_COMMAND' ? 'selected' : ''}>
            <fmt:message key="admin_panel_view_sportTypes"/></option>
    </select>
</form>

<div id="content">
    <c:choose>
        <c:when test="${param.command eq 'GET_ALL_SPORT_TYPE_COMMAND'}">
            <c:forEach var="sportType" items="${requestScope['sportTypes']}">
                <p>${sportType.name}</p>
                <p>${sportType.description}</p>
            </c:forEach>
            <form action="controller?page=admin.jsp" method="post">
                <input type="hidden" name="command" value="ADD_SPORT_TYPE_COMMAND">
                <input type="text" name="name" placeholder="name" required>
                <input type="text" name="description" placeholder="description" required>
                <button type="submit"><fmt:message key="admin_panel_add"/></button>
            </form>
        </c:when>
    </c:choose>
    <c:choose>
        <c:when test="${param.command eq 'GET_ALL_PARTICIPANTS_COMMAND'}">
            <c:forEach var="participant" items="${requestScope['participants']}">
                <p>${participant.name}</p>
            </c:forEach>
            <form action="controller?page=admin.jsp" method="post">
                <input type="hidden" name="command" value="ADD_PARTICIPANT_COMMAND">
                <input type="text" name="name" placeholder="name" required>
                <button type="submit"><fmt:message key="admin_panel_add"/></button>
            </form>
        </c:when>
    </c:choose>
    <c:choose>
        <c:when test="${param.command eq 'GET_ALL_TOURNAMENTS_COMMAND'}">
            <c:forEach var="tournament" varStatus="loop" items="${requestScope['tournaments']}">
                <div>
                    <p>${tournament.name}</p>
                    <p>${tournament.description}</p>
                    <p>${requestScope['currSportTypes'][loop.index].name}</p>
                </div>
            </c:forEach>
            <form action="controller?page=admin.jsp" method="post">
                <input type="hidden" name="command" value="ADD_TOURNAMENT_COMMAND">
                <input type="text" name="name" placeholder="name" required>
                <input type="text" name="description" placeholder="description" required>
                <select name="sportTypeID">
                    <c:forEach var="sportType" varStatus="loop" items="${requestScope['sportTypes']}">
                        <option value="${sportType.id}">${sportType.name}</option>
                    </c:forEach>
                </select>
                <button type="submit"><fmt:message key="admin_panel_add"/></button>
            </form>
        </c:when>
    </c:choose>
    <c:choose>
        <c:when test="${param.command eq 'GET_ALL_BET_TYPE_COMMAND'}">
            <c:forEach var="betType" items="${requestScope['betTypes']}">
                <div>
                    <p>${betType.name}</p>
                    <p>${betType.description}</p>
                </div>
            </c:forEach>
        </c:when>
    </c:choose>
    <c:choose>
        <c:when test="${param.command eq 'GET_ALL_USERS_BET_COMMAND'}">
            <c:forEach var="usersBet" varStatus="loop" items="${requestScope['usersBets']}">
                <div>
                    <p>${usersBet.date}</p>
                    <p>${usersBet.betAmount}</p>
                    <p>${usersBet.winningAmount}</p>
                    <p>${requestScope['users'][loop.index].login}</p>
                    <p>${requestScope['users'][loop.index].email}</p>
                </div>
            </c:forEach>
        </c:when>
    </c:choose>
    <c:choose>
        <c:when test="${param.command eq 'GET_ALL_EVENTS_COMMAND'}">
            <c:forEach var="event" varStatus="loop" items="${requestScope['events']}">
                <div>
                    <p>${event.name}</p>
                    <p>${event.description}</p>
                    <p>${event.start_date}</p>
                    <p>${event.result}</p>
                    <p>${requestScope['tournaments'][loop.index].name}</p>
                    <p>${requestScope['users'][loop.index].login}</p>
                    <p>${requestScope['users'][loop.index].email}</p>
                </div>
            </c:forEach>
            <form action="controller?page=admin.jsp" method="post">
                <input type="hidden" name="command" value="ADD_EVENT_COMMAND">
                <input type="text" name="name" placeholder="name" required>
                <input type="text" name="description" placeholder="description" required>
                <input type="datetime-local" name="start_date" id="start_date" required>
                <input type="text" name="result">
                <select name="winner">
                    <c:forEach var="participant" items="${requestScope['allParticipants']}">
                        <option value="${participant.id}">${participant.name}</option>
                    </c:forEach>
                </select>
                <button type="submit"><fmt:message key="admin_panel_add"/></button>
            </form>
        </c:when>
    </c:choose>
</div>

</body>
</html>