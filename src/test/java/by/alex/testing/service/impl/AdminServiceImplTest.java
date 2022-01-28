package by.alex.testing.service.impl;

import by.alex.testing.dao.DaoException;
import by.alex.testing.dao.mysql.UserDaoImpl;
import by.alex.testing.domain.User;
import by.alex.testing.domain.UserRole;
import by.alex.testing.service.AdminService;
import by.alex.testing.service.ServiceException;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AdminServiceImplTest {

    @Test
    public void whenGetInstanceReturnNotNull() {
        Assert.assertNotNull(AdminServiceImpl.getInstance());
    }

    private AdminService adminService;
    private UserDaoImpl userDao;
    private User firstUser;

    @BeforeTest
    void setUp() {
        adminService = AdminServiceImpl.getInstance();
        userDao = mock(UserDaoImpl.class);
        firstUser = new User("login1", "firstName1", "lastName1",
                new char[]{1, 2, 3, 4, 5, 6, 'q', 'W'}, UserRole.STUDENT);
        firstUser.setId(1L);
    }

    @Test
    void deleteUserTest() throws ServiceException, DaoException {
        adminService.deleteUser(firstUser.getId());
        when(userDao.delete(firstUser.getId())).thenReturn(true);
        Assert.assertTrue(userDao.delete(firstUser.getId()));
    }
}