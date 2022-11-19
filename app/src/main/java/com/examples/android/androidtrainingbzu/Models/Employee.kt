package com.examples.android.androidtrainingbzu.Models

import java.util.*

class Employee {


    var name: String? = null
    var email: String? = null
    var phone: String? = null
    var address: String? = null
    var dept: String? = null
    var picResPath: String? = null
    var id = 0
        private set
    var picResID = 0
    var hireDate: Date? = null

    constructor() {}

    /**
     * @param name : Employee Name
     * @param email : Employee Email
     * @param phone : Employee Mobile Number
     * @param address : Employee Address
     * @param dept : Employee Department Name
     * @param id : Employee Job ID
     * @param picResID : Employee picture ID from resources
     * @param hireDate
     */
    constructor(
        name: String?,
        email: String?,
        phone: String?,
        address: String?,
        dept: String?,
        id: Int,
        picResID: Int,
        hireDate: Date?,
    ) {
        this.name = name
        this.email = email
        this.phone = phone
        this.address = address
        this.dept = dept
        this.id = id
        this.picResID = picResID
        this.hireDate = hireDate
    }
}