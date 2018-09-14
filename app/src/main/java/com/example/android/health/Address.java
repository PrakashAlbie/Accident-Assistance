package com.example.android.health;

/**
 * Created by Praveen on 17-03-2018.
 */

public class Address {

    String doorno,street,district,pincode,state;


    public Address()
    {

    }


    public Address(String doorno, String street, String district, String pincode, String state) {
        this.doorno = doorno;
        this.street = street;
        this.district = district;
        this.pincode = pincode;
        this.state = state;
    }

    public String getDoorno() {
        return doorno;
    }

    public String getStreet() {
        return street;
    }

    public String getDistrict() {
        return district;
    }

    public String getPincode() {
        return pincode;
    }

    public String getState() {
        return state;
    }
}
