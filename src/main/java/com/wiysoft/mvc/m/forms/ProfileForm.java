package com.wiysoft.mvc.m.forms;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * Created by weiliyang on 1/25/16.
 */
public class ProfileForm {

    @NotEmpty
    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(final String email) {
        this.email = email;
    }
}
