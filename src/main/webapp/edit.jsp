<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Meal</title>
</head>
<body>
<section>
    <h3><a href="index.html">Home</a></h3>
    <h1>Edit meal</h1>
    <jsp:useBean id="meal" type="ru.javawebinar.topjava.model.Meal" scope="request"/>
    <form method="post" action="meals" enctype="application/x-www-form-urlencoded">
        <input type="hidden" name="id" value="${meal.id}">
        <dl>
            <dt>DateTime: <input type="datetime-local" value="${meal.dateTime}" name="dateTime" required></dt>
        </dl>
        <dl>
            <dt>Description: <input type="text" value="${meal.description}" name="description" required></dt>
        </dl>
        <dl>
            <dt>Calories: <input type="number" value="${meal.calories}" name="calories" required></dt>
        </dl>
        <button type="submit">Save</button>
        <button onclick="window.history.back()" type="button">Cancel</button>
    </form>
</section>
</body>
</html>
