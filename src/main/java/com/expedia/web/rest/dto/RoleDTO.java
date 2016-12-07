package com.expedia.web.rest.dto;


public class RoleDTO {

    private Integer groupRoleId;

    private String tenant;

    private String pos;

    private String role;

    private String status;

    private String approverMail;

    public RoleDTO() {
    }

    public RoleDTO(Integer groupRoleId, String tenant, String pos, String role, String status, String approverMail) {
        this.groupRoleId = groupRoleId;
        this.tenant = tenant;
        this.pos = pos;
        this.role = role;
        this.status = status;
        this.approverMail = approverMail;
    }

    public Integer getGroupRoleId() {
        return groupRoleId;
    }

    public void setGroupRoleId(Integer groupRoleId) {
        this.groupRoleId = groupRoleId;
    }

    public String getTenant() {
        return tenant;
    }

    public void setTenant(String tenant) {
        this.tenant = tenant;
    }

    public String getPos() {
        return pos;
    }

    public void setPos(String pos) {
        this.pos = pos;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getApproverMail() {
        return approverMail;
    }

    public void setApproverMail(String approverMail) {
        this.approverMail = approverMail;
    }

    @Override
    public String toString() {
        return "RoleDTO{" +
                "groupRoleId=" + groupRoleId +
                ", tenant='" + tenant + '\'' +
                ", pos='" + pos + '\'' +
                ", role='" + role + '\'' +
                ", status='" + status + '\'' +
                ", approverMail='" + approverMail + '\'' +
                '}';
    }
}
