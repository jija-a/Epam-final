package by.alex.testing.service;

import com.lambdaworks.crypto.SCryptUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HashService {

    private static final Logger logger =
            LoggerFactory.getLogger(HashService.class);

    private HashService(){
    }

    public static String hash(char[] password) {
        logger.info("hashing password");
        return SCryptUtil.scrypt(String.valueOf(password), 16, 16, 16);
    }

    public static boolean check(String password, char[] hashed) {
        logger.info("Checking hashed password");
        return SCryptUtil.check(password, String.valueOf(hashed));
    }
}
