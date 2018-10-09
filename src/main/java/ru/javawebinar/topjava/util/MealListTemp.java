package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealWithExceed;
import ru.javawebinar.topjava.repository.Repository;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.List;

public class MealListTemp {

    private static final Meal M1;
    private static final Meal M2;
    private static final Meal M3;
    private static final Meal M4;
    private static final Meal M5;
    private static final Meal M6;

    static {
        M1 = new Meal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500);
        M2 = new Meal(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000);
        M3 = new Meal(LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500);
        M4 = new Meal(LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000);
        M5 = new Meal(LocalDateTime.of(2015, Month.MAY, 31, 0, 0), "Обед", 500);
        M6 = new Meal(LocalDateTime.of(2015, Month.MAY, 31, 23, 59), "Ужин", 510);

    }

    public static List<MealWithExceed> exceedsList(List<Meal> meals) {
        return MealsUtil.getFilteredWithExceeded(meals, LocalTime.of(0, 0), LocalTime.of(23, 59), 2000);
    }

    public static void Initializer(Repository repository) {
        repository.save(M1);
        repository.save(M2);
        repository.save(M3);
        repository.save(M4);
        repository.save(M5);
        repository.save(M6);
    }

}
