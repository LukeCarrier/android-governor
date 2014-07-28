package im.carrier.luke.governor.config.route;

import android.content.Context;

import fi.iki.elonen.NanoHTTPD;
import im.carrier.luke.governor.config.Route;

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
     * Constructor.
     *
     * @param controller The name of the controller.
     * @param method The name of the method.
     */
    public MethodRoute(String controller, String method) {
        this.controller = controller;
        this.method = method;
    }

    @Override
    public NanoHTTPD.Response getResponse(Context appContext, NanoHTTPD.IHTTPSession session) {
        return null;
    }
}
