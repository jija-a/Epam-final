package by.alex.testing.service;

import com.lambdaworks.crypto.SCryptUtil;

public class HashService {

    private HashService(){
    }

    public static String hash(char[] password) {
        return SCryptUtil.scrypt(String.valueOf(password), 16, 16, 16);
    }

    public static boolean check(String password, char[] hashed) {
        return SCryptUtil.check(password, String.valueOf(hashed));
    }
}
