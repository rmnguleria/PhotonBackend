package com.expedia.web.rest.dto;


import java.util.List;

public class RequestRoleDTO {

    private List<RoleInfoDTO> requestedRoles;

    private String userId;

    private String userName;

    private String userEmail;

    private String managerEmail;

    public List<RoleInfoDTO> getRequestedRoles() {
        return requestedRoles;
    }

    public void setRequestedRoles(List<RoleInfoDTO> requestedRoles) {
        this.requestedRoles = requestedRoles;
    }

    public String getManagerEmail() {
        return managerEmail;
    }

    public void setManagerEmail(String managerEmail) {
        this.managerEmail = managerEmail;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public String toString() {
        return "RequestRoleDTO{" +
                "requestedRoles=" + requestedRoles +
                ", userId='" + userId + '\'' +
                ", userName='" + userName + '\'' +
                ", userEmail='" + userEmail + '\'' +
                ", managerEmail='" + managerEmail + '\'' +
                '}';
    }
}
