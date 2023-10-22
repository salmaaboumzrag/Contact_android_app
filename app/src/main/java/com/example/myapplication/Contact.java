package com.example.myapplication;

import android.graphics.Bitmap;

import java.io.Serializable;

public class Contact implements Serializable {
    private String gender; // M or F
    private String name;
    private String firstName; // Prenom in French
    private String birthDate; // Date de naissance in French
    private String phone;
    private String email;
    private String address;
    private String postalCode;
    private String contactImagePath;

    public Contact(String gender, String name, String firstName, String birthDate,
                   String phone, String email, String address, String postalCode, String contactImagePath) {
        this.gender = gender;
        this.name = name;
        this.firstName = firstName;
        this.birthDate = birthDate;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.postalCode = postalCode;
        this.contactImagePath = contactImagePath;
    }

    // Getters and Setters
    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getContactImagePath() {
        return contactImagePath;
    }

    public void setContactImagePath(String contactImagePath) {
        this.contactImagePath = contactImagePath;
    }

    @Override
    public String toString() {
        return firstName + " " + name.toUpperCase() + '\n' + '\n' +
                "   Gender: " + gender + '\n' +
                "   Birth Date: " + birthDate + '\n' +
                "   Phone: " + phone + '\n' +
                "   Email: " + email + '\n' +
                "   Address: " + address + '\n' +
                "   Postal Code: " + postalCode;
    }
}
