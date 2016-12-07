package com.expedia.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Table(name = "grouprole")
public class GroupRole extends AbstractAuditingEntity implements Serializable{

    private static final long serialVersionUID = 2L;

    @Id
    @NotNull
    @Column(name = "id")
    private int id;

    @NotNull
    @Column(name = "groupid")
    private int groupId;

    @NotNull
    @Column(name = "roleid")
    private int roleId;

    @NotNull
    @Column(name = "status")
    private String status;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GroupRole groupRole = (GroupRole) o;

        if (groupId != groupRole.groupId) return false;
        return roleId == groupRole.roleId;

    }

    @Override
    public int hashCode() {
        int result = groupId;
        result = 31 * result + roleId;
        return result;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "GroupRole{" +
                "id=" + id +
                ", groupId=" + groupId +
                ", roleId=" + roleId +
                ", status='" + status + '\'' +
                '}';
    }
}
