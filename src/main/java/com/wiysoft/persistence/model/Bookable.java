package com.wiysoft.persistence.model;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by weiliyang on 1/11/16.
 */
@Entity
@Table(name = "t_bookable")
public class Bookable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, unique = true)
    private String name;
    @Column(nullable = false)
    private Date lastModified;

    @ManyToOne
    private User owner;

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

    public User getOwner() {
        return owner;
    }

    public void setOwner(final User owner) {
        this.owner = owner;
    }
}
