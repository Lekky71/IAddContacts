package com.lekai.root.iaddcontacts.ui.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lekai.root.iaddcontacts.models.ContactModel
import com.lekai.root.iaddcontacts.repositories.ContactRepo
import kotlinx.coroutines.launch

class ContactViewModel(private val contactRepo: ContactRepo) : ViewModel(){
    fun addContacts(contact :ContactModel){
        viewModelScope.launch {
            contactRepo.addContact(contact)
        }
    }
}