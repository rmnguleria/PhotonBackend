package com.expedia.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Entity
@Table(name = "groups")
public class Groups implements Serializable{

    private static final long serialVersionUID = 3L;

    @Id
    @NotNull
    @Column(name = "id")
    private Integer id;

    @Size(min=0,max = 255)
    @Column(name = "name")
    private String name;

    @Size(min=0,max = 255)
    @Column(name = "status")
    private String status;

    @Size(min=0,max = 255)
    @Column(name = "type")
    private String type;

    @Column(name="parentid")
    private Integer parentId;

    @Size(min=0,max = 255)
    @Column(name="description")
    private String description;

    @Size(min=0,max = 255)
    @Column(name="couponredemptionurl")
    private String couponRedemptionURL;

    @Column(name="tpid")
    private Integer tpid;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public String getCouponRedemptionURL() {
        return couponRedemptionURL;
    }

    public void setCouponRedemptionURL(String couponRedemptionURL) {
        this.couponRedemptionURL = couponRedemptionURL;
    }

    public Integer getTpid() {
        return tpid;
    }

    public void setTpid(Integer tpid) {
        this.tpid = tpid;
    }

    @Override
    public String toString() {
        return "Groups{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", status='" + status + '\'' +
                ", type='" + type + '\'' +
                ", parentId=" + parentId +
                ", description='" + description + '\'' +
                ", couponRedemptionURL='" + couponRedemptionURL + '\'' +
                ", tpid=" + tpid +
                '}';
    }
}
