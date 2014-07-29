package im.carrier.luke.governor.server;

/**
 * Controller class and object pair.
 */
public class ControllerClassObjectPair {
    /**
     * Controller class.
     */
    protected Class cls;

    /**
     * Controller object.
     */
    protected Controller controller;

    /**
     * Constructor.
     *
     * @param cls        The controller class.
     * @param controller An instance of the controller class.
     */
    public ControllerClassObjectPair(Class cls, Controller controller) {
        this.cls = cls;
        this.controller = controller;
    }

    /**
     * Get the controller class.
     *
     * @return The controller class.
     */
    public Class getCls() {
        return cls;
    }

    /**
     * Get the controller instance.
     *
     * @return The controller instance.
     */
    public Controller getController() {
        return controller;
    }
}
