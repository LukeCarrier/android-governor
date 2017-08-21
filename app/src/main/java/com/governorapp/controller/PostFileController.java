package com.governorapp.controller;

import android.content.Context;
import android.text.TextUtils;

import com.governorapp.config.Configuration;
import com.governorapp.server.Controller;
import com.governorapp.util.Constants;
import com.governorapp.util.FileUtil;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import fi.iki.elonen.NanoHTTPD;

/**
 * Created by macoli on 17/6/9.
 */

public class PostFileController extends AbstractController implements Controller {
    /**
     * Constructor.
     *
     * @param appContext Application context.
     * @param config     Governor server configuration.
     */
    public PostFileController(Context appContext, Configuration config) {
        super(appContext, config);
    }

    @Override
    public NanoHTTPD.Response doWork(NanoHTTPD.IHTTPSession session) {
        NanoHTTPD.Method method = session.getMethod();

        Map<String, String> files = new HashMap<>();
        if (NanoHTTPD.Method.POST.equals(method) || NanoHTTPD.Method.PUT.equals(method)) {
            try {
                session.parseBody(files);
                Map<String, String> params = session.getParms();
                for (Map.Entry<String, String> entry : params.entrySet()) {
                    final String paramsKey = entry.getKey();
                    if (TextUtils.equals(paramsKey , "upload")) {
                        final String tmpFilePath = files.get(paramsKey);
                        final String fileName = params.get(paramsKey);
                        final File tmpFile = new File(tmpFilePath);
                        final File targetFile = new File(Constants.UPLOAD_PATH + fileName);
                        //a copy file methoed just what you like
                        FileUtil.copyFile(tmpFile, targetFile);

                        //maybe you should put the follow code out
                        return new NanoHTTPD.Response("Success");
                    }
                }
            } catch (IOException ioe) {
                return new NanoHTTPD.Response(NanoHTTPD.Response.Status.INTERNAL_ERROR,
                        NanoHTTPD.MIME_PLAINTEXT, "500 Internal Error");
            } catch (NanoHTTPD.ResponseException re) {
                return new NanoHTTPD.Response(NanoHTTPD.Response.Status.INTERNAL_ERROR,
                        NanoHTTPD.MIME_PLAINTEXT, "500 Internal Error");
            }
        }
        return null;
    }
}
