package by.alex.testing.service;

import org.testng.Assert;
import org.testng.annotations.Test;

public class HashServiceTest {

    private static final char[] PSW = new char[]{'1', '2', '3', '4', '5', 'q', 'W', '@'};

    @Test
    public void testHashNotNull() {
        String hash = HashService.hash(PSW);
        Assert.assertNotNull(hash);
    }

    @Test
    public void testHashValidationSuccess(){
        String hash = HashService.hash(PSW);
        boolean actual = HashService.check(String.valueOf(PSW), hash.toCharArray());
        Assert.assertTrue(actual);
    }

    @Test
    public void testHashValidationFail(){
        String another = "123";
        String hash = HashService.hash(PSW);
        boolean actual = HashService.check(another, hash.toCharArray());
        Assert.assertFalse(actual);
    }
}
