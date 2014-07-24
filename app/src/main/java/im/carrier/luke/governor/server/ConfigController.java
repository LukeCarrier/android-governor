package im.carrier.luke.governor.server;

import java.util.List;

import im.carrier.luke.governor.server.resources.ConfigControllerMethod;

/**
 * Created by luke on 24/07/14.
 */
public class ConfigController {
    protected String name;
    protected String path;
    protected List<ConfigControllerMethod> methods;

    public ConfigController(String name, String path) {
        this.name = name;
        this.path = path;
    }

    public void addMethod(ConfigControllerMethod method) {
        this.methods.add(method);
    }
}
