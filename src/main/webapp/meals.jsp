<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="http://topjava.javawebinar.ru/functions" prefix="f" %>


<html>
<head>
    <title>Meals</title>
    <style type="text/css">
        td.col1 {
            background: red;
        }

        td.col2 {
            background: green;
        }
    </style>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<h1>Meals</h1>
<table border="2" cellpadding="8" cellspacing="0">
    <tr>
        <th>Date</th>
        <th>Description</th>
        <th>Calories</th>
    </tr>
    <tr>
        <c:forEach items="${meals}" var="meal">
        <jsp:useBean id="meal" type="ru.javawebinar.topjava.model.MealWithExceed"/>
        <td class="${meal.exceed ? 'col2': 'col1'}"><p>${f:formatLocalDateTime(meal.dateTime, 'yyyy-MM-dd HH:mm')}</p>
        </td>
        <td class="${meal.exceed ? 'col2': 'col1'}"><p>${meal.description}</p></td>
        <td class="${meal.exceed ? 'col2': 'col1'}"><p>${meal.calories}</p></td>
    </tr>
    </c:forEach>
</table>
</body>
</html>
