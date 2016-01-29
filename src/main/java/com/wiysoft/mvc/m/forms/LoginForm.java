package com.wiysoft.mvc.m.forms;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * Created by weiliyang on 1/25/16.
 */
public class LoginForm {

    @NotEmpty
    private String name;
    @NotEmpty
    private String password;

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(final String password) {
        this.password = password;
    }
}
