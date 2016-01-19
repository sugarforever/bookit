package com.wiysoft.exceptions;

import com.wiysoft.persistence.model.User;

/**
 * Created by weiliyang on 1/11/16.
 */
public class UserIdNotFoundException extends UserManagementException {

    public UserIdNotFoundException(final String message, final Throwable cause, final User user) {
        super(message, cause, user);
    }
}
