package com.expedia.web.rest.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LUserDTO {

    private String idToken;

    private String userId;

    private String userName;

    private String userEmail;

    private String managerName;

    private String managerEmail;

    public LUserDTO() {
    }

    public LUserDTO(String idToken, String userName,  String userId, String userEmail, String managerEmail, String managerName) {
        this.idToken = idToken;
        this.userName = userName;
        this.userId = userId;
        this.userEmail = userEmail;
        this.managerEmail = managerEmail;
        this.managerName = managerName;
    }

    @JsonProperty("id_token")
    public String getIdToken() {
        return idToken;
    }

    public void setIdToken(String idToken) {
        this.idToken = idToken;
    }

    @JsonProperty("user_id")
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @JsonProperty("user_email")
    public String getUserEmail() {
        return userEmail;
    }


    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    @JsonProperty("manager_name")
    public String getManagerName() {
        return managerName;
    }

    public void setManagerName(String managerName) {
        this.managerName = managerName;
    }

    @JsonProperty("manager_email")
    public String getManagerEmail() {
        return managerEmail;
    }

    public void setManagerEmail(String managerEmail) {
        this.managerEmail = managerEmail;
    }

    @JsonProperty("user_name")
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
