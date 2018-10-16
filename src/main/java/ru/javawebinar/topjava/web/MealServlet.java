package ru.javawebinar.topjava.web;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.web.meal.MealRestController;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

public class MealServlet extends HttpServlet {

    private MealRestController mealRestController;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        ConfigurableApplicationContext appctx = new ClassPathXmlApplicationContext("spring/spring-app.xml");
        mealRestController = appctx.getBean(MealRestController.class);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action");
        if (action == null) {
            String id = request.getParameter("id");
            Meal meal = new Meal(SecurityUtil.authUserId(), LocalDateTime.parse(request.getParameter("dateTime")),
                    request.getParameter("description"), Integer.parseInt(request.getParameter("calories")));
            if (id.isEmpty()) {
                mealRestController.create(meal);
            } else mealRestController.update(meal, Integer.parseInt(id));
            response.sendRedirect("meals");
        } else if ("filter".equals(action)) {
            LocalDate startDate = request.getParameter("startDate").isEmpty() ? null : LocalDate.parse(request.getParameter("startDate"));
            LocalDate endDate = request.getParameter("endDate").isEmpty() ? null : LocalDate.parse(request.getParameter("endDate"));
            LocalTime startTime = request.getParameter("startTime").isEmpty() ? null : LocalTime.parse(request.getParameter("startTime"));
            LocalTime endTime = request.getParameter("endTime").isEmpty() ? null : LocalTime.parse(request.getParameter("endTime"));
            request.setAttribute("meals", mealRestController.getWithFilterByTime(startDate, endDate, startTime, endTime));
            request.getRequestDispatcher("/meals.jsp").forward(request, response);
        } else {
            response.sendRedirect("meals");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        switch (action == null ? "all" : action) {
            case "delete":
                int id = getId(request);
                mealRestController.delete(id);
                response.sendRedirect("meals");
                break;
            case "1":
            case "2":
                SecurityUtil.setUserid("1".equals(action) ? 1 : 2);
                request.setAttribute("meals", mealRestController.getAll());
                request.getRequestDispatcher("/meals.jsp").forward(request, response);
            case "create":
            case "update":
                final Meal meal = "create".equals(action) ?
                        new Meal(1, LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 1000) :
                        mealRestController.get(getId(request));
                request.setAttribute("meal", meal);
                request.getRequestDispatcher("/mealForm.jsp").forward(request, response);
                break;
            case "all":
            default:
                request.setAttribute("meals", mealRestController.getAll());
                request.getRequestDispatcher("/meals.jsp").forward(request, response);
                break;
        }
    }

    private int getId(HttpServletRequest request) {
        String paramId = Objects.requireNonNull(request.getParameter("id"));
        return Integer.parseInt(paramId);
    }
}
