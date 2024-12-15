package com.github.nightfall.cbds.util;

import com.github.nightfall.cbds.CBDSConstants;

/**
 * The super-simple log wrapper using nothing but basic io cals from System.out.
 *
 * @author Mr Zombii
 * @since 1.0.2
 */
public class ConsoleLogWrapper implements ILogWrapper {

    @Override
    public void info(String s) {
        System.out.println("[CBDS] (info) | " + s);
    }

    @Override
    public void warn(String s) {
        System.out.println("[CBDS] (warning) | " + s);
    }

    @Override
    public void error(String s) {
        System.out.println("[CBDS] (error) | " + s);
    }

    @Override
    public void debug(String s) {
        if (CBDSConstants.isDebug)
            System.out.println("[CBDS] (debug) | " + s);
    }
}
