package by.alex.testing.service.impl;

import org.testng.Assert;
import org.testng.annotations.Test;

public class AdminServiceImplTest {

    @Test
    public void whenGetInstanceReturnNotNull() {
        Assert.assertNotNull(AdminServiceImpl.getInstance());
    }
}