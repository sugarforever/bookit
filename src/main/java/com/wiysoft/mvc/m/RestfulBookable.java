package com.wiysoft.mvc.m;

import com.wiysoft.persistence.model.Bookable;

import java.util.Date;

/**
 * Created by weiliyang on 1/11/16.
 */
public class RestfulBookable {

    private long id;
    private String name;
    private Date lastModified;

    public long getId() {
        return id;
    }

    public void setId(final long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public Date getLastModified() {
        return lastModified;
    }

    public void setLastModified(final Date lastModified) {
        this.lastModified = lastModified;
    }

    public static final RestfulBookable build(Bookable bookable) {
        if (bookable == null)
            return null;

        RestfulBookable restfulBookable = new RestfulBookable();
        restfulBookable.setId(bookable.getId());
        restfulBookable.setLastModified(bookable.getLastModified());
        restfulBookable.setName(bookable.getName());

        return restfulBookable;
    }
}
