package by.alex.testing.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Utility class that changes locale.
 * Gets localized messages from resource file.
 */
public final class MessageManager {

    /**
     * @see Logger
     */
    private static final Logger LOGGER =
            LoggerFactory.getLogger(MessageManager.class);

    /**
     * {@link MessageManager} instance. Signleton pattern.
     */
    public static final MessageManager INSTANCE = new MessageManager();

    /**
     * Locale file name.
     */
    private static final String RESOURCES = "page_content";

    /**
     * @see ResourceBundle
     */
    private ResourceBundle resourceBundle;


    private MessageManager() {
        LOGGER.debug("Initializing Message Manager");
        resourceBundle =
                ResourceBundle.getBundle(RESOURCES, Locale.getDefault());
    }

    /**
     * Method gets localized message from resource file.
     *
     * @param key {@link String} property key
     * @return {@link String} message from file
     */
    public String getMessage(final String key) {
        LOGGER.trace("Getting message from resource file");
        return resourceBundle.getString(key);
    }

    /**
     * Method changes locale.
     *
     * @param locale {@link Locale}
     */
    public void changeLocale(final Locale locale) {
        LOGGER.debug("Changing locale");
        resourceBundle = ResourceBundle.getBundle(RESOURCES, locale);
    }

}
