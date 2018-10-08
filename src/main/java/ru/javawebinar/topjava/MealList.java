package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealWithExceed;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;

public class MealList {

    public static final Meal M1;
    public static final Meal M2;
    public static final Meal M3;
    public static final Meal M4;
    public static final Meal M5;
    public static final Meal M6;

    static {
        M1 = new Meal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500);
        M2 = new Meal(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000);
        M3 = new Meal(LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500);
        M4 = new Meal(LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000);
        M5 = new Meal(LocalDateTime.of(2015, Month.MAY, 31, 0, 0), "Обед", 500);
        M6 = new Meal(LocalDateTime.of(2015, Month.MAY, 31, 23, 59), "Ужин", 510);

    }

    public static List<MealWithExceed> exceedsList() {
        List<Meal> meals = Arrays.asList(M1, M2, M3, M4, M5, M6);
        return MealsUtil.getFilteredWithExceeded(meals, LocalTime.of(0, 0), LocalTime.of(23, 59), 2000);

    }
}
