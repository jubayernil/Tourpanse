package com.compiler.tourpanse.models;


public class User {

    private int userId;
    private String fullName;
    private String email;
    private String phoneNumber;
    private String emergencyContactNumber;
    private String username;
    private String password;
    private String confirmPassword;

    public User(int userId, String fullName, String email, String phoneNumber, String emergencyContactNumber, String username, String password) {
        this.password = password;
        this.username = username;
        this.userId = userId;
        this.fullName = fullName;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    public User(String fullName, String email, String phoneNumber, String emergencyContactNumber, String username, String password) {
        this.password = password;
        this.username = username;
        this.userId = userId;
        this.fullName = fullName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.emergencyContactNumber = emergencyContactNumber;
    }

    public User(){

    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getEmergencyContactNumber() {
        return emergencyContactNumber;
    }

    public void setEmergencyContactNumber(String emergencyContactNumber) {
        this.emergencyContactNumber = emergencyContactNumber;
    }
}
