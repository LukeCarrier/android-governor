package com.governorapp.config.route;

import android.content.Context;

import fi.iki.elonen.NanoHTTPD;

import com.governorapp.config.Configuration;
import com.governorapp.config.Route;
import com.governorapp.server.Controller;
import com.governorapp.server.ControllerClassObjectPair;
import com.governorapp.server.ControllerFactory;

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
     * Constructor.
     *
     * @param controller The name of the controller.
     * @param method     The name of the method.
     */
    public MethodRoute(String controller, String method, String verb) {
        this.controller = controller;
        this.method = method;
        this.verb = verb;
    }

    @Override
    public NanoHTTPD.Response getResponse(Context appContext, Configuration config, NanoHTTPD.IHTTPSession session) {
        try {
            ControllerClassObjectPair controllerPair = ControllerFactory.getInstance().getController(controller, config);

            Class<?> controllerClass = controllerPair.getCls();
            Controller controller = controllerPair.getController();

            return (NanoHTTPD.Response) controllerClass.getMethod(method).invoke(controller);
        } catch (Exception e) {
            return new NanoHTTPD.Response(NanoHTTPD.Response.Status.INTERNAL_ERROR,
                    NanoHTTPD.MIME_PLAINTEXT, "500 Internal Error");
        }
    }
}
