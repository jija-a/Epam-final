package by.alex.testing.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Locale;

/**
 * Class needs to resolve which {@link Locale}
 * needs to be set on view layer.
 */
public enum LocaleManager {
    /**
     * Eng locale.
     */
    ENG("en", new Locale("en", "US")),
    /**
     * Ru locale.
     */
    RUS("ru", new Locale("ru", "RU"));

    /**
     * @see Logger
     */
    private static final Logger LOGGER =
            LoggerFactory.getLogger(LocaleManager.class);

    /**
     * @see Locale
     */
    private final Locale locale;

    /**
     * {@link Locale} short name.
     */
    private final String name;

    LocaleManager(final String name, final Locale locale) {
        this.name = name;
        this.locale = locale;
    }

    /**
     * Method resolves locale by it short name sent from view.
     *
     * @param inputLanguage user chosen language
     * @return defined {@link Locale}
     */
    public static Locale resolveLocale(final String inputLanguage) {
        for (LocaleManager currentLocale : LocaleManager.values()) {
            if (currentLocale.name.equals(inputLanguage)) {
                return currentLocale.locale;
            }
        }
        LOGGER.warn("locale {} is not found, set default locale is {}",
                inputLanguage, ENG.locale);
        return ENG.locale;
    }
}
