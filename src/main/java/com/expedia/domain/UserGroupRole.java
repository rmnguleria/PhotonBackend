package com.expedia.domain;


import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Table(name = "usergrouprole")
public class UserGroupRole implements Serializable{

    private static final long serialVersionUID = 6L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

    @NotNull
    @Column(name = "userid")
    private Integer userId;

    @NotNull
    @Column(name = "grouproleid")
    private Integer groupRoleId;

    @NotNull
    @Column(name = "status")
    private String status;

    @Column(name = "approvermail")
    private String approverMail;

    public UserGroupRole(){}

    public UserGroupRole(Integer userId, Integer groupRoleId, String status, String approverMail) {
        this.userId = userId;
        this.groupRoleId = groupRoleId;
        this.status = status;
        this.approverMail = approverMail;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getGroupRoleId() {
        return groupRoleId;
    }

    public void setGroupRoleId(Integer groupRoleId) {
        this.groupRoleId = groupRoleId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getApproverMail() {
        return approverMail;
    }

    public void setApproverMail(String approverMail) {
        this.approverMail = approverMail;
    }

    @Override
    public String toString() {
        return "UserGroupRole{" +
                "id=" + id +
                ", userId=" + userId +
                ", groupRoleId=" + groupRoleId +
                ", status='" + status + '\'' +
                ", approverMail='" + approverMail + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserGroupRole that = (UserGroupRole) o;

        return id != null ? id.equals(that.id) : that.id == null;

    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
