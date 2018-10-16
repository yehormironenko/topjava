package ru.javawebinar.topjava.repository.mock;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Repository
public class InMemoryMealRepositoryImpl implements MealRepository {
    private Map<Integer, Meal> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.MEALS.forEach(this::save);

    }

    @Override
    public Meal save(Meal meal) {
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            repository.put(meal.getId(), meal);
            return meal;
        }
        // treat case: update, but absent in storage
        return repository.computeIfPresent(meal.getId(), (id, oldMeal) -> meal);
    }

    @Override
    public boolean delete(int id, int userId) {
        if (repository.get(id).getUserId() == userId) {
            repository.remove(id);
            return true;
        }
        return false;
    }

    @Override
    public Meal get(int id, int userId) {
        if (repository.get(id).getUserId() != userId) {
            return null;
        }
        return repository.get(id);
    }

    @Override
    public List<Meal> getAll(int userId) {
        return repository.values().stream().filter(s -> s.getUserId().equals(userId)).
                sorted(Comparator.comparing(Meal::getDateTime).reversed()).
                collect(Collectors.toList());
    }

    @Override
    public List<Meal> getWithFilterByTime(LocalDateTime startTime, LocalDateTime endTime, int userId) {
        return repository.values().stream().filter(s -> s.getUserId().equals(userId)).
                filter(s -> DateTimeUtil.isBetween(s.getDateTime(), startTime, endTime)).
                sorted(Comparator.comparing(Meal::getDateTime).reversed()).
                collect(Collectors.toList());

    }

}

