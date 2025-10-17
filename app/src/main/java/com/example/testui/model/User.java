package com.example.testui.model;

public class    User {
    String id;
    String fullname;
    String email;
    String phone;
    String dob;
    String gender;
    String image;
    String role;
    String address;

    public User() {
    }

    public User(String address, String dob, String email, String fullname, String gender, String id, String phone, String image, String role) {
        this.address = address;
        this.dob = dob;
        this.email = email;
        this.fullname = fullname;
        this.gender = gender;
        this.id = id;
        this.phone = phone;
        this.image = image;
        this.role = role;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
