package com.lekai.root.iaddcontacts.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.lekai.root.iaddcontacts.ContactModel;
import com.lekai.root.iaddcontacts.R;

/**
 * Created by root on 4/21/17.
 */

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ContactViewHolder>{
    private Context mContext;
    int mCount ;
    ContactModel[] contacts;
    String name;
    String phone;
    public ContactAdapter(Context context, int count){
        mContext = context;
        mCount = count;
    }
    @Override
    public ContactViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.each_contact_view,parent,false);
        return new ContactViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ContactAdapter.ContactViewHolder holder, final int position) {
        contacts = new ContactModel[10];
        holder.contactPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                name = holder.getName();
                phone = holder.getPhone();
                if(name.length()!=0 && phone.length() !=0) {
                    ContactModel contactModel = new ContactModel(name, phone);
                    contacts[position] = contactModel;
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }
    @Override
    public int getItemCount() {
        return mCount;
    }
    public class ContactViewHolder extends RecyclerView.ViewHolder {
        EditText contactName;
        EditText contactPhone;
        public ContactViewHolder(View itemView) {
            super(itemView);
            contactName = (EditText) itemView.findViewById(R.id.contact_name);
            contactPhone = (EditText) itemView.findViewById(R.id.contact_phone);
        }
        public String getName(){
            return contactName.getText().toString();
        }
        public String getPhone(){
            return contactPhone.getText().toString();
        }
    }

    public ContactModel[] getAllContacts(){
        return contacts;
    }
}
