package com.github.nightfall.odsl.util;

import com.github.nightfall.odsl.ODSLConstants;

/**
 * The super-simple log wrapper using nothing but basic io cals from System.out.
 *
 * @author Mr Zombii
 * @since 1.0.2
 */
public class ConsoleLogWrapper implements ILogWrapper {

    @Override
    public void info(String s) {
        System.out.println("[ODSL] (info) | " + s);
    }

    @Override
    public void warn(String s) {
        System.out.println("[ODSL] (warning) | " + s);
    }

    @Override
    public void error(String s) {
        System.out.println("[ODSL] (error) | " + s);
    }

    @Override
    public void debug(String s) {
        if (ODSLConstants.isDebug)
            System.out.println("[ODSL] (debug) | " + s);
    }
}
