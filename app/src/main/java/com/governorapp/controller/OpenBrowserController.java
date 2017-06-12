package com.governorapp.controller;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;

import com.governorapp.activity.TempActivity;
import com.governorapp.config.Configuration;
import com.governorapp.server.Controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import fi.iki.elonen.NanoHTTPD;

/**
 * Created by macoli on 17/6/9.
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
            String url = parameterList[1] ;
            openWebBrowser(url) ;

        }


        return new NanoHTTPD.Response("Watch your phone!");
    }

    private void openWebBrowser(final String url){
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                Intent ii = new Intent();
                ii.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                ii.setAction(Intent.ACTION_VIEW);
                try {
                    String decodeUrl = URLDecoder.decode(url , "utf-8") ;
                    Uri content_url = Uri.parse(decodeUrl) ;
                    ii.setData(content_url);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }


//        startActivity(Intent.createChooser(ii , "choose"));
                Intent tempIntent = new Intent(appContext , TempActivity.class) ;
                tempIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                tempIntent.putExtra("intent" , ii) ;
                appContext.startActivity(tempIntent);
            }
        }) ;
    }
}
