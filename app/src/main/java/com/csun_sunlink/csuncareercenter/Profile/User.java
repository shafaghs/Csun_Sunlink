package com.csun_sunlink.csuncareercenter.Profile;

/**
 * Created by bigmatt76 on 11/30/16.
 */

public class User {
    private String userID = " ";
    private String firstName = " ";
    private String lastName = " ";
    private String middleName = " ";
    private String email = " ";
    private String phone = " ";
    private String address = " ";
    private String status = " ";
    private String geopref = " ";
    private String workAuth = " ";

    public User() {

    }

    protected void setFirstName(String newFirstName) {
        if(newFirstName!="null")
            this.firstName=newFirstName;
    }
    protected void setLastName(String newLastName) {
        if(newLastName!="null")
            this.lastName=newLastName;
    }
    protected void setMiddleName(String newMiddleName) {
        if(newMiddleName!="null")
            this.middleName=newMiddleName;
    }
    protected void setEmail(String newEmail) {
        if(newEmail!="null")
            this.email= newEmail;
    }
    protected void setPhone(String newPhone) {
        if(newPhone!="null")
            this.phone = newPhone;
    }
    protected void setAddress(String newAddress) {
        if(newAddress!="null")
            this.address = newAddress;
    }
    protected void setStatus(String newStatus) {
        if(newStatus!="null")
            this.status = newStatus;
    }
    protected void setGeoPref(String newGeoPref) {
        if(newGeoPref!="null")
            this.geopref = newGeoPref;
    }
    protected void setWorkAuth(String newWorkAuth) {
        if(newWorkAuth != "null"){
            this.workAuth = newWorkAuth;
        }
    }

    protected String getFirstName() {
        return this.firstName;
    }
    protected String getEmail(){
        return this.email;
    }

    protected String getLastName() {
        return this.lastName;
    }
    protected String getMiddleName() {
        return this.middleName;
    }
    protected String getPhone() {
        return this.phone;
    }
    protected String getAddress() {
        return this.address;
    }
    protected String getStatus() {
        return this.status;
    }
    protected String getGeopref() {
        return this.geopref;
    }
    protected String getWorkAuth() {
        return this.workAuth;
    }

}








