package by.alex.testing.service;

public class RegexStorage {

    private RegexStorage() {
    }

    public static final String TITLE_PATTERN = "^[a-zA-Z0-9]([ ._-](?![ ._-])|[a-zA-Z0-9]){1,33}[a-zA-Z0-9]$";

    public static final String PASSWORD_PATTERN = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,16}$";

    public static final String LOGIN_PATTERN = "^[a-zA-Z0-9]([._-](?![._-])|[a-zA-Z0-9]){3,18}[a-zA-Z0-9]$";

    public static final String NAME_PATTERN = "^[a-zA-Z]([._-](?![._-])|[a-zA-Z]){1,23}[a-zA-Z]$";
}
