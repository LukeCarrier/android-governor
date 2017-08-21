package com.governorapp.controller;

import android.content.Context;

import com.governorapp.config.Configuration;
import com.governorapp.server.Controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import fi.iki.elonen.NanoHTTPD;

/**
 * Created by lidongxu on 17/6/12.
 */

public class VideoController extends AbstractController implements Controller {
    /**
     * Constructor.
     *
     * @param appContext Application context.
     * @param config     Governor server configuration.
     */
    public VideoController(Context appContext, Configuration config) {
        super(appContext, config);
    }

    @Override
    public NanoHTTPD.Response doWork(NanoHTTPD.IHTTPSession session) {
        String path = session.getUri() ;
        if (path.contains(".mp4") || path.contains(".mov") || path.contains(".3gp")){
            try {
                FileInputStream fis = new FileInputStream(new File(path)) ;
                return new NanoHTTPD.Response(NanoHTTPD.Response.Status.OK , NanoHTTPD.MIME_MPEG , fis) ;
            } catch (FileNotFoundException e) {

            }
        }
        return new NanoHTTPD.Response(NanoHTTPD.Response.Status.NOT_FOUND, NanoHTTPD.MIME_PLAINTEXT, "404 Not Found");
    }
}
