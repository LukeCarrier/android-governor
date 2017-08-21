package com.governorapp.controller;

import android.content.Context;

import com.google.gson.Gson;
import com.governorapp.config.Configuration;

import fi.iki.elonen.NanoHTTPD;

/**
 * Abstract controller.
 */
public abstract class AbstractController {
    /**
     * Server configuration.
     */
    protected Configuration config;

    /**
     * Application context.
     */
    protected Context appContext;

    /**
     * Constructor.
     *
     * @param appContext Application context.
     * @param config     Governor server configuration.
     */
    public AbstractController(Context appContext, Configuration config) {
        this.appContext = appContext;
        this.config = config;
    }

    abstract public NanoHTTPD.Response doWork(NanoHTTPD.IHTTPSession session) ;

    /**
     * Return a successful response with data, but no errors.
     *
     * @param data The response data.
     * @return A response.
     */
    protected NanoHTTPD.Response respond(Object data) {
        return respond(data, NanoHTTPD.Response.Status.OK);
    }

    /**
     * Return a response with a set status and optional errors.
     *
     * @param data   The response data.
     * @param status The HTTP response status.
     * @param errors Errors/warnings which occurred during the processing of the request.
     * @return A response.
     */
    protected NanoHTTPD.Response respond(Object data, NanoHTTPD.Response.IStatus status) {
        Gson gson = new Gson();
        String body = gson.toJson(data);

        return new NanoHTTPD.Response(status, "application/json", body);
    }
}
