package com.wiysoft.mvc.m.forms;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * Created by weiliyang on 1/25/16.
 */
public class ChangePasswordForm {

    @NotEmpty
    private String password;
    @NotEmpty
    private String newPassword;

    public String getPassword() {
        return password;
    }

    public void setPassword(final String password) {
        this.password = password;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(final String newPassword) {
        this.newPassword = newPassword;
    }
}
