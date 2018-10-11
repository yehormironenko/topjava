package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.MealListTemp;
import ru.javawebinar.topjava.repository.MealMemoryRepository;
import ru.javawebinar.topjava.repository.Repository;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Objects;


import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {

    private static Logger log = getLogger(MealServlet.class);
    private Repository repository;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        repository = new MealMemoryRepository();
        MealListTemp.Initializer(repository);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("UTF-8");
        String id = request.getParameter("id");
        Meal m = new Meal(id == null ? null : Integer.parseInt(id), LocalDateTime.parse(request.getParameter("dateTime")),
                request.getParameter("description"),
                Integer.parseInt(request.getParameter("calories")));
        String logStr = "{id:" + m.getId() + ", date:" + m.getDateTime() + ", description:" + m.getDescription() + ", calories:" + m.getCalories() + " }";
        log.info(id == null ? "Create:" + logStr : "Update:" + logStr);
        repository.save(m);
        response.sendRedirect("meals");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws
            ServletException, IOException {
        log.debug("redirect to meals");
        String action = request.getParameter("action");

        switch (action == null ? action = "default" : action) {
            case "delete":
                int id = getId(request);
                log.info("Delete: {id:" + repository.get(id).getId() + ", date:" + repository.get(id).getDateTime() + ", description:" + repository.get(id).getDescription() + ", calories:" + repository.get(id).getCalories() + " }");
                repository.delete(id);
                response.sendRedirect("meals");
                break;
            case "edit":
            case "add":
                Meal m = action.equals("add") ? new Meal(LocalDateTime.of(0000, Month.JANUARY, 1, 0, 0), "", 0) : repository.get(getId(request));
                request.setAttribute("meal", m);
                log.debug("redirect to edit");
                request.getRequestDispatcher("/edit.jsp").forward(request, response);
                break;
            case "default":
            default:
                request.setAttribute("meals", MealListTemp.exceedsList(repository.getAll()));
                request.getRequestDispatcher("meals.jsp").forward(request, response);
        }
    }

    private Integer getId(HttpServletRequest request) {
        return Integer.parseInt(Objects.requireNonNull(request.getParameter("id")));
    }
}