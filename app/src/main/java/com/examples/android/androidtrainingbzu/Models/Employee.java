package com.examples.android.androidtrainingbzu.Models;

import java.util.Date;

public class Employee {
    private String name;
    private String email;
    private String phone;
    private String address;
    private String dept;
    private String picResPath;
    private int ID, picResID;
    private Date hireDate;
    public Employee() {
    }

    /**
     *  @param name : Employee Name
     * @param email : Employee Email
     * @param phone : Employee Mobile Number
     * @param address : Employee Address
     * @param dept : Employee Department Name
     * @param id : Employee Job ID
     * @param picResID : Employee picture ID from resources
     * @param hireDate
     */
    public Employee(String name, String email, String phone, String address, String dept, int id, int picResID, Date hireDate) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.dept = dept;
        ID = id;
        this.picResID = picResID;
        this.hireDate = hireDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

    public int getID() {
        return ID;
    }

    public int getPicResID() {
        return picResID;
    }

    public void setPicResID(int picResID) {
        this.picResID = picResID;
    }

    public Date getHireDate() {
        return hireDate;
    }

    public void setHireDate(Date hireDate) {
        this.hireDate = hireDate;
    }
    public String getPicResPath() {
        return picResPath;
    }

    public void setPicResPath(String picResPath) {
        this.picResPath = picResPath;
    }
}
