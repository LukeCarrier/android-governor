package im.carrier.luke.governor.server;

/**
 * Controller response.
 */
public class ControllerResponse {
    /**
     * Errors/warnings which occurred during the processing of the request.
     */
    public String[] errors;

    /**
     * The response data.
     */
    public Object data;

    /**
     * Constructor.
     *
     * @param data   The response data.
     * @param errors Errors/warnings which occurred during the processing of the request.
     */
    public ControllerResponse(Object data, String[] errors) {
        this.data = data;
        this.errors = errors;
    }
}
