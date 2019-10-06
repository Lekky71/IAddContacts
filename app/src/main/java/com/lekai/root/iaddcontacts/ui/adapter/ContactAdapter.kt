package com.lekai.root.iaddcontacts.ui.adapter

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.recyclerview.widget.RecyclerView
import com.lekai.root.iaddcontacts.R
import com.lekai.root.iaddcontacts.models.ContactModel
import kotlinx.android.synthetic.main.each_contact_view.view.*

class ContactAdapter(private val context: Context, private val mCount : Int) :
        RecyclerView.Adapter<ContactAdapter.ContactViewHolder>() {
    var allContacts : Array<ContactModel?>? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder  =
            ContactViewHolder(LayoutInflater.from(context).inflate(R.layout.each_contact_view, parent, false))

    override fun getItemCount(): Int  = mCount

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        allContacts = arrayOfNulls(10)
        holder.phoneEditText?.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val name =  holder.contactNameEditText!!.text.toString()
                val phone = holder.phoneEditText!!.text.toString()
                if (name.isNotEmpty() && phone.isNotEmpty()){
                    val contactModel = ContactModel(name, phone)
                    allContacts!![position] = contactModel
                }}

        })
    }

    inner class ContactViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
         var contactNameEditText: EditText? = itemView.contact_name
         var phoneEditText: EditText? = itemView.contact_phone
    }
}