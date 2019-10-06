package com.lekai.root.iaddcontacts.repositories

import com.lekai.root.iaddcontacts.models.ContactModel

interface ContactRepo{
    suspend fun addContact(contactModel: ContactModel)
}