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

<h1><fmt:message key="admin_panel_heading"/></h1>

<form id="adminForm" action="controller?page=admin.jsp" method="POST">
    <select id="command" name="command" onchange="submitForm(this.value)">
        <option disabled selected value><fmt:message key="admin_panel_choose"/></option>
        <option value="GET_ALL_EVENTS_COMMAND" ${param.command eq 'GET_ALL_EVENTS_COMMAND' ? 'selected' : ''}>
            <fmt:message
                    key="admin_panel_view_events"/></option>
        <option value="GET_ALL_BET_TYPE_EVENT_COMMAND" ${param.command eq 'GET_ALL_BET_TYPE_EVENT_COMMAND' ? 'selected' : ''}>
            <fmt:message key="admin_panel_view_betTypeEvents"/></option>
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
    <form action="controller?page=admin.jsp" method="post">
        <div class="item">
            <input type="hidden" name="command" value="DELETE_SPORT_TYPE_COMMAND">
            <input type="hidden" name="sportTypeID" value="${sportType.id}">
            <span><fmt:message key="admin_panel_name_sportType"/>: ${sportType.name}</span>
            <span><fmt:message key="admin_panel_description"/>: ${sportType.description}</span>
            <button type="submit"><fmt:message key="admin_panel_delete"/></button>
        </div>
    </form>
</div>
</c:forEach>
<label for="addSportType"><fmt:message key="admin_panel_add_sportType"/></label>
<form action="controller?page=admin.jsp" method="post" class="form" id="addSportType">
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
            <form action="controller?page=admin.jsp" method="post">
                <div class="item">
                    <input type="hidden" name="command" value="DELETE_PARTICIPANT_COMMAND">
                    <input type="hidden" name="participantID" value="${participant.id}">
                    <p><fmt:message key="admin_panel_name_participant"/>: ${participant.name}</p>
                    <button type="submit"><fmt:message key="admin_panel_delete"/></button>
                </div>
            </form>
        </c:forEach>
        <label for="addParticipant"><fmt:message key="admin_panel_add_participant"/></label>
        <form action="controller?page=admin.jsp" method="post" class="form" id="addParticipant">
            <input type="hidden" name="command" value="ADD_PARTICIPANT_COMMAND">
            <input type="text" name="name" placeholder="name" required>
            <button type="submit"><fmt:message key="admin_panel_add"/></button>
        </form>
    </c:when>
</c:choose>
<c:choose>
    <c:when test="${param.command eq 'GET_ALL_TOURNAMENTS_COMMAND'}">
        <c:forEach var="tournament" varStatus="loop" items="${requestScope['tournaments']}">
            <form action="controller?page=admin.jsp" method="post">
                <div class="item">
                    <input type="hidden" name="command" value="DELETE_TOURNAMENT_COMMAND">
                    <input type="hidden" name="tournamentID" value="${tournament.id}">
                    <p><fmt:message key="admin_panel_name_tournament"/>: ${tournament.name}</p>
                    <p><fmt:message key="admin_panel_description"/>: ${tournament.description}</p>
                    <p><fmt:message
                            key="admin_panel_name_sportType"/>: ${requestScope['currSportTypes'][loop.index].name}</p>
                </div>
            </form>
        </c:forEach>
        <label for="addTournament"><fmt:message key="admin_panel_add_tournament"/></label>
        <form action="controller?page=admin.jsp" method="post" class="form" id="addTournament">
            <input type="hidden" name="command" value="ADD_TOURNAMENT_COMMAND">
            <input type="text" name="name" placeholder="name" required>
            <input type="text" name="description" placeholder="description" required>
            <select name="sportTypeID" required>
                <option disabled selected value><fmt:message key="admin_panel_choose"/></option>
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
            <div class="item">
                <p><fmt:message key="admin_panel_name_betType"/>: ${betType.name}</p>
                <p><fmt:message key="admin_panel_description"/>: ${betType.description}</p>
            </div>
        </c:forEach>
    </c:when>
</c:choose>
<c:choose>
    <c:when test="${param.command eq 'GET_ALL_USERS_BET_COMMAND'}">
        <c:forEach var="usersBet" varStatus="loop" items="${requestScope['usersBets']}">
            <div class="item">
                <p><fmt:message key="admin_panel_date"/>:${usersBet.date}</p>
                <p><fmt:message key="admin_panel_betAmount"/>:${usersBet.betAmount}</p>
                <p><fmt:message key="admin_panel_winningAmount"/>${usersBet.winningAmount}</p>
                <p><fmt:message key="admin_panel_login"/>${requestScope['users'][loop.index].login}</p>
                <p><fmt:message key="admin_panel_email"/>${requestScope['users'][loop.index].email}</p>
            </div>
        </c:forEach>
    </c:when>
