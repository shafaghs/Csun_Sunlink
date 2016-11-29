package com.csun_sunlink.csuncareercenter.Profile;

/**
 * Created by bigmatt76 on 11/28/16.
 */

public class ProfileInfo {

    //Personal Strings
    String header;
    String info;

    public ProfileInfo( String newHeader, String newInfo) {
        this.header = newHeader;
        this.info = newInfo;
    }
    //Getters:
    public String getHeader() {
        return this.header;
    }

    public String getInfo() {
        return this.info;
    }
    /*
    private String userName, userEmail, userPhone, userAddress, userJobSearchStatus,userGeoPreference, userWorkAuthorization;

    public ProfileInfo(String newName, String newEmail, String newPhone, String newAddress, String newStatus, String newGeoPref, String newWorkAuth) {
        this.userName = newName;
        this.userEmail = newEmail;
        this.userPhone = newPhone;
        this.userAddress = newAddress;
        this.userJobSearchStatus = newStatus;
        this.userGeoPreference = newGeoPref;
        this.userWorkAuthorization = newWorkAuth;
    }

    //Getters
    public String getName(){
        return this.userName;
    }
    public String getEmail(){
        return this.userEmail;
    }
    public String getPhone(){
        return this.userPhone;
    }
    public String getUserAddress(){
        return this.userAddress;
    } */
}
