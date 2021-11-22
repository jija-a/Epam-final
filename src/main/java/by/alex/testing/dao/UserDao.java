package by.alex.testing.dao;

import by.alex.testing.domain.User;

import java.util.List;

public interface UserDao {

    User readByLogin(String login) throws DaoException;

    List<User> readByCourseId(Long id) throws DaoException;

    List<User> readAll(int start, int total) throws DaoException;

    List<User> readByName(int start, int total, String name) throws DaoException;

    Integer count() throws DaoException;

    Integer count(String search) throws DaoException;
}
