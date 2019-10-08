package com.lekai.root.iaddcontacts.service

import com.lekai.root.iaddcontacts.models.ContactModel
/**
 * @author KhalidToak .
 * @since 6 OCT, 2019
 */
interface ContactService {
    suspend fun addContact(contactModel: ContactModel?)
}