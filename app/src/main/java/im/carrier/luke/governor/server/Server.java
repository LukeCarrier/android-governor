package im.carrier.luke.governor.server;

import android.content.Context;

import java.io.IOException;

import fi.iki.elonen.NanoHTTPD;
import im.carrier.luke.governor.config.Configuration;
import im.carrier.luke.governor.config.Route;

/**
 * Created by luke on 23/07/14.
 */
public class Server extends NanoHTTPD {
    protected Configuration config;
    protected Context appContext;

    public Server(Context context, Configuration config) throws IOException {
        super(config.getPort());

        this.appContext = context;
        this.config = config;
    }

    @Override
    public Response serve(IHTTPSession session) {
        String path = session.getUri();

        Route route;
        try {
            route = config.getRoute(path);
            return route.getResponse(appContext, session);
        } catch (IOException e) {
            return new Response(Response.Status.NOT_FOUND, MIME_PLAINTEXT, "404 Not Found");
        }
    }
}
