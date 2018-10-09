<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="http://topjava.javawebinar.ru/functions" prefix="f" %>


<html>
<head>
    <title>Meals</title>
    <style type="text/css">
        .col1 {
            color: green;
        }

        .col2 {
            color: red;
        }
    </style>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<h1>Meals</h1>
<table border="2" cellpadding="8" cellspacing="0">
    <tr>
        <th>id</th>
        <th>Date</th>
        <th>Description</th>
        <th>Calories</th>
        <th></th>
        <th></th>
    </tr>
    <c:forEach items="${meals}" var="meal">
        <jsp:useBean id="meal" type="ru.javawebinar.topjava.model.MealWithExceed"/>
        <tr class="${meal.exceed ? 'col2' : 'col1'}">
            <td>${meal.id}</td>
            <td>${f:formatLocalDateTime(meal.dateTime, 'yyyy-MM-dd HH:mm')}</td>
            <td><p>${meal.description}</p></td>
            <td><p>${meal.calories}</p></td>
            <td><a href="meals?action=edit&id=${meal.id}">Edit</a></td>
            <td><a href="meals?action=delete&id=${meal.id}">Delete</a></td>
        </tr>
    </c:forEach>
</table>
<br>
<form method="post" action="meals" enctype="application/x-www-form-urlencoded">
    <p>DateTime:
        <input type="datetime-local" name="dateTime" required>

        Description:
        <input type="text" name="description" required>
        Calories:
        <input type="number" name="calories" required> &nbsp
        <button type="submit">Add meal</button>
    </p>
</form>
</body>
</html>
