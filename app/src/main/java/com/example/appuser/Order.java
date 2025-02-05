package com.example.appuser;

public class Order {

    String name;
    String address;
    String pincode;
    String mobile;
    String email;
    public Order() {
    }

    public Order(String name, String address, String pincode, String mobile, String email) {
        this.name = name;
        this.address = address;
        this.pincode = pincode;
        this.mobile = mobile;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}