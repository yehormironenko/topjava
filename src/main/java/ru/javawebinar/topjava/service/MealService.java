package ru.javawebinar.topjava.service;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDateTime;
import java.util.Collection;

public interface MealService {
    Meal create(Meal meal);

    Meal update(Meal meal, int userId) throws NotFoundException;

    void delete(int id, int userId) throws NotFoundException;

    Meal get(int id, int userId) throws NotFoundException;

    Collection<Meal> getAll(int userId);

    Collection<Meal> getWithFilterByTime(LocalDateTime startTime, LocalDateTime endTime, int userId);
}