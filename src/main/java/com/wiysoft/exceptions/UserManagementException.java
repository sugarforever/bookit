package com.wiysoft.exceptions;

import com.wiysoft.persistence.model.User;

/**
 * Created by weiliyang on 1/11/16.
 */
public class UserManagementException extends Exception {

    private User user;

    public UserManagementException(final String message, final Throwable cause, final User user) {
        super(message, cause);
        this.user = user;
    }
}
