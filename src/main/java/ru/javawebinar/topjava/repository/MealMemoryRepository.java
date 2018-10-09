package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;


public class MealMemoryRepository implements Repository {

    private ConcurrentMap<Integer, Meal> repository = new ConcurrentHashMap<>();
    private AtomicInteger newID = new AtomicInteger();

    @Override
    public void save(Meal meal) {
        if (checkId(meal)) {
            meal.setId(newID.incrementAndGet());
            repository.put(meal.getId(), meal);
        } else {
            repository.put(meal.getId(), meal);
        }
    }

    @Override
    public void delete(int id) {
        repository.remove(id);
    }

    @Override
    public Meal get(int id) {
        return repository.get(id);
    }

    @Override
    public List<Meal> getAll() {
        return new ArrayList<>(repository.values());
    }


    private boolean checkId(Meal meal) {
        return meal.getId() == null;
    }

}
