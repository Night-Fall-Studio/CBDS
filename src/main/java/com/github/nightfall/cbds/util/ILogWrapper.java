package com.github.nightfall.cbds.util;

/**
 * A simple API class that allows CBDS to print warnings, errors,
 * and more to the console when something wrong happens.
 *
 * @author Mr Zombii
 * @since 1.0.0
 */
public interface ILogWrapper {

    /**
     * The basic informational logging method
     */
    void info(String s);

    /**
     * The warning logging method for non-fatal errors.
     */
    void warn(String s);

    /**
     * The error logging method for possible fatal errors.
     */
    void error(String s);

    /**
     * The debug logging method for the dev-environment
     */
    void debug(String s);

}
