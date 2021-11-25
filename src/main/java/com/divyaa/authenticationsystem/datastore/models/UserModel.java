package com.divyaa.authenticationsystem.datastore.models;

import javax.persistence.*;

/**
 * @author Divyaa P
 */
@Entity
@Table(name = "user")
public class UserModel {

    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String emailId;
    private String password;
    private String refreshToken;
    private boolean active = true;

    public UserModel(String name, String emailId, String password) {
        this.name = name;
        this.emailId = emailId;
        this.password = password;
    }

    public UserModel() {
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmailId() {
        return emailId;
    }

    public String getPassword() {
        return password;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public boolean isActive() {
        return active;
    }
}
