package com.lekai.root.iaddcontacts;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.provider.ContactsContract.CommonDataKinds.StructuredName;
import android.provider.ContactsContract.Data;

/**
 * Created by root on 4/21/17.
 */
//Accessing the contacts api to be able to send the contact info directly
public class AddContacts {
    public AddContacts(){
    }
    public static void contactAdd(Context c, String name ,String phone) {
        ContentValues values = new ContentValues();
        values.put(Data.DISPLAY_NAME, name);
        Uri rawContactUri = c.getContentResolver().insert(ContactsContract.RawContacts.CONTENT_URI, values);
        long rawContactId = ContentUris.parseId(rawContactUri);
        long contactId = AddContacts.getContactId(c, rawContactId);
        System.out.println("rawContactId = " + rawContactId);
        System.out.println("contactId = " + contactId);

        values.clear();
        values.put(Phone.NUMBER, phone);
        values.put(Phone.TYPE, Phone.TYPE_OTHER);
        values.put(Phone.MIMETYPE, Phone.CONTENT_ITEM_TYPE);
        values.put(Data.RAW_CONTACT_ID, rawContactId);
        c.getContentResolver().insert(Data.CONTENT_URI, values);

        values.clear();
        values.put(Data.MIMETYPE, Data.CONTENT_TYPE);
        values.put(StructuredName.DISPLAY_NAME, name);
        values.put(Data.RAW_CONTACT_ID, rawContactId);
        c.getContentResolver().insert(Data.CONTENT_URI, values);

        values.clear();
        values.put(Data.MIMETYPE, StructuredName.CONTENT_ITEM_TYPE);
        values.put(StructuredName.DISPLAY_NAME, name);
        values.put(Data.RAW_CONTACT_ID, rawContactId);
        c.getContentResolver().insert(Data.CONTENT_URI, values);

    }

    public static long getContactId(Context context, long rawContactId) {
        Cursor cur = null;
        try {
            cur = context.getContentResolver().query(ContactsContract.RawContacts.CONTENT_URI, new String[] { ContactsContract.RawContacts.CONTACT_ID }, ContactsContract.RawContacts._ID + "=" + rawContactId, null, null);
            if (cur.moveToFirst()) {
                return cur.getLong(cur.getColumnIndex(ContactsContract.RawContacts.CONTACT_ID));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cur != null) {
                cur.close();
            }
        }
        return -1l;
    }
}
