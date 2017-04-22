package com.lekai.root.iaddcontacts.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.lekai.root.iaddcontacts.AddContacts;
import com.lekai.root.iaddcontacts.ContactModel;
import com.lekai.root.iaddcontacts.R;
import java.util.ArrayList;

/**
 * Created by root on 4/21/17.
 */

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ContactViewHolder>{
    private Context mContext;
    public ContactModel contact;
    int mCount ;
    ArrayList<ContactModel> allContacts;
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
        allContacts = new ArrayList<ContactModel>();
        holder.addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = holder.getName();
                phone = holder.getPhone();

                if(name.length()!=0 && phone.length() !=0) {
                    //This adds the details to the contact
                    AddContacts.contactAdd(mContext,name,phone);
                    Toast.makeText(mContext, "Contact saved", Toast.LENGTH_SHORT).show();
                }else if(name.length()==0){
                    holder.contactName.setError("name cannot be empty");
                }if(phone.length()==0){
                    holder.contactPhone.setError("enter a valid number");
                }
            }
        });
    }
    @Override
    public int getItemCount() {
        return mCount;
    }
    public class ContactViewHolder extends RecyclerView.ViewHolder {
        EditText contactName;
        EditText contactPhone;
        Button addButton;
        public ContactViewHolder(View itemView) {
            super(itemView);
            contactName = (EditText) itemView.findViewById(R.id.contact_name);
            contactPhone = (EditText) itemView.findViewById(R.id.contact_phone);
            addButton = (Button) itemView.findViewById(R.id.contact_add_button);
        }
        public String getName(){
            return contactName.getText().toString();
        }
        public String getPhone(){
            return contactPhone.getText().toString();
        }
    }
}
