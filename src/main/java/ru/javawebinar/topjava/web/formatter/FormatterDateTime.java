package ru.javawebinar.topjava.web.formatter;


import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface FormatterDateTime {
}
