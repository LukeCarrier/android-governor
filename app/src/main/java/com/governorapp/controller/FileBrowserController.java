package com.governorapp.controller;

import android.content.Context;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;

import com.governorapp.config.Configuration;
import com.governorapp.server.Controller;
import com.governorapp.util.StreamUtil;
import com.governorapp.util.Utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import fi.iki.elonen.NanoHTTPD;

/**
 * Created by lidongxu on 17/6/14.
 */

public class FileBrowserController extends AbstractController implements Controller {
    /**
     * Constructor.
     *
     * @param appContext Application context.
     * @param config     Governor server configuration.
     */
    public FileBrowserController(Context appContext, Configuration config) {
        super(appContext, config);
    }

    private final String tr = "<tr><td><a href=\"/file_browse?path=%s\"/>%s</a>     %s</td></tr>" ;
    private SimpleDateFormat formatter = new SimpleDateFormat("yy/MM/dd HH:mm:ss") ;
    @Override
    public NanoHTTPD.Response doWork(NanoHTTPD.IHTTPSession session) {
        //\?path=(\s|\S)*
        Log.d("macoli" , "FileBrowserController doWork") ;
        NanoHTTPD.Method method = session.getMethod();
        String params = "" ;
        Map<String, String> files = new HashMap<>();


        try {
            session.parseBody(files);
            params = session.getQueryParameterString() ;
            String path = params.split("=")[1] ;
            String filesStr = getFileStr(path) ;

            if (!TextUtils.isEmpty(filesStr)){
                InputStream is = appContext.getAssets().open("images.html") ;

                String html = StreamUtil.readStreams(is);

                html = String.format(html , filesStr) ;

                return new NanoHTTPD.Response(NanoHTTPD.Response.Status.OK
                        , NanoHTTPD.MIME_HTML , html) ;
            } else {
                try {
                    File f = new File(path) ;
                    String mimeType = Utils.getMimeType(path) ;
                    FileInputStream fis = new FileInputStream(f) ;

                    return new NanoHTTPD.Response(NanoHTTPD.Response.Status.OK , mimeType , fis) ;
                } catch (FileNotFoundException e) {

                }
                return new NanoHTTPD.Response(NanoHTTPD.Response.Status.NOT_FOUND, NanoHTTPD.MIME_PLAINTEXT, "404 Not Found");
            }
        } catch (Exception ex){
            return new NanoHTTPD.Response(NanoHTTPD.Response.Status.NOT_FOUND, NanoHTTPD.MIME_PLAINTEXT, String.format("%s Not Found" , params));
        }

    }

    private String getFileStr(String path){
        StringBuilder sb = new StringBuilder() ;
        File file = null ;
        if (TextUtils.equals("/" , path)){
            file = Environment.getExternalStorageDirectory().getAbsoluteFile() ;
        } else {
            file = new File(path) ;
        }
        if (file.isDirectory()){
            File[] fileList = file.listFiles() ;
            for (File f : fileList){
                String fp = f.getAbsolutePath() ;
                String fn = f.getName() ;
                String modify = formatter.format(f.lastModified()) ;
                String ftr = String.format(tr , fp , fn , modify) ;
                sb.append(ftr) ;
            }
        } else {

        }
        return sb.toString() ;
    }
}
