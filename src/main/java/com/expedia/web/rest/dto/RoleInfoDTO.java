package com.expedia.web.rest.dto;


public class RoleInfoDTO {

    private Integer groupRoleId;

    private String tenant;

    private String pos;

    private String role;

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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getPos() {
        return pos;
    }

    public void setPos(String pos) {
        this.pos = pos;
    }

    @Override
    public String toString() {
        return "RoleInfoDTO{" +
                "groupRoleId=" + groupRoleId +
                ", tenant='" + tenant + '\'' +
                ", pos='" + pos + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}
