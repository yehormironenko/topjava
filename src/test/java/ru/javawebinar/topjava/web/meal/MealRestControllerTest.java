package ru.javawebinar.topjava.web.meal;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.web.AbstractControllerTest;
import ru.javawebinar.topjava.web.json.JsonUtil;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;

public class MealRestControllerTest extends AbstractControllerTest {

    private static final String REST_URL = MealRestController.REST_URL + '/';


    @Test
    void testGet() throws Exception {
        mockMvc.perform(get(REST_URL))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().string(MEALS_WITH_EXCESS_JSON));
    }

    @Test
    void testDelete() throws Exception {
        mockMvc.perform(delete(REST_URL + MEAL1_ID))
                .andDo(print())
                .andExpect(status().isNoContent());
        MealTestData.assertMatch(mealService.getAll(START_SEQ), MEAL6, MEAL5, MEAL4, MEAL3, MEAL2);
    }

    @Test
    void testGetAll() throws Exception {
        mockMvc.perform(get(REST_URL))
                .andDo(print())
                .andExpect(status().isOk());
        MealTestData.assertMatch(mealService.getAll(START_SEQ), MEALS);
    }

    @Test
    void testUpdate() throws Exception {
        Meal meal = getUpdated();

        mockMvc.perform(put(REST_URL + MEAL1_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(meal)))
                .andDo(print())
                .andExpect(status().isNoContent());
        assertMatch(mealService.get(MEAL1_ID, START_SEQ), meal);
    }

    @Test
    void testCreate() throws Exception {
        Meal meal = getCreated();
        String newMealJson = JsonUtil.writeValue(meal);

        mockMvc.perform(post(REST_URL + MEAL1_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(newMealJson))
                .andDo(print())
                .andExpect(status().isOk());

        meal.setId(START_SEQ + 10);
        List<Meal> MEALS_UPD = Stream.concat(Stream.of(meal), MEALS.stream()).collect(Collectors.toList());

        assertMatch(mealService.get(START_SEQ + 10, START_SEQ), meal);
        assertMatch(mealService.getAll(START_SEQ), MEALS_UPD);
    }

    @Test
    void testGetBetween() throws Exception {
        mockMvc.perform(get(REST_URL + "filter")
                .param("startDateTime", "2015-05-30T09:00")
                .param("endDateTime", "2015-05-30T11:00"))
            /*    .param("startTime", "09:00")
                .param("endTime", "11:00"))*/
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(content().string(MEAL_WITH_EXCEED_FILTER));
    }

}