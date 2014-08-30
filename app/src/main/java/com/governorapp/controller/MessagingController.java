package com.governorapp.controller;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import com.governorapp.config.Configuration;
import com.governorapp.model.MessageThread;
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
    public MessagingController(Context appContext, Configuration config) {
        super(appContext, config);
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
        Uri uri = Uri.parse("content://sms/inbox");
        Cursor cursor = appContext.getContentResolver().query(uri, null, null, null, null);

        List<MessageThread> threads = new ArrayList<MessageThread>();
        MessageThread thread;
        while (cursor.moveToNext()) {
            thread = MessageThread.fromCursor(cursor);
            threads.add(thread);
        }

        return this.respond(threads);
    }
}
