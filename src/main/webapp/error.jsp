<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Error Page</title>
</head>
<body>
<h2>Error Page</h2>
<p>An error occurred. Please try again later.</p>
<p>Error details: <c:out value="${requestScope.error}" /></p>
</body>
</html>