package com.lekai.root.iaddcontacts.service

import android.content.ContentUris
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.provider.ContactsContract
import android.util.Log
import com.lekai.root.iaddcontacts.models.ContactModel
import kotlin.math.log

/**
 * @author KhalidToak .
 * @since 6 OCT, 2019
 */
class ContactServiceImpl(private val context: Context) : ContactService{
    override suspend fun addContact(contactModel: ContactModel?) {
        ContentValues().apply {
            if (contactModel!=null) {
                put(ContactsContract.Data.DISPLAY_NAME, contactModel.name)
                val rawContactUri = context.contentResolver.insert(ContactsContract.RawContacts.CONTENT_URI, this)
                val rawContactId = ContentUris.parseId(rawContactUri!!)
                val contactId = getContactId(context, rawContactId)
                Log.d("rawContactId", rawContactId.toString())
                Log.d("ContactId", contactId.toString())
                clear()
                put(ContactsContract.CommonDataKinds.Phone.NUMBER, contactModel.phone)
                put(ContactsContract.CommonDataKinds.Phone.TYPE, ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE)
                put(ContactsContract.CommonDataKinds.Phone.MIMETYPE, ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
                put(ContactsContract.Data.RAW_CONTACT_ID, rawContactId)
                context.contentResolver.insert(ContactsContract.Data.CONTENT_URI, this)
                clear()
                put(ContactsContract.Data.MIMETYPE, ContactsContract.Data.CONTENT_TYPE)
                put(ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME, contactModel.name)
                put(ContactsContract.Data.RAW_CONTACT_ID, rawContactId)
                context.contentResolver.insert(ContactsContract.Data.CONTENT_URI, this)

                clear()
                put(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE)
                put(ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME, contactModel.name)
                put(ContactsContract.Data.RAW_CONTACT_ID, rawContactId)
                context.contentResolver.insert(ContactsContract.Data.CONTENT_URI, this)
            }
        }
    }
    private  fun getContactId(context: Context, rawContactId: Long): Long {
        var cur: Cursor? = null
        try {
            cur = context.contentResolver.query(ContactsContract.RawContacts.CONTENT_URI,
                    arrayOf(ContactsContract.RawContacts.CONTACT_ID),
                    ContactsContract.RawContacts._ID + "=" + rawContactId, null, null)
            if (cur!!.moveToFirst()) {
                return cur.getLong(cur.getColumnIndex(ContactsContract.RawContacts.CONTACT_ID))
            }
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            cur?.close()
        }
        return -1L
    }
}