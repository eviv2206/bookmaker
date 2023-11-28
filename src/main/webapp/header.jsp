<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<fmt:setLocale value='${sessionScope.language}'/>
<fmt:setBundle basename="messages"/>
<c:set value='${sessionScope.curruser}' var="user"/>

<div style="display: flex; gap: 10px; justify-content: center">
    <form action="controller" method="post">
        <input type="hidden" name="command" value="CHANGE_LOCALE_COMMAND">
        <select name="language" onchange="this.form.submit()">
            <option value="en" ${sessionScope.language == 'en' ? 'selected' : ''}>EN</option>
            <option value="ru" ${sessionScope.language == 'ru' ? 'selected' : ''}>RU</option>
        </select>
    </form>
    <c:if test="${not empty user}">
        <form action="controller?page=login.jsp" method="post">
            <input type="hidden" name="command" value="LOGOUT_COMMAND">
            <button type="submit"><fmt:message key="logout_button"/></button>
        </form>
    </c:if>

</div>
