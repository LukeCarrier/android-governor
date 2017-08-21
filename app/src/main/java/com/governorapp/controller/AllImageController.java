package com.governorapp.controller;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import com.governorapp.config.Configuration;
import com.governorapp.server.Controller;
import com.governorapp.util.StreamUtil;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import fi.iki.elonen.NanoHTTPD;

/**
 * Created by macoli on 17/6/12.
 */

public class AllImageController extends AbstractController implements Controller {

    /**
     * Constructor.
     *
     * @param appContext Application context.
     * @param config     Governor server configuration.
     */
    public AllImageController(Context appContext, Configuration config) {
        super(appContext, config);
    }

    @Override
    public NanoHTTPD.Response doWork(NanoHTTPD.IHTTPSession session) {
        try {
            InputStream is = appContext.getAssets().open("images.html") ;

            String html = StreamUtil.readStreams(is);

            html = String.format(html , getSystemPhotoList(appContext)) ;

            return new NanoHTTPD.Response(NanoHTTPD.Response.Status.OK
                    , NanoHTTPD.MIME_HTML , html) ;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private final String tr = "<tr><td><img  height=\"200\" width=\"200\" src=\"%s\"  alt=\"%s\"/></td></tr>" ;
    public String getSystemPhotoList(Context context)
    {
        StringBuilder sb = new StringBuilder() ;
        Uri uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;

        ContentResolver contentResolver = context.getContentResolver();
        Cursor cursor = contentResolver.query(uri, null, null, null, null);
        if (cursor == null || cursor.getCount() <= 0) return null; // 没有图片
        while (cursor.moveToNext())
        {
            int index = cursor
                    .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            String path = cursor.getString(index); // 文件地址
            String name = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME)) ;
            File file = new File(path);
            if (file.exists())
            {
                sb.append(String.format(tr , path , name)) ;
            }
        }

        return sb.toString() ;
    }
}
