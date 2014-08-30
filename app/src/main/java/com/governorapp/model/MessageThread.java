package com.governorapp.model;

import android.database.Cursor;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Messaging thread.
 *
 * This model represents data straight out of the Android telephony provider. It is very likely that
 * this code has been modified from device to device.
 */
public class MessageThread {
    public static final Map<String, String> fieldMap;

    /**
     * Unique ID identifying the thread.
     */
    public int id;

    /**
     * Address (phone number).
     *
     * Stored as a string to prevent modifications to the numbers.
     */
    public String address;

    /**
     * Thread body.
     */
    public String body;

    /**
     * Timestamp indicating date sent.
     */
    public int date;

    /**
     * Timestamp indicating delivery date.
     */
    public int dateSent;

    /**
     * XXX: nobody knows
     */
    public int errorCode;

    /**
     * XXX: nobody knows
     */
    public int locked;

    /**
     * ID of the person (contact) sent to or received from.
     */
    public int personId;

    /**
     * Delivery protocol.
     */
    public int protocol;

    /**
     * Has the message been read yet?
     */
    public boolean read;

    /**
     * XXX: nobody knows
     */
    public int replyPathPresent;

    /**
     * XXX: nobody knows
     */
    public int seen;

    /**
     * XXX: nobody knows
     */
    public String serviceCenter;

    /**
     * XXX: nobody knows
     */
    public int status;

    /**
     * Thread subject.
     */
    public String subject;

    /**
     * Message type (folder) flag.
     *
     * -> 1 = incoming (inbox)
     * -> 2 = outgoing (sent)
     */
    public int type;

    static {
        Map<String, String> tempFieldMap = new HashMap<String, String>();

        tempFieldMap.put("id", "thread_id");
        tempFieldMap.put("address", "address");
        tempFieldMap.put("body", "body");
        tempFieldMap.put("date", "date");
        tempFieldMap.put("dateSent", "date_sent");
        tempFieldMap.put("errorCode", "error_code");
        tempFieldMap.put("locked", "locked");
        tempFieldMap.put("personId", "person");
        tempFieldMap.put("protocol", "protocol");
        tempFieldMap.put("read", "read");
        tempFieldMap.put("replyPathPresent", "reply_path_present");
        tempFieldMap.put("seen", "seen");
        tempFieldMap.put("serviceCenter", "service_center");
        tempFieldMap.put("status", "status");
        tempFieldMap.put("subject", "subject");
        tempFieldMap.put("type", "type");

        fieldMap = Collections.unmodifiableMap(tempFieldMap);
    }

    /**
     * Model a thread retrieved directly from a DB cursor.
     *
     * @param cursor The cursor object from which to retrieve values.
     *
     * @return A populated model object.
     */
    public static MessageThread fromCursor(Cursor cursor) {
        MessageThread thread = new MessageThread();

        thread.id = cursor.getInt(cursor.getColumnIndex(fieldMap.get("id")));
        thread.address = cursor.getString(cursor.getColumnIndex(fieldMap.get("address")));
        thread.body = cursor.getString(cursor.getColumnIndex(fieldMap.get("body")));
        thread.date = cursor.getInt(cursor.getColumnIndex(fieldMap.get("date")));
        thread.dateSent = cursor.getInt(cursor.getColumnIndex(fieldMap.get("dateSent")));
        thread.errorCode = cursor.getInt(cursor.getColumnIndex(fieldMap.get("errorCode")));
        thread.locked = cursor.getInt(cursor.getColumnIndex(fieldMap.get("locked")));
        thread.personId = cursor.getInt(cursor.getColumnIndex(fieldMap.get("personId")));
        thread.protocol = cursor.getInt(cursor.getColumnIndex(fieldMap.get("protocol")));
        thread.read = cursor.getInt(cursor.getColumnIndex(fieldMap.get("read"))) == 1;
        thread.replyPathPresent = cursor.getInt(cursor.getColumnIndex(fieldMap.get("replyPathPresent")));
        thread.seen = cursor.getInt(cursor.getColumnIndex(fieldMap.get("seen")));
        thread.serviceCenter = cursor.getString(cursor.getColumnIndex(fieldMap.get("serviceCenter")));
        thread.status = cursor.getInt(cursor.getColumnIndex(fieldMap.get("status")));
        thread.subject = cursor.getString(cursor.getColumnIndex(fieldMap.get("subject")));
        thread.type = cursor.getInt(cursor.getColumnIndex(fieldMap.get("type")));

        return thread;
    }
}
