package im.carrier.luke.governor.server;

import android.content.Context;
import android.content.res.AssetManager;

import java.io.IOException;
import java.io.InputStream;

import fi.iki.elonen.NanoHTTPD;

/**
 * Created by luke on 23/07/14.
 */
public class Server extends NanoHTTPD {
    protected Config config;
    protected Context context;

    public Server(Context context, Config config) throws IOException {
        super(config.getPort());

        this.context = context;
        this.config = config;
    }

    @Override
    public Response serve(IHTTPSession session) {
        String uri = session.getUri();

        // Probably want a hash table here
        if (uri.equals("/")) {
            try {
                return respondWithStaticFile("htdocs/index.html");
            } catch (IOException e) {
                return respond(Response.Status.INTERNAL_ERROR);
            }
        }

        return respond(Response.Status.NOT_FOUND);
    }

    protected Response respond(String content) {
        return new Response(content);
    }

    protected Response respond(Response.IStatus status) {
        return new Response(status, MIME_PLAINTEXT, "");
    }

    protected Response respondWithStaticFile(String filename) throws IOException {
        AssetManager assetMgr = context.getAssets();

        InputStream fileStream = assetMgr.open(filename);
        byte[] fileBuffer = new byte[fileStream.available()];
        fileStream.read(fileBuffer);
        fileStream.close();

        return respond(new String(fileBuffer));
    }
}
