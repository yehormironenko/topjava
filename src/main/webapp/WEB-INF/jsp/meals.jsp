<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://topjava.javawebinar.ru/functions" %>
<html>
<jsp:include page="fragments/headTag.jsp"/>
<body>
<script type="text/javascript" src="resources/js/topjava.common.js" defer></script>
<script type="text/javascript" src="resources/js/topjava.meals.js" defer></script>
<jsp:include page="fragments/bodyHeader.jsp"/>


<div class="jumbotron pt-4">
    <div class="container">

        <h3><spring:message code="meal.title"/></h3>
        <form id="filter" class="form-inline" method="post" action="meals/filter">
            <div class="grid">

                <div class="row mt-2">
                    <span class="input-group-text" id="startDate" style="width:120px;"><spring:message
                            code="meal.startDate"/></span>
                    <div class="col-5">
                        <input type="date" class="form-control" name="startDate" value="${param.startDate}">
                    </div>
                </div>

                <div class="row mt-2">
                    <span class="input-group-text" id="endDate" style="width:120px;"><spring:message
                            code="meal.endDate"/></span>
                    <div class="col-5">
                        <input type="date" class="form-control" name="endDate" value="${param.endDate}">
                    </div>
                </div>

                <div class="row mt-2">
                    <span class="input-group-text" id="startTime" style="width:120px;"><spring:message
                            code="meal.startTime"/></span>
                    <div class="col-5">
                        <input type="time" class="form-control" name="startTime" value="${param.startTime}">
                    </div>
                </div>

                <div class="row mt-2">
                    <span class="input-group-text" style="width:120px;" id="endTime"><spring:message
                            code="meal.endTime"/></span>
                    <div class="col-5 ">
                        <input type="time" class="form-control" name="endTime" value="${param.endTime}">
                    </div>
                </div>
            </div>
        </form>

        <div class="row mt-2">
            <button class="btn btn-primary mt-2" onclick="filteredData()">
                <span class="fa fa-filter"></span>
                <spring:message code="meal.filter"/>
            </button>
            <div class="col-5">
                <button class="btn btn-primary mt-2" onclick="resetFilter()">
                    <span class="fa fa-close"></span>
                    <spring:message code="meal.reset"/>
                </button>
            </div>
        </div>
        <hr>
        <div class="row">
            <button class="btn btn-primary mt-2" onclick="add()">
                <span class="fa fa-plus"></span>
                <spring:message code="meal.add"/>
            </button>
        </div>
        <hr>
        <table class="table table-striped mt-3" id="datatable">
            <thead>
            <tr>
                <th><spring:message code="meal.dateTime"/></th>
                <th><spring:message code="meal.description"/></th>
                <th><spring:message code="meal.calories"/></th>
                <th></th>
                <th></th>
            </tr>
            </thead>
            <c:forEach items="${meals}" var="meal">
                <jsp:useBean id="meal" scope="page" type="ru.javawebinar.topjava.to.MealTo"/>
                <tr data-mealExcess="${meal.excess}">
                    <td>
                            <%--${meal.dateTime.toLocalDate()} ${meal.dateTime.toLocalTime()}--%>
                            <%--<%=TimeUtil.toString(meal.getDateTime())%>--%>
                            <%--${fn:replace(meal.dateTime, 'T', ' ')}--%>
                            ${fn:formatDateTime(meal.dateTime)}
                    </td>
                    <td>${meal.description}</td>
                    <td>${meal.calories}</td>
                        <%--<td><a href="meals/update?id=${meal.id}"><spring:message code="common.update"/></a></td>
                        <td><a href="meals/delete?id=${meal.id}"><spring:message code="common.delete"/></a></td>
                        --%>
                    <td><a><span class="fa fa-pencil" style="color: black"></span></a></td>
                    <td><a onclick="deleteRow(${meal.id})"><span class="fa fa-remove" style="color: black"></span></a>
                    </td>
                </tr>
            </c:forEach>
        </table>
    </div>
</div>
<div class="modal fade" tabindex="-1" id="editRow">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h4 class="modal-title"><spring:message code="meal.add"/></h4>
                <button type="button" class="close" data-dismiss="modal">&times;</button>
            </div>
            <div class="modal-body">
                <form id="detailsForm">
                    <input type="hidden" id="id" name="id">

                    <div class="form-group">
                        <label for="description" class="col-form-label"><spring:message
                                code="meal.description"/></label>
                        <input type="text" class="form-control" id="description" name="description"
                               placeholder="<spring:message code="meal.description"/>">
                    </div>

                    <div class="form-group">
                        <label for="calories" class="col-form-label"><spring:message code="meal.calories"/></label>
                        <input type="number" class="form-control" id="calories" name="calories"
                               placeholder="<spring:message code="meal.calories"/>">
                    </div>

                    <div class="form-group">
                        <label for="dateTime" class="col-form-label"><spring:message code="meal.dateTime"/></label>
                        <input type="datetime-local" class="form-control" id="dateTime" name="dateTime"
                               placeholder="<spring:message code="meal.dateTime"/>">
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-dismiss="modal">
                    <span class="fa fa-close"></span>
                    <spring:message code="common.cancel"/>
                </button>
                <button type="button" class="btn btn-primary" onclick="save()">
                    <span class="fa fa-check"></span>
                    <spring:message code="common.save"/>
                </button>
            </div>
        </div>
    </div>
</div>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>