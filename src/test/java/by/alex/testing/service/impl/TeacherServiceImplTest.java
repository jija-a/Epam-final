package by.alex.testing.service.impl;

import org.testng.Assert;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class TeacherServiceImplTest {

    @Test
    public void whenGetInstanceReturnNotNull() {
        Assert.assertNotNull(TeacherServiceImpl.getInstance());
    }
}