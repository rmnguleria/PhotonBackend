package com.expedia.domain;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.ZonedDateTime;

@Entity
@Table(name = "user")
public class User extends AbstractAuditingEntity implements Serializable{

    private static final long serialVersionUID = 5L;

    @Id
    @NotNull
    @Column(name = "id")
    private int id;

    @NotNull
    @Size(min = 1,max = 255)
    @Column(name = "login")
    private String login;

    @NotNull
    @Size(min = 1,max = 255)
    @Column(name = "firstname")
    private String firstName;

    @NotNull
    @Size(min = 1,max = 255)
    @Column(name = "lastname")
    private String lastName;

    @NotNull
    @Size(min = 1,max = 255)
    @Column(name = "status")
    private String status;

    @Column(name = "email")
    private String email;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        return login != null ? login.equals(user.login) : user.login == null;

    }

    @Override
    public int hashCode() {
        return login != null ? login.hashCode() : 0;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public User(){

    }

    public User(int id, String login, String lastName, String firstName, String status, String email, ZonedDateTime createDate,String createdBy,ZonedDateTime modifyDate,String modifiedBy) {
        this.id = id;
        this.login = login;
        this.lastName = lastName;
        this.firstName = firstName;
        this.status = status;
        this.email = email;
        this.setCreateDate(createDate);
        this.setCreatedBy(createdBy);
        this.setModifyDate(modifyDate);
        this.setModifiedBy(modifiedBy);
    }

    public User(String login, String firstName, String lastName, String status) {
        this.login = login;
        this.firstName = firstName;
        this.lastName = lastName;
        this.status = status;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", status='" + status + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
