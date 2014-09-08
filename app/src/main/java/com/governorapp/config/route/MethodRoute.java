package com.governorapp.config.route;

import android.content.Context;
import android.util.Log;

import com.governorapp.BuildConfig;
import com.governorapp.config.Configuration;
import com.governorapp.config.Route;
import com.governorapp.server.Controller;
import com.governorapp.server.ControllerClassObjectPair;
import com.governorapp.server.ControllerFactory;

import java.lang.reflect.Method;
import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import fi.iki.elonen.NanoHTTPD;

/**
 * Dynamic method route.
 */
public class MethodRoute implements Route {
    /**
     * Controller name.
     */
    protected String controller;

    /**
     * Method name.
     */
    protected String method;

    /**
     * HTTP method.
     */
    protected String verb;

    /**
     * Method parameters.
     */
    private Map<String, Class<?>> parameters;

    /**
     * Constructor.
     *
     * @param controller The name of the controller.
     * @param method     The name of the method.
     * @param verb       The HTTP verb.
     */
    public MethodRoute(String controller, String method, String verb) {
        this.controller = controller;
        this.method = method;
        this.verb = verb;
    }

    /**
     * Constructor (with parameters).
     *
     * @param controller The name of the controller.
     * @param method     The name of the method.
     * @param verb       The HTTP verb.
     * @param parameters The parameters to pass to the method.
     */
    public MethodRoute(String controller, String method, String verb, Map<String, Class<?>> parameters) {
        this(controller, method, verb);

        setParameters(parameters);
    }

    /**
     * Get the controller and execute the method.
     *
     * @param appContext Android application context.
     * @param config Governor server configuration.
     * @param session The HTTP session containing the request.
     *
     * @return An HTTP response for NanoHttpd.
     */
    @Override
    public NanoHTTPD.Response getResponse(Context appContext, Configuration config, NanoHTTPD.IHTTPSession session) {
        try {
            ControllerClassObjectPair controllerPair = ControllerFactory.getInstance().getController(controller, appContext, config);

            Class<?> controllerClass = controllerPair.getCls();
            Controller controller = controllerPair.getController();

            Class[] parameterTypes;
            try {
                parameterTypes = parameters.values().toArray(new Class[parameters.size()]);
            } catch (NullPointerException ex) {
                // There are no parameters to handle
                parameterTypes = null;
            }
            Method controllerMethod = controllerClass.getDeclaredMethod(method, parameterTypes);

            return (NanoHTTPD.Response) controllerMethod.invoke(controller);
        } catch (Exception e) {
            if (BuildConfig.DEBUG) {
                Log.e("com.governorapp", "exception when executing method", e);
            }

            return new NanoHTTPD.Response(NanoHTTPD.Response.Status.INTERNAL_ERROR,
                    NanoHTTPD.MIME_PLAINTEXT, "500 Internal Error");
        }
    }

    /**
     * Set parameters to pass to the method.
     *
     * @param parameters The parameters to pass to the method.
     */
    private void setParameters(Map<String, Class<?>> parameters) {
        this.parameters = parameters;
    }
}
