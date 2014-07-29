package im.carrier.luke.governor.controller;

import android.os.Build;

import java.util.HashMap;

import fi.iki.elonen.NanoHTTPD;
import im.carrier.luke.governor.server.Controller;

/**
 * System controller.
 * <p/>
 * Provides system information.
 */
public class SystemController extends AbstractController implements Controller {
    public NanoHTTPD.Response getBuild() throws NoSuchFieldException, IllegalAccessException {
        HashMap<String, String> buildInfo = new HashMap<String, String>();
        String[] buildInfoKeys = {"BOARD", "BOOTLOADER", "BRAND", "CPU_ABI", "CPU_ABI2", "DEVICE",
                "DISPLAY", "FINGERPRINT", "HARDWARE", "HOST", "ID", "MANUFACTURER", "MODEL",
                "PRODUCT", "RADIO", "SERIAL", "TAGS", "TIME", "TYPE", "USER"};

        for (String key : buildInfoKeys) {
            buildInfo.put(key, Build.class.getDeclaredField(key).get(null).toString());
        }

        return this.respond(buildInfo);
    }
}
