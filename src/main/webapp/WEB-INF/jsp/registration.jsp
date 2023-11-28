<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<fmt:setLocale value='${sessionScope.language}'/>
<fmt:setBundle basename="messages"/>

<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title><fmt:message key="registration_page_title"/></title>
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

    .error {
      color: red;
    }
  </style>
</head>
<body>

<%@include file="/header.jsp"%>

<h2><fmt:message key="registration_page_heading"/></h2>

<c:if test="${not empty error}">
  <p class="error">${error}</p>
</c:if>

<form action="controller" method="post">
  <label for="login"><fmt:message key="login_label"/></label>
  <input type="text" id="login" name="login" required>
  <br>
  <label for="password"><fmt:message key="password_label"/></label>
  <input type="password" id="password" name="password" required>
  <br>
  <label for="repeat_password"><fmt:message key="repeatPassword_label"/></label>
  <input type="password" id="repeat_password" name="repeat_password" required>
  <br>
  <label for="email"><fmt:message key="email_label"/></label>
  <input type="text" id="email" name="email" required>
  <br>
  <button type="submit"><fmt:message key="register_button"/></button>
</form>

<p>
  <a href="${pageContext.request.contextPath}/controller?page=login.jsp">
    <fmt:message key="registered_message"/>
  </a>
</p>


</body>
</html>
