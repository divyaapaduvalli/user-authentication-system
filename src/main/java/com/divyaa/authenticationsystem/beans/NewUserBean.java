package com.divyaa.authenticationsystem.beans;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author Divyaa P
 */
public class NewUserBean {
    @NotNull(message = "User name cannot be null")
    private String username;

    @NotNull(message = "Email id cannot be null")
    @Email
    private String emailId;

    @NotNull(message = "Password cannot be null")
    @Size(min = 8, max = 16, message = "Password must have equal to or greater than 8 characters and less than 16 characters")
    private String password;

    public String getUsername() {
        return username;
    }

    public String getEmailId() {
        return emailId;
    }

    public String getPassword() {
        return password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
