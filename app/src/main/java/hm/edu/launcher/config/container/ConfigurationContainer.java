package hm.edu.launcher.config.container;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ConfigurationContainer {

    private final List<AppContainer> apps = new ArrayList<>();

    public List<AppContainer> getApps() {
        return Collections.unmodifiableList(apps);
    }

    public void add(AppContainer container) {
        apps.add(container);
    }

    public void changeAt(int id, AppContainer app) {
        apps.set(id, app);
    }

    public void removeAt(int id) {
        apps.remove(id);
    }

}
