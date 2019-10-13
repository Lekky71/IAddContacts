package com.lekai.root.iaddcontacts.util

import android.net.Uri
import com.google.android.material.snackbar.Snackbar
import com.lekai.root.iaddcontacts.models.ContactModel
import com.opencsv.CSVReader
import com.opencsv.exceptions.CsvMalformedLineException
import kotlinx.android.synthetic.main.activity_main.importContactsButton
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import java.io.FileReader
import java.lang.Exception


/**
 * .:.:.:. Created by @henrikhorbovyi on 12/10/19 .:.:.:.
 */

object CsvUtils {

    fun importContacts(file: Uri?): MutableList<ContactModel> {
        var results: MutableList<ContactModel> = mutableListOf()
        file?.path?.let {
            val path = it.split("raw:")[1]
            val reader = CSVReader(FileReader(path))
            results = obtainContactsFromCsvReader(reader)
        }
        return results
    }

    private fun obtainContactsFromCsvReader(reader: CSVReader): MutableList<ContactModel>  {
        val contacts = mutableListOf<ContactModel>()
        reader.readAll().run {
            removeAt(0)
            forEach { contact ->
                val contactModel = ContactModel(contact[0], contact[1], contact[2], contact[3])
                contacts.add(contactModel)
            }
        }
        return contacts
    }

}