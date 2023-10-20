package com.example.myapplication;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


//@Getter
//@Setter
//@AllArgsConstructor
//@NoArgsConstructor
//@ToString
public class Contact implements Serializable {
    private String gender; // M or F
    private String name;
    private String firstName; // Prenom in French
    private String birthDate; // Date de naissance in French
    private String phone;
    private String email;
    private String address;
    private String postalCode;

    // Constructor
    public Contact(String gender, String name, String firstName, String birthDate,
                   String phone, String email, String address, String postalCode) {
        this.gender = gender;
        this.name = name;
        this.firstName = firstName;
        this.birthDate = birthDate;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.postalCode = postalCode;
    }


    // Getter and Setter methods for Name
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // Getter and Setter methods for Email
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    // Getter and Setter methods for Phone
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getGender() {
        return gender;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public String getAddress() {
        return address;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    @Override
    public String toString() {
        return firstName + " " + name.toUpperCase()+'\n' +'\n' +
                "   Gender: " + gender + '\n' +
                "   Birth Date: " + birthDate + '\n' +
                "   Phone: " + phone + '\n' +
                "   Email: " + email + '\n' +
                "   Address: " + address + '\n' +
                "   Postal Code: " + postalCode ;
    }

}


