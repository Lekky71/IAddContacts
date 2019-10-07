package com.lekai.root.iaddcontacts

import android.provider.ContactsContract

/**
 * Created by Arvind on 10/7/2019.
 */
class Customer {
    var customer_name: String? = null
    var phone_number: String? = null
    var email: String?= null
    var organization: String? = null

    constructor() {}
    constructor(customer_name: String?, phone_number: String?, email: String?, organization: String?){
        this.customer_name = customer_name
        this.phone_number = phone_number
        this.email = email
        this.organization = organization
    }

    override fun toString(): String {
        return "Customer [name= " + customer_name + "number" + phone_number + "email= " + email + "organiazation = " + organization + "]"
    }

}