package by.alex.testing.dao;

import by.alex.testing.domain.User;

import java.util.List;

public interface UserDao extends BaseDao<User> {

    List<User> readAll(int start, int total) throws DaoException;

    User readByLogin(String login) throws DaoException;

    List<User> readByNameOrLogin(int start, int total, String name) throws DaoException;

    List<User> readByCourseId(Long id) throws DaoException;

    List<User> readByCourseId(int start, int total, long courseId) throws DaoException;

    List<User> readByCourseId(int start, int recOnPage, long courseId, String search) throws DaoException;

    Integer count() throws DaoException;

    Integer count(String search) throws DaoException;
}
