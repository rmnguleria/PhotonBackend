package com.expedia.web.rest.dto;


import java.util.List;

public class RequestRoleDTO {

    private List<RoleInfoDTO> requestedRoles;

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

    @Override
    public String toString() {
        return "RequestRoleDTO{" +
                "requestedRoles=" + requestedRoles +
                ", managerEmail='" + managerEmail + '\'' +
                '}';
    }
}
