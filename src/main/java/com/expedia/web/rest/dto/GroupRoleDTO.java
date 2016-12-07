package com.expedia.web.rest.dto;


public class GroupRoleDTO {

    private Integer groupRoleId;

    private String tenant;

    private String pos;

    private String role;

    public GroupRoleDTO() {
    }

    public GroupRoleDTO(Integer groupRoleId, String tenant, String pos, String role) {
        this.groupRoleId = groupRoleId;
        this.tenant = tenant;
        this.pos = pos;
        this.role = role;
    }

    public Integer getGroupRoleId() {
        return groupRoleId;
    }

    public void setGroupRoleId(Integer groupRoleId) {
        this.groupRoleId = groupRoleId;
    }

    public String getPos() {
        return pos;
    }

    public void setPos(String pos) {
        this.pos = pos;
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
}
