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
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import fi.iki.elonen.NanoHTTPD;

/**
 * Dynamic method route.
 */
public class DynamicMethodRoute implements Route {
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
     * Regular expression for the route.
     */
    protected String routePathRegex;

    /**
     * Method parameters.
     */
    private Map<String, Class<?>> parameters;

    /**
     * Constructor (with parameters).
     *
     * @param controller The name of the controller.
     * @param method     The name of the method.
     * @param verb       The HTTP verb.
     * @param parameters The parameters to pass to the method.
     */
    public DynamicMethodRoute(String controller, String method, String verb, String routePathRegex, Map<String, Class<?>> parameters) {
        this.controller = controller;
        this.method = method;
        this.verb = verb;
        this.routePathRegex = routePathRegex;
        this.parameters = parameters;
    }

    /**
     * Extract parameter values from the URI.
     *
     * @param uri            The request URI.
     * @param parameterTypes The types of the parameters to extract.
     * @return
     */
    private Object[] getParameterValues(String uri, Class[] parameterTypes) {
        Object[] parameterValues = new Object[parameterTypes.length];

        Pattern pattern = Pattern.compile(routePathRegex);
        Matcher matcher = pattern.matcher(uri);

        int i = 0;
        while (matcher.find()) {
            if (parameterTypes[i] == Integer.class) {
                parameterValues[i] = Integer.valueOf(matcher.group(i + 1));
            }
            i++;
        }

        return parameterValues;
    }

    /**
     * Get the controller and execute the method.
     *
     * @param appContext Android application context.
     * @param config     Governor server configuration.
     * @param session    The HTTP session containing the request.
     * @return An HTTP response for NanoHttpd.
     */
    @Override
    public NanoHTTPD.Response getResponse(Context appContext, Configuration config, NanoHTTPD.IHTTPSession session) {
        try {
            ControllerClassObjectPair controllerPair = ControllerFactory.getInstance().getController(controller, appContext, config);

            Class<?> controllerClass = controllerPair.getCls();
            Controller controller = controllerPair.getController();

            Class[] parameterTypes = parameters.values().toArray(new Class[parameters.size()]);
            Object[] parameterValues = getParameterValues(session.getUri(), parameterTypes);
            Method controllerMethod = controllerClass.getDeclaredMethod(method, parameterTypes);

            return (NanoHTTPD.Response) controllerMethod.invoke(controller, parameterValues);
        } catch (Exception e) {
            if (BuildConfig.DEBUG) {
                Log.e("com.governorapp", "exception when executing method", e);
            }

            return new NanoHTTPD.Response(NanoHTTPD.Response.Status.INTERNAL_ERROR,
                    NanoHTTPD.MIME_PLAINTEXT, "500 Internal Error");
        }
    }
}
