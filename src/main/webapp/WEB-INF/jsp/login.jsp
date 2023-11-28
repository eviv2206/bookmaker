<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<fmt:setLocale value='${sessionScope.language}'/>
<fmt:setBundle basename="messages"/>
<c:set value='${sessionScope.curruser}' var="user"/>
<c:if test="${not empty user and user.privileges == true}">
    <c:redirect url="/controller?page=admin.jsp"/>
</c:if>
<c:if test="${not empty user and user.privileges == false}">
    <c:redirect url="/controller?page=bookmaker.jsp"/>
</c:if>


<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title><fmt:message key="login_page_title"/></title>
    <style>
        body {
            font-family: Arial, sans-serif;
            text-align: center;
            margin: 50px;
        }

        form {
            display: inline-block;
            text-align: left;
        }

        input {
            padding: 5px;
            margin: 5px;
        }

        button {
            padding: 8px;
            margin-top: 10px;
        }
    </style>
</head>
<body>

<%@ include file="/header.jsp"%>

<h2><fmt:message key="login_page_heading"/></h2>

<form action="controller" method="post">
    <input type="hidden" name="command" value="SIGN_IN_COMMAND">
    <label for="login"><fmt:message key="login_label"/></label>
    <input type="text" id="login" name="login" required>
    <br>
    <label for="password"><fmt:message key="password_label"/></label>
    <input type="password" id="password" name="password" required>
    <br>
    <button type="submit"><fmt:message key="signIn_button"/></button>
</form>

<p><fmt:message key="notRegistered_message"/> <a href="${pageContext.request.contextPath}/controller?page=registration.jsp"><fmt:message key="register_link"/></a></p>
</body>
</html>
