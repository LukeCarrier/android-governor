package com.governorapp.controller;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import com.governorapp.config.Configuration;
import com.governorapp.model.Message;
import com.governorapp.server.Controller;

import java.util.ArrayList;
import java.util.List;

import fi.iki.elonen.NanoHTTPD;

/**
 * Messaging controller.
 * <p/>
 * Allows sending and reading SMS messages.
 */
public class MessagingController extends AbstractController implements Controller {
    protected Uri smsContentUri;

    public MessagingController(Context appContext, Configuration config) {
        super(appContext, config);

        smsContentUri = Uri.parse("content://sms/inbox");
    }

    /**
     * Retrieve a list of threads.
     *
     * Content providers in Android can be considered private parts of the API, and are subject to
     * change without warning. We need to be careful about maintaining this code!
     *
     * @return
     */
    public NanoHTTPD.Response getThreads() {
        String select = "1 = 1) GROUP BY (" + Message.fieldMap.get("personId");
        Cursor cursor = appContext.getContentResolver().query(this.smsContentUri, null, select, null, null);

        List<Message> threads = new ArrayList<Message>();
        Message thread;
        while (cursor.moveToNext()) {
            thread = Message.fromCursor(cursor);
            threads.add(thread);
        }

        return this.respond(threads);
    }
}
