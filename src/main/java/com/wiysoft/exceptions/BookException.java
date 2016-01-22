package com.wiysoft.exceptions;

import com.wiysoft.persistence.model.User;

/**
 * Created by weiliyang on 1/11/16.
 */
public class BookException extends Exception {

    private User user;

    public BookException(final String message, final Throwable cause, final User user) {
        super(message, cause);
        this.user = user;
    }
}
