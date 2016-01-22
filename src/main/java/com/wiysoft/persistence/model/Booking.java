package com.wiysoft.persistence.model;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by weiliyang on 1/11/16.
 */
@Entity
@Table(name = "t_booking", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"bookedFor", "bookable"})
})
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private Date bookedFor;
    @ManyToOne(fetch = FetchType.EAGER)
    private Bookable bookable;
    @ManyToOne(fetch = FetchType.EAGER)
    private User holder;

    public long getId() {
        return id;
    }

    public void setId(final long id) {
        this.id = id;
    }

    public Date getBookedFor() {
        return bookedFor;
    }

    public void setBookedFor(final Date bookedFor) {
        this.bookedFor = bookedFor;
    }

    public Bookable getBookable() {
        return bookable;
    }

    public void setBookable(final Bookable bookable) {
        this.bookable = bookable;
    }

    public User getHolder() {
        return holder;
    }

    public void setHolder(final User holder) {
        this.holder = holder;
    }
}
