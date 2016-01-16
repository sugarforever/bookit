package com.wiysoft.mvc.m;

import com.wiysoft.persistence.model.User;

/**
 * Created by weiliyang on 1/11/16.
 */
public class RestfulUser {
    private long id;
    private String name;

    public RestfulUser(final long id, final String name) {
        this.id = id;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public static final RestfulUser build(User user) {
        if (user == null)
            return null;

        return new RestfulUser(user.getId(), user.getName());
    }
}