</c:choose>
<c:choose>
    <c:when test="${param.command eq 'GET_ALL_EVENTS_COMMAND'}">
        <c:forEach var="event" varStatus="loop" items="${requestScope['events']}">
            <form action="controller?page=admin.jsp" method="post">
                <div class="item">
                    <input type="hidden" name="command" value="DELETE_EVENT_COMMAND">
                    <input type="hidden" name="eventID" value="${event.id}">
                    <p><fmt:message key="admin_panel_name_event"/>: ${event.name}</p>
                    <p><fmt:message key="admin_panel_description"/>: ${event.description}</p>
                    <p><fmt:message key="admin_panel_start_time"/>: ${event.start_time}</p>
                    <p><fmt:message key="admin_panel_result"/>: ${event.result}</p>
                    <p><fmt:message key="admin_panel_winner"/>: ${requestScope['winners'][loop.index].name}</p>
                    <p><fmt:message key="admin_panel_name_tournament"/>: ${requestScope['tournaments'][loop.index].name}</p>
                    <p><fmt:message key="admin_panel_view_participants"/></p>
                    <c:forEach var="participant" items="${requestScope['participants'][loop.index]}">
                        <p>${participant.name}</p>
                    </c:forEach>
                    <button type="submit"><fmt:message key="admin_panel_delete"/></button>
                </div>
            </form>
            <c:choose>
                <c:when test="${empty event.result}">
                    <form action="controller?page=admin.jsp" method="post">
                        <div class="item">
                            <input type="hidden" name="command" value="ADD_RESULT_EVENT_COMMAND">
                            <input type="hidden" name="eventID" value="${event.id}">
                            <input type="text" name="result" placeholder="result" required>
                            <select required name="winnerID">
                                <option disabled selected value><fmt:message key="admin_panel_choose"/></option>
                                <c:forEach var="participant" items="${requestScope['allParticipants']}">
                                    <option value="${participant.id}">${participant.name}</option>
                                </c:forEach>
                            </select>
                            <button type="submit"><fmt:message key="admin_panel_update"/></button>
                        </div>
                    </form>
                </c:when>
            </c:choose>
        </c:forEach>
        <label for="addEvent"><fmt:message key="admin_panel_add_event"/></label>
        <form action="controller?page=admin.jsp" method="post" class="form" id="addEvent">
            <input type="hidden" name="command" value="ADD_EVENT_COMMAND">
            <input type="text" name="name" placeholder="name" required>
            <input type="text" name="description" placeholder="description" required>
            <input type="datetime-local" name="start_time" id="start_time" required>
            <select required name="participants" multiple size="2">
                <option disabled selected value><fmt:message key="admin_panel_choose"/></option>
                <c:forEach var="participant" items="${requestScope['allParticipants']}">
                    <option value="${participant.id}">${participant.name}</option>
                </c:forEach>
            </select>
            <select required name="tournamentID">
                <option disabled selected value><fmt:message key="admin_panel_choose"/></option>
                <c:forEach var="tournament" items="${requestScope['allTournaments']}">
                    <option value="${tournament.id}">${tournament.name}</option>
                </c:forEach>
            </select>
            <button type="submit"><fmt:message key="admin_panel_add"/></button>
        </form>
    </c:when>
</c:choose>
<c:choose>
    <c:when test="${param.command eq 'GET_ALL_BET_TYPE_EVENT_COMMAND'}">
        <c:forEach var="betTypeEvent" varStatus="loop" items="${requestScope['betTypeEvents']}">
            <form action="controller?page=admin.jsp" method="post">
                <div class="item">
                    <input type="hidden" name="command" value="DELETE_BET_TYPE_EVENT_COMMAND">
                    <input type="hidden" name="betTypeEventID" value="${betTypeEvent.betTypeEventId}">
                    <p>${requestScope['currEvents'][loop.index].name}</p>
                    <p>${requestScope['currBetTypes'][loop.index].name}</p>
                    <p>${betTypeEvent.coefficient}</p>
                    <button type="submit"><fmt:message key="admin_panel_delete"/></button>
                </div>
            </form>
        </c:forEach>
        <label for="addBetTypeEvent"><fmt:message key="admin_panel_add_betTypeEvent"/></label>
        <form action="controller?page=admin.jsp" method="post" class="form" id="addBetTypeEvent">
            <input type="hidden" name="command" value="ADD_BET_TYPE_EVENT_COMMAND">
            <select required name="betTypeEventEventId">
                <option disabled selected value><fmt:message key="admin_panel_choose"/></option>
                <c:forEach var="event" items="${requestScope['allEvents']}">
                    <option value="${event.id}">${event.name}</option>
                </c:forEach>
            </select>
            <select required name="betTypeEventBetTypeId">
                <option disabled selected value><fmt:message key="admin_panel_choose"/></option>
                <c:forEach var="betType" items="${requestScope['allBetTypes']}">
                    <option value="${betType.id}">${betType.name}</option>
                </c:forEach>
            </select>
            <input type="number" step="0.01" name="betTypeEventCoefficient">
            <button type="submit"><fmt:message key="admin_panel_add"/></button>
        </form>
    </c:when>
</c:choose>

</body>
</html>