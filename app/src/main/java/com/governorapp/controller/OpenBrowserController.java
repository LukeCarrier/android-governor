package com.governorapp.controller;

import android.content.Context;

import com.governorapp.config.Configuration;
import com.governorapp.server.Controller;

import fi.iki.elonen.NanoHTTPD;

/**
 * Created by lidongxu on 17/6/9.
 */

public class OpenBrowserController extends AbstractController implements Controller {
    /**
     * Constructor.
     *
     * @param appContext Application context.
     * @param config     Governor server configuration.
     */
    public OpenBrowserController(Context appContext, Configuration config) {
        super(appContext, config);
    }

    @Override
    protected NanoHTTPD.Response doWork(NanoHTTPD.IHTTPSession session) {
        return null;
    }
}
