package im.carrier.luke.governor.server;

import java.io.IOException;
import java.util.Properties;

import fi.iki.elonen.NanoHTTPD;

/**
 * Created by luke on 23/07/14.
 */
public class Server extends NanoHTTPD {
    public Server(int port) throws IOException {
        super(port);
    }

    @Override
    public Response serve(IHTTPSession session) {
        return new Response("<h1>CAKE</h1>");
    }
}
