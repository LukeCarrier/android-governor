package com.governorapp.server;

import com.governorapp.config.Configuration;

import java.lang.reflect.InvocationTargetException;

/**
 * Controller factory.
 */
public class ControllerFactory {
    /**
     * Class name format string.
     */
    public static final String CLASS_FORMAT = "com.governorapp.controller.%sController";

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
    public ControllerClassObjectPair getController(String name, Configuration config)
            throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException,
            InvocationTargetException, InstantiationException {
        String clsName = String.format(CLASS_FORMAT, name);

        Class<?> cls = Class.forName(clsName);

        Class[] prototype = new Class[1];
        prototype[0] = Configuration.class;

        return new ControllerClassObjectPair(cls, (Controller) cls.getDeclaredConstructor(prototype).newInstance(config));
    }
}
