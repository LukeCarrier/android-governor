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
    /**
     * Inbox URI.
     */
    protected Uri inboxUri;

    /**
     * Constructor.
     *
     * @param appContext The application context, from which we obtain a content resolver.
     * @param config     Governor's server configuration.
     */
    public MessagingController(Context appContext, Configuration config) {
        super(appContext, config);

        inboxUri = Uri.parse("content://sms/inbox");
    }

    @Override
    protected NanoHTTPD.Response doWork(NanoHTTPD.IHTTPSession session) {
        List<Message> threads = getMessagesList("") ;
        return this.respond(threads) ;
    }

    /**
     * Retrieve a list of messages.
     *
     * @param personId
     * @return
     */
    public NanoHTTPD.Response getMessages(Integer personId) {
        String select = Message.fieldMap.get("personId") + " = " + personId;
        List<Message> messages = getMessagesList(select);

        return this.respond(messages);
    }

    /**
     * Retrieve a list of threads.
     * <p/>
     * Content providers in Android can be considered private parts of the API, and are subject to
     * change without warning. We need to be careful about maintaining this code!
     *
     * @return
     */
    public NanoHTTPD.Response getThreads() {
        String select = "1 = 1) GROUP BY (" + Message.fieldMap.get("personId");
        List<Message> threads = getMessagesList(select);

        return this.respond(threads);
    }

    /**
     * Retrieve a list of messages matching the given selector.
     *
     * @param select The WHERE fragment of a query.
     * @return The matching messages.
     */
    private List<Message> getMessagesList(String select) {
        Cursor cursor = appContext.getContentResolver().query(this.inboxUri, null, select, null, null);
        List<Message> messages = new ArrayList<Message>();
        Message thread;

        while (cursor.moveToNext()) {
            thread = Message.fromCursor(cursor);
            messages.add(thread);
        }
        cursor.close();

        return messages;
    }
}
