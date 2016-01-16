package com.wiysoft.persistence.model;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

/**
 * Created by weiliyang on 1/11/16.
 */
@Entity
@Table(name = "t_user", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"email"}),
        @UniqueConstraint(columnNames = {"name"})
})
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, unique = true)
    private String email;
    @Column(nullable = false, unique = true)
    private String name;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private int status;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Bookable> bookables;

    public long getId() {
        return id;
    }

    public void setId(final long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

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

    public int getStatus() {
        return status;
    }

    public void setStatus(final int status) {
        this.status = status;
    }

    public Set<Bookable> getBookables() {
        return bookables;
    }

    public void setBookables(final Set<Bookable> bookables) {
        this.bookables = bookables;
    }
}
