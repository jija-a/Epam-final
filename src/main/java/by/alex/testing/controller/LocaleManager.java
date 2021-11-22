package by.alex.testing.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Locale;

public enum LocaleManager {
    ENG("en", new Locale("en", "US")),
    RUS("ru", new Locale("ru", "RU"));

    private static final Logger logger =
            LoggerFactory.getLogger(LocaleManager.class);

    private final Locale locale;

    private final String name;

    LocaleManager(String name, Locale locale) {
        this.name = name;
        this.locale = locale;
    }

    public static Locale resolveLocale(String inputLanguage) {
        for (LocaleManager currentLocale : LocaleManager.values()) {
            if (currentLocale.name.equals(inputLanguage)) {
                return currentLocale.locale;
            }
        }
        logger.warn("locale {} is not found, set default locale is {}", inputLanguage, ENG.locale);
        return ENG.locale;
    }
}
