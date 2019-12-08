package edu.hm.launcher.config.container;


/**
 * Simple container to hold information about an App
 */
public class AppContainer {

    private final String appName;

    public AppContainer(String appName) {
        this.appName = appName;
    }

    public String getAppName() {
        return appName;
    }
}