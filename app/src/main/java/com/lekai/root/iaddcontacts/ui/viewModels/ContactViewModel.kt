package com.lekai.root.iaddcontacts.ui.viewModels

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lekai.root.iaddcontacts.models.ContactModel
import com.lekai.root.iaddcontacts.repositories.ContactRepo
import com.lekai.root.iaddcontacts.util.CsvUtils
import com.opencsv.exceptions.CsvMalformedLineException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.lang.Exception

class ContactViewModel(private val contactRepo: ContactRepo) : ViewModel(){

    private val _contacts: MutableLiveData<List<ContactModel>> = MutableLiveData()
    val contactsObservable: LiveData<List<ContactModel>>
        get() = _contacts


    fun addContacts(contact :ContactModel){
        viewModelScope.launch {
            contactRepo.addContact(contact)
        }
    }

    fun importContacts(uri: Uri?, error: (Throwable) -> Unit) {
        viewModelScope.launch {
            try {
                val contacts = viewModelScope.async(Dispatchers.IO) {
                    CsvUtils.importContacts(uri)
                }
                _contacts.postValue(contacts.await())
            } catch (exception: Exception) { error(exception) }
        }
    }
}