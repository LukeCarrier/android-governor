package im.carrier.luke.governor.controller;

import com.google.gson.Gson;

import fi.iki.elonen.NanoHTTPD;
import im.carrier.luke.governor.server.ControllerResponse;

/**
 * Abstract controller.
 */
public class AbstractController {
    /**
     * Return a successful response with data, but no errors.
     *
     * @param data The response data.
     * @return A response.
     */
    protected NanoHTTPD.Response respond(Object data) {
        return respond(data, NanoHTTPD.Response.Status.OK, null);
    }

    /**
     * Return a response with a set status and optional errors.
     *
     * @param data   The response data.
     * @param status The HTTP response status.
     * @param errors Errors/warnings which occurred during the processing of the request.
     * @return A response.
     */
    protected NanoHTTPD.Response respond(Object data, NanoHTTPD.Response.IStatus status, String[] errors) {
        ControllerResponse response = new ControllerResponse(data, errors);

        Gson gson = new Gson();
        String body = gson.toJson(response);

        return new NanoHTTPD.Response(status, "application/json", body);
    }
}
