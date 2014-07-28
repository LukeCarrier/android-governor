package im.carrier.luke.governor.config.route;

import android.content.Context;
import android.content.res.AssetManager;

import java.io.IOException;
import java.io.InputStream;

import fi.iki.elonen.NanoHTTPD;
import im.carrier.luke.governor.config.Route;

/**
 * Static asset route.
 */
public class AssetRoute implements Route {
    /**
     * The path to the file within the Android asset manager.
     */
    protected String file;

    /**
     * Constructor.
     *
     * @param file The path to the file within the Android asset manager.
     */
    public AssetRoute(String file) {
        this.file = file;
    }

    @Override
    public NanoHTTPD.Response getResponse(Context appContext, NanoHTTPD.IHTTPSession session) {
        AssetManager assetMgr = appContext.getAssets();

        try {
            InputStream fileStream = assetMgr.open(file.substring(1));
            byte[] fileBuffer = new byte[fileStream.available()];
            fileStream.read(fileBuffer);
            fileStream.close();
            return new NanoHTTPD.Response(new String(fileBuffer));
        } catch (IOException e) {
            return new NanoHTTPD.Response(NanoHTTPD.Response.Status.NOT_FOUND,
                    NanoHTTPD.MIME_PLAINTEXT, "404 Not Found");
        }
    }
}
