package im.carrier.luke.governor.server;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import fi.iki.elonen.NanoHTTPD;

/**
 * Created by luke on 23/07/14.
 */
public class Server extends NanoHTTPD {
    protected Context context;

    public Server(Context context, int port) throws IOException {
        super(port);

        this.context = context;
    }

    @Override
    public Response serve(IHTTPSession session) {
        String uri = session.getUri();

        // Probably want a hash table here
        if (uri.equals("/")) {
            try {
                return respondWithStaticFile("htdocs/index.html");
            } catch (IOException e) {
                return respondWithStatus(Response.Status.INTERNAL_ERROR);
            }
        }

        return respondWithStatus(Response.Status.NOT_FOUND);
    }

    protected Response respond(String content) {
        return new Response(content);
    }

    protected Response respondWithStatus(Response.IStatus status) {
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
