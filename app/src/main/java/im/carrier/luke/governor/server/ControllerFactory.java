package im.carrier.luke.governor.server;

/**
 * Controller factory.
 */
public class ControllerFactory {
    /**
     * Class name format string.
     */
    public static final String CLASS_FORMAT = "im.carrier.luke.governor.controller.%sController";

    /**
     * Singleton instance.
     */
    private static ControllerFactory instance;

    /**
     * Get the singleton instance.
     *
     * @return The singleton instance.
     */
    public static ControllerFactory getInstance() {
        if (instance == null) {
            instance = new ControllerFactory();
        }

        return instance;
    }

    /**
     * Get a controller by name.
     *
     * @param name The name of the controller to retrieve.
     * @return An instance of the designated controller.
     * @throws ClassNotFoundException
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    public ControllerClassObjectPair getController(String name)
            throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        String clsName = String.format(CLASS_FORMAT, name);

        Class<?> cls = Class.forName(clsName);

        return new ControllerClassObjectPair(cls, (Controller) cls.newInstance());
    }
}
