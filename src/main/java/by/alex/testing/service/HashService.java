package by.alex.testing.service;

import com.lambdaworks.crypto.SCryptUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class HashService {

    /**
     * @see Logger
     */
    private static final Logger LOGGER =
            LoggerFactory.getLogger(HashService.class);

    /**
     * @see com.lambdaworks.crypto.SCrypt
     */
    private static final int N = 16;
    /**
     * @see com.lambdaworks.crypto.SCrypt
     */
    private static final int R = 16;
    /**
     * @see com.lambdaworks.crypto.SCrypt
     */
    private static final int P = 16;

    private HashService() {
    }

    /**
     * Method to hash by {@link com.lambdaworks.crypto.SCrypt}
     * {@link by.alex.testing.domain.User} password.
     *
     * @param password {@link by.alex.testing.domain.User} password
     * @return {@link String} hashed password
     */
    public static String hash(final char[] password) {
        LOGGER.debug("hashing password");
        return SCryptUtil.scrypt(String.valueOf(password), N, R, P);
    }

    /**
     * Method to check hashed {@link by.alex.testing.domain.User} password.
     *
     * @param password password that {@link by.alex.testing.domain.User} enter
     * @param hashed   hashed DB password
     * @return true if matches, false if not
     */
    public static boolean check(final String password, final char[] hashed) {
        LOGGER.debug("Checking hashed password");
        return SCryptUtil.check(password, String.valueOf(hashed));
    }
}
