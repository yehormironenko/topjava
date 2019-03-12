package ru.javawebinar.topjava.repository.jdbc;

import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.LocalDateTime;


/*Version 2.4.1 for Java 8 supports java.time classes in JDBC and adds many enhancements.*/
@Repository
@Profile("hsqldb")
public class JdbcHsqldbMealRepositoryImpl extends AbstractJdbcMealRepositoryImpl {

    protected JdbcHsqldbMealRepositoryImpl(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        super(jdbcTemplate, namedParameterJdbcTemplate);
    }

    @Override
    public Object dateTimeForDb(LocalDateTime localDateTime) {
        return Timestamp.valueOf(localDateTime);
    }
}
