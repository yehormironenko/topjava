package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;

import static ru.javawebinar.topjava.MealTestData.*;


@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {

    static {
        SLF4JBridgeHandler.install();
    }

    @Autowired
    private MealService service;

    @Test
    public void create() throws Exception {
        Meal meal = new Meal(LocalDateTime.of(2019, Month.FEBRUARY, 23, 23, 0), "Dinner2", 500);
        Meal created = service.create(meal, MEAL_USER_ID);
        assertMatch(service.getAll(MEAL_USER_ID), created, MEAL3, MEAL2, MEAL1);
    }

    @Test
    public void update() throws Exception {
        Meal updated = new Meal(MEAL1);
        updated.setDescription("Lunch");
        updated.setCalories(1231);
        service.update(updated, MEAL_USER_ID);
        assertMatch(service.getAll(MEAL_USER_ID), MEAL3, MEAL2, updated);
    }

    @Test(expected = NotFoundException.class)
    public void updateNotFound() throws Exception {
        Meal updated = new Meal(MEAL1);
        updated.setDescription("Lunch");
        updated.setCalories(1231);
        service.update(updated, MEAL_ADMIN_ID);
    }

    @Test
    public void delete() throws Exception {
        service.delete(MEAL_USER_ID + 2, MEAL_USER_ID);
        assertMatch(service.getAll(MEAL_USER_ID), MEAL3, MEAL2);
    }

    @Test(expected = NotFoundException.class)
    public void deletedNotFound() throws Exception {
        service.delete(1, 1);
    }

    @Test
    public void get() throws Exception {
        Meal meal = service.get(MEAL_USER_ID + 2, MEAL_USER_ID);
        assertMatch(meal, MEAL1);
    }

    @Test(expected = NotFoundException.class)
    public void getNotFound() throws Exception {
        Meal meal = service.get(MEAL_ADMIN_ID + 4, MEAL_USER_ID);
    }

    @Test
    public void getAll() throws Exception {
        List<Meal> meals = service.getAll(MEAL_ADMIN_ID);
        assertMatch(meals, MEAL6, MEAL5, MEAL4);
    }


    @Test
    public void getBetweenDateTimes() throws Exception {
        assertMatch(service.getBetweenDates(LocalDate.of(2019, Month.FEBRUARY, 22),
                LocalDate.of(2019, Month.FEBRUARY, 23), MEAL_USER_ID), MEAL3, MEAL2, MEAL1
        );
    }

}