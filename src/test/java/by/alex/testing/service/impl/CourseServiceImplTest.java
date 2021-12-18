package by.alex.testing.service.impl;

import org.testng.Assert;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class CourseServiceImplTest {

    @Test
    public void whenGetInstanceReturnNotNull() {
        Assert.assertNotNull(CourseServiceImpl.getInstance());
    }
}