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

import fi.iki.elonen.NanoHTTPD;

/**
 * Created by macoli on 17/6/12.
 */

public class AllVideoController extends AbstractController implements Controller {

    /**
     * Constructor.
     *
     * @param appContext Application context.
     * @param config     Governor server configuration.
     */
    public AllVideoController(Context appContext, Configuration config) {
        super(appContext, config);
    }

    @Override
    public NanoHTTPD.Response doWork(NanoHTTPD.IHTTPSession session) {
        try {
            InputStream is = appContext.getAssets().open("images.html") ;

            String html = StreamUtil.readStreams(is);

            html = String.format(html , getSystemVideoList(appContext)) ;

            return new NanoHTTPD.Response(NanoHTTPD.Response.Status.OK
                    , NanoHTTPD.MIME_HTML , html) ;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private final String tr = "<tr><td><video width=\"320\" height=\"240\" controls=\"controls\"><source src=\"%s\" type=\"video/mp4\">Your webbrowser doesn't support video tag</video></td></tr><tr><td>%s</td><td><a href=\"%s\">Download</a></td></tr>" ;
    public String getSystemVideoList(Context context)
    {
        StringBuilder sb = new StringBuilder() ;

        Cursor cursor = context.getContentResolver().query(
                MediaStore.Video.Media.EXTERNAL_CONTENT_URI, null, null,
                null, null);
        if (cursor == null || cursor.getCount() <= 0) return null; // 没有图片
        while (cursor.moveToNext())
        {
            String title = cursor
                    .getString(cursor
                            .getColumnIndexOrThrow(MediaStore.Video.Media.TITLE));
            String displayName = cursor
                    .getString(cursor
                            .getColumnIndexOrThrow(MediaStore.Video.Media.DISPLAY_NAME));
            String mimeType = cursor
                    .getString(cursor
                            .getColumnIndexOrThrow(MediaStore.Video.Media.MIME_TYPE));
            String path = cursor
                    .getString(cursor
                            .getColumnIndexOrThrow(MediaStore.Video.Media.DATA));
            long duration = cursor
                    .getInt(cursor
                            .getColumnIndexOrThrow(MediaStore.Video.Media.DURATION));
            long size = cursor
                    .getLong(cursor
                            .getColumnIndexOrThrow(MediaStore.Video.Media.SIZE));
            File file = new File(path);
            if (file.exists())
            {
                sb.append(String.format(tr , path , displayName , path)) ;
            }
        }

        return sb.toString() ;
    }
}
