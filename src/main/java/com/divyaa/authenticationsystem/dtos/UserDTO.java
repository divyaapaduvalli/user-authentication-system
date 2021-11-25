package com.divyaa.authenticationsystem.dtos;

/**
 * @author Divyaa P
 */
public class UserDTO {

    private Long id;
    private String name;
    private String password;
    private String emailId;
    private String refreshToken;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setUserId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String emailId) {
        this.emailId = emailId;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
