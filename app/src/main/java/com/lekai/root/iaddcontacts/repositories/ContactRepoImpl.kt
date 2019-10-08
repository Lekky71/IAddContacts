package com.lekai.root.iaddcontacts.repositories

import android.util.Log
import com.lekai.root.iaddcontacts.models.ContactModel
import com.lekai.root.iaddcontacts.service.ContactService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ContactRepoImpl(private val contactService: ContactService) : ContactRepo {
    override suspend fun addContact(contactModel: ContactModel) {
                Log.d("Model", contactModel.toString())
                withContext(Dispatchers.IO) { contactService.addContact(contactModel) }
    }
}