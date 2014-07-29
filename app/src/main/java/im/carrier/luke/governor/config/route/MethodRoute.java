package im.carrier.luke.governor.config.route;

import android.content.Context;

import fi.iki.elonen.NanoHTTPD;
import im.carrier.luke.governor.config.Route;
import im.carrier.luke.governor.server.ControllerClassObjectPair;
import im.carrier.luke.governor.server.ControllerFactory;

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
     * HTTP method
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
    public NanoHTTPD.Response getResponse(Context appContext, NanoHTTPD.IHTTPSession session) {
        try {
            ControllerClassObjectPair controllerPair = ControllerFactory.getInstance().getController(controller);

            return (NanoHTTPD.Response) controllerPair.getCls().getMethod(method).invoke(controllerPair.getController());
        } catch (Exception e) {
            return new NanoHTTPD.Response(NanoHTTPD.Response.Status.INTERNAL_ERROR,
                    NanoHTTPD.MIME_PLAINTEXT, "500 Internal Error");
        }
    }
}
