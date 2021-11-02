package by.alex.testing.dao;

import by.alex.testing.domain.User;

public interface UserDao extends Dao<User, Long> {

    User readByLogin(String login) throws DaoException;
}
