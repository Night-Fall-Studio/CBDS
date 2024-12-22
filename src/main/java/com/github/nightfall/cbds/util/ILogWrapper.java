package com.github.nightfall.cbds.util;

/**
 * A simple API class that allows CBDS to print warnings, errors,
 * and more to the console when something wrong happens.
 *
 * @author Mr Zombii
 * @since 1.0.0
 *
 * @apiNote set CBDSConstants.LOGGER with whatever logger you want to use for CBDS.
 */
public interface ILogWrapper {

    /**
     * Logs basic information.
     *
     * @param s The message being logged.
     */
    void info(String s);

    /**
     * Logs out information as a warning.
     *
     * @param s The message being logged.
     */
    void warn(String s);

    /**
     * Logs out information as an error which could be a fatal error.
     *
     * @param s The message being logged.
     */
    void error(String s);

    /**
     * The debug logging method for the dev-environment
     *
     * @param s The message being logged.
     */
    void debug(String s);

}
