package by.alex.testing.service.impl;

import org.testng.Assert;
import org.testng.annotations.Test;

public class AttendanceServiceImplTest {

    @Test
    public void whenGetInstanceReturnNotNull() {
        Assert.assertNotNull(AttendanceServiceImpl.getInstance());
    }
}