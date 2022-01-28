package by.alex.testing.service;

public final class RegexStorage {

    private RegexStorage() {
    }

    /**
     * Regular expression for title.
     */
    public static final String TITLE_PATTERN =
            "^(?:[\\p{L}]+)(?:[\\p{L}0-9 _]*){3,25}$";

    /**
     * Regular expression for password.
     */
    public static final String PASSWORD_PATTERN =
            "^(?=.*[a-zA-Zа-яА-Я])(?=.*\\d)[[a-zA-Zа-яА-Я]\\d]{8,16}$";

    /**
     * Regular expression for login.
     */
    public static final String LOGIN_PATTERN =
            "^[a-zA-Z0-9]([._-](?![._-])|[a-zA-Z0-9]){3,253}[a-zA-Z0-9]$";

    /**
     * Regular expression for name.
     */
    public static final String NAME_PATTERN =
            "^(?:[\\p{L}]*){3,25}$";
}
