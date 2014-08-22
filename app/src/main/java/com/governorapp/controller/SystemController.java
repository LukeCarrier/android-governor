package com.governorapp.controller;

import android.content.Context;
import android.os.Build;

import com.governorapp.config.Configuration;
import com.governorapp.server.Controller;

import java.util.HashMap;

import fi.iki.elonen.NanoHTTPD;

/**
 * System controller.
 * <p/>
 * Provides system information.
 */
public class SystemController extends AbstractController implements Controller {
    /**
     * Constructor.
     *
     * @param config
     */
    public SystemController(Context appContext, Configuration config) {
        super(appContext, config);
    }

    /**
     * Get device hardware and software build information.
     *
     * @return Device hardware and software build information from os.Build.
     * @throws NoSuchFieldException
     * @throws IllegalAccessException
     */
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
