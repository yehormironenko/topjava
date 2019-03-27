package ru.javawebinar.topjava.repository.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.AbstractNamedEntity;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Repository
@Transactional(readOnly = true)
public class JdbcUserRepositoryImpl implements UserRepository {

    private static final BeanPropertyRowMapper<User> ROW_MAPPER = BeanPropertyRowMapper.newInstance(User.class);

    private final JdbcTemplate jdbcTemplate;

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private final SimpleJdbcInsert insertUser;

    @Autowired
    public JdbcUserRepositoryImpl(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.insertUser = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("users")
                .usingGeneratedKeyColumns("id");

        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    @Transactional
    public User save(User user) {
        BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource(user);
        String sqlInsert = "INSERT into user_roles (user_id, role) values (?, ?)";
        String sqlDelete = "DELETE FROM user_roles where user_id=? and role=?";
        if (user.isNew()) {
            Number newKey = insertUser.executeAndReturnKey(parameterSource);
            user.setId(newKey.intValue());
            batchActionWithRoles(user, sqlInsert, new ArrayList<>(user.getRoles()));
        } else {
            if (namedParameterJdbcTemplate.update(
                    "UPDATE users SET name=:name, email=:email, password=:password, " +
                            "registered=:registered, enabled=:enabled, calories_per_day=:caloriesPerDay WHERE id=:id", parameterSource) == 0) {
                return null;
            }
            List<Role> rolesDb = getRoles(user);
            List<Role> rolesInQuery = new ArrayList<>(user.getRoles());

            List<Role> forDelete = rolesDb.stream().filter(role -> rolesInQuery.stream().noneMatch(role1 -> role.name()
                    .equals(role1.name()))).collect(Collectors.toList());

            List<Role> forInsert = rolesInQuery.stream().filter(role -> rolesDb.stream().noneMatch(role1 -> role.name()
                    .equals(role1.name()))).collect(Collectors.toList());

            if (forDelete.size() != 0) {
                batchActionWithRoles(user, sqlDelete, forDelete);
            }

            if (forInsert.size() != 0) {
                batchActionWithRoles(user, sqlInsert, forInsert);
            }
        }
        return user;
    }


    @Override
    @Transactional
    public boolean delete(int id) {
        return jdbcTemplate.update("DELETE FROM users WHERE id=?", id) != 0;
    }

    @Override
    public User get(int id) {
        List<User> users = getUserWithRoles("SELECT * FROM users u LEFT JOIN user_roles ur" +
                " ON u.id = ur.user_id where u.id=? ORDER BY u.name, u.email ", id);
        return DataAccessUtils.singleResult(users);
    }

    @Override
    public User getByEmail(String email) {
        List<User> users = getUserWithRoles("SELECT * FROM users u LEFT JOIN user_roles ur" +
                " ON u.id = ur.user_id where u.email=? ORDER BY u.name, u.email ", email);
        return DataAccessUtils.singleResult(users);
    }

    @Override
    public List<User> getAll() {
        return getUserWithRoles("SELECT * FROM users u LEFT JOIN user_roles ur" +
                " ON u.id = ur.user_id ORDER BY u.name, u.email ");
    }

    private List<Role> getRoles(User user) {
        return jdbcTemplate.queryForList("SELECT role FROM user_roles WHERE user_id = ?", Role.class, user.getId());
    }

    private List<User> getUserWithRoles(String sql, Object... objects) {
        return jdbcTemplate.query(sql, rs -> {
            Map<Integer, User> map = new HashMap<>();
            while (rs.next()) {
                int id = rs.getInt("id");
                User user = map.get(id);
                Role role = Role.valueOf(rs.getString("role"));
                if (user == null) {
                    user = new User(id, rs.getString("name"), rs.getString("email"),
                            rs.getString("password"), rs.getInt("calories_per_day"),
                            rs.getBoolean("enabled"), rs.getDate("registered"), EnumSet.of(role));
                    map.put(id, user);
                } else {
                    user.getRoles().add(role);
                }
            }
            List<User> users = new ArrayList<>(map.values());
            users.sort(Comparator.comparing((Function<User, String>) AbstractNamedEntity::getName).thenComparing(User::getEmail));
            return users;
        }, objects);
    }

    private void batchActionWithRoles(User user, String sql, List<Role> roles) {
        jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                for (Role r : roles) {
                    ps.setInt(1, user.getId());
                    ps.setString(2, r.name());
                }
            }

            @Override
            public int getBatchSize() {
                return roles.size();
            }
        });

    }

}