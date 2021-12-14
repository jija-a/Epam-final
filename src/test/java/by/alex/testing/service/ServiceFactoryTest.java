package by.alex.testing.service;

import org.testng.Assert;
import org.testng.annotations.Test;

public class ServiceFactoryTest {

    @Test
    public void testInstanceNotNull() {
        Assert.assertNotNull(ServiceFactory.getInstance());
    }

    @Test
    public void testCommonServiceNotNull() {
        Assert.assertNotNull(ServiceFactory.getInstance().getCommonService());
    }

    @Test
    public void testTeacherServiceNotNull() {
        Assert.assertNotNull(ServiceFactory.getInstance().getTeacherService());
    }

    @Test
    public void testPaginationServiceNotNull() {
        Assert.assertNotNull(ServiceFactory.getInstance().getPaginationService());
    }

    @Test
    public void testStudentServiceNotNull() {
        Assert.assertNotNull(ServiceFactory.getInstance().getStudentService());
    }

    @Test
    public void testAdminServiceNotNull() {
        Assert.assertNotNull(ServiceFactory.getInstance().getAdminService());
    }
}
