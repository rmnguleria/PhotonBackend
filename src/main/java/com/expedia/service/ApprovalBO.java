package com.expedia.service;

/**
 * Created by rguleria on 09/11/16.
 */
public class ApprovalBO {

    private String action;
    private String roleRequests;
    private String managerEmail;
    private Integer userId;

    public ApprovalBO(){}

    public ApprovalBO(String action, String roleRequests, String managerEmail,Integer userId) {
        this.action = action;
        this.roleRequests = roleRequests;
        this.managerEmail = managerEmail;
        this.userId = userId;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getRoleRequests() {
        return roleRequests;
    }

    public void setRoleRequests(String roleRequests) {
        this.roleRequests = roleRequests;
    }

    public String getManagerEmail() {
        return managerEmail;
    }

    public void setManagerEmail(String managerEmail) {
        this.managerEmail = managerEmail;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "ApprovalBO{" +
                "action='" + action + '\'' +
                ", roleRequests='" + roleRequests + '\'' +
                ", managerEmail='" + managerEmail + '\'' +
                ", userId=" + userId +
                '}';
    }
}
