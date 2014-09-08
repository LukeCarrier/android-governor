package com.governorapp.model;

import android.database.Cursor;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Message.
 * <p/>
 * This model represents data straight out of the Android telephony provider. It is very likely that
 * this code has been modified from device to device.
 * <p/>
 * We reuse this same class for both threads of messages and individual messages. We might need to
 * revisit using two separate classes when we integrate contacts again.
 */
public class Message {
    /**
     * Map of our fields to fields in the Cursor.
     */
    public static final Map<String, String> fieldMap;

    /**
     * Unique ID identifying the thread.
     */
    public int id;

    /**
     * Address (phone number).
     * <p/>
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
     * <p/>
     * -> 1 = incoming (inbox)
     * -> 2 = outgoing (sent)
     */
    public int type;

    /**
     * Class initializer.
     */
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

    public static Message fromCursor(Cursor cursor) {
        Message message = new Message();

        message.id = cursor.getInt(cursor.getColumnIndex(fieldMap.get("id")));
        message.address = cursor.getString(cursor.getColumnIndex(fieldMap.get("address")));
        message.body = cursor.getString(cursor.getColumnIndex(fieldMap.get("body")));
        message.date = cursor.getInt(cursor.getColumnIndex(fieldMap.get("date")));
        message.dateSent = cursor.getInt(cursor.getColumnIndex(fieldMap.get("dateSent")));
        message.errorCode = cursor.getInt(cursor.getColumnIndex(fieldMap.get("errorCode")));
        message.locked = cursor.getInt(cursor.getColumnIndex(fieldMap.get("locked")));
        message.personId = cursor.getInt(cursor.getColumnIndex(fieldMap.get("personId")));
        message.protocol = cursor.getInt(cursor.getColumnIndex(fieldMap.get("protocol")));
        message.read = cursor.getInt(cursor.getColumnIndex(fieldMap.get("read"))) == 1;
        message.replyPathPresent = cursor.getInt(cursor.getColumnIndex(fieldMap.get("replyPathPresent")));
        message.seen = cursor.getInt(cursor.getColumnIndex(fieldMap.get("seen")));
        message.serviceCenter = cursor.getString(cursor.getColumnIndex(fieldMap.get("serviceCenter")));
        message.status = cursor.getInt(cursor.getColumnIndex(fieldMap.get("status")));
        message.subject = cursor.getString(cursor.getColumnIndex(fieldMap.get("subject")));
        message.type = cursor.getInt(cursor.getColumnIndex(fieldMap.get("type")));

        return message;
    }
}
