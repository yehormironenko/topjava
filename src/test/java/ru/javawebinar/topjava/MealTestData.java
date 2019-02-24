package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;

public class MealTestData {

    public static final int MEAL_USER_ID = START_SEQ;
    public static final int MEAL_ADMIN_ID = START_SEQ + 1;

    public static final Meal MEAL1;
    public static final Meal MEAL2;
    public static final Meal MEAL3;
    public static final Meal MEAL4;
    public static final Meal MEAL5;
    public static final Meal MEAL6;

    static {
        MEAL1 = new Meal(MEAL_USER_ID + 2, LocalDateTime.of(2019, Month.FEBRUARY, 22, 15, 0), "Breakfast", 500);
        MEAL2 = new Meal(MEAL_USER_ID + 3, LocalDateTime.of(2019, Month.FEBRUARY, 23, 10, 0), "Lunch", 1000);
        MEAL3 = new Meal(MEAL_USER_ID + 4, LocalDateTime.of(2019, Month.FEBRUARY, 23, 20, 0), "Dinner", 510);
        MEAL4 = new Meal(MEAL_ADMIN_ID + 4, LocalDateTime.of(2019, Month.FEBRUARY, 22, 10, 0), "Breakfast", 500);
        MEAL5 = new Meal(MEAL_ADMIN_ID + 5, LocalDateTime.of(2019, Month.FEBRUARY, 22, 13, 0), "Lunch", 1000);
        MEAL6 = new Meal(MEAL_ADMIN_ID + 6, LocalDateTime.of(2019, Month.FEBRUARY, 23, 21, 0), "Dinner", 500);
    }

    public static void assertMatch(Meal actual, Meal expected) {
        assertThat(actual).isEqualToComparingFieldByField(expected);
    }

    public static void assertMatch(Iterable<Meal> actual, Meal... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    public static void assertMatch(Iterable<Meal> actual, Iterable<Meal> expected) {
        assertThat(actual).usingFieldByFieldElementComparator().isEqualTo(expected);
    }

}
