package com.governorapp.controller;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;

import com.governorapp.config.Configuration;
import com.governorapp.server.Controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import fi.iki.elonen.NanoHTTPD;

/**
 * Created by macoli on 17/6/9.
 */

public class SyncCuttingBoardController extends AbstractController implements Controller {
    /**
     * Constructor.
     *
     * @param appContext Application context.
     * @param config     Governor server configuration.
     */
    public SyncCuttingBoardController(Context appContext, Configuration config) {
        super(appContext, config);
    }

    @Override
    public NanoHTTPD.Response doWork(NanoHTTPD.IHTTPSession session) {

        NanoHTTPD.Method method = session.getMethod();

        Map<String, String> files = new HashMap<>();
        if (NanoHTTPD.Method.POST.equals(method) || NanoHTTPD.Method.PUT.equals(method)) {
            try {
                session.parseBody(files);

            } catch (IOException ioe) {

            } catch (NanoHTTPD.ResponseException re) {

            }
            String parameter = session.getQueryParameterString() ;
            String[] parameterList = parameter.split("=") ;
            put(parameterList[1]);
        }


        return new NanoHTTPD.Response("sync cutting board success!");
    }

    private void put(String data){
        ClipboardManager clipboard = (ClipboardManager) appContext.getSystemService(appContext.CLIPBOARD_SERVICE);
        ClipData textCd = ClipData.newPlainText(null, data);
        clipboard.setPrimaryClip(textCd);
    }

}
