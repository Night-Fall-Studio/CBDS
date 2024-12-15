package com.github.nightfall.cbds.util;

/**
 * The do-nothing log wrapper for commercial use applications
 * that require no logging for release.
 *
 * @author Mr Zombii
 * @since 1.0.2
*/
public class SilentLogWrapper implements ILogWrapper {

    @Override
    public void info(String s) {}

    @Override
    public void warn(String s) {}

    @Override
    public void error(String s) {}

    @Override
    public void debug(String s) {}
}
