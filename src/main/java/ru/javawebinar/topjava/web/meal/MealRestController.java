package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealWithExceed;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static ru.javawebinar.topjava.util.ValidationUtil.assureIdConsistent;
import static ru.javawebinar.topjava.util.ValidationUtil.checkNew;

@Controller
public class MealRestController {
    private MealService service;
    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    public MealRestController(MealService service) {
        this.service = service;
    }

    public Meal get(int id) {
        int userId = SecurityUtil.authUserId();
        log.info("get meal{}, user{}", id, userId);
        return service.get(id, userId);
    }

    public void delete(int id) {
        int userId = SecurityUtil.authUserId();
        log.info("delete meal{}, user{}", id, userId);
        service.delete(id, userId);
    }

    public Meal create(Meal meal) {
        int userId = SecurityUtil.authUserId();
        checkNew(meal);
        log.info("create {}, user {}", meal, userId);
        return service.create(meal);
    }

    public void update(Meal meal, int id) {
        int userId = SecurityUtil.authUserId();
        assureIdConsistent(meal, id);
        log.info("update {}, user {}", meal, userId);
        service.update(meal, userId);
    }

    public List<MealWithExceed> getAll() {
        int userId = SecurityUtil.authUserId();
        log.info("getAll, user {}", userId);
        return MealsUtil.getWithExceeded(service.getAll(userId), SecurityUtil.authUserCaloriesPerDay());
    }

    public List<MealWithExceed> getWithFilterByTime(LocalDate startDate, LocalDate endDate, LocalTime startTime, LocalTime endTime) {
        int userId = SecurityUtil.authUserId();
        log.info("getWithFilterByTime date:{} - {}, time:{} - {}, user{}", startDate, endDate, startTime, endTime, userId);

        List<Meal> mealByDate = (List<Meal>) service.getWithFilterByTime(
                LocalDateTime.of(
                        startDate == null ? LocalDate.of(1, 1, 1) : startDate, LocalTime.MIN),
                LocalDateTime.of(
                        endDate == null ? LocalDate.of(3000, 1, 1) : endDate, LocalTime.MAX)
                , userId);

        return MealsUtil.getFilteredWithExceeded(mealByDate, SecurityUtil.authUserCaloriesPerDay(),
                startTime == null ? LocalTime.MIN : startTime,
                endTime == null ? LocalTime.MAX : endTime
        );
    }

}