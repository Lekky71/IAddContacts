package com.lekai.root.iaddcontacts.ui.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.lekai.root.iaddcontacts.repositories.ContactRepo

@Suppress("UNCHECKED_CAST")
class ContactViewModelFactory(private val contactRepo: ContactRepo) : ViewModelProvider.NewInstanceFactory(){
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ContactViewModel(contactRepo) as T
    }

}