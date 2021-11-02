package by.alex.testing.dao;

import by.alex.testing.domain.Test;

import java.util.List;

public interface TestDao extends Dao<Test, Long> {

    List<Test> readAllTestsByCourseId(long id) throws DaoException;
}
