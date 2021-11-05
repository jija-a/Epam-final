package by.alex.testing.dao;

import by.alex.testing.domain.User;

import java.util.List;

public interface UserDao extends Dao<User, Long> {

    User readByLogin(String login) throws DaoException;

    List<User> readByCourseId(Long id) throws DaoException;
}
