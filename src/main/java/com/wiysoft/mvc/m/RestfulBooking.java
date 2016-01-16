package com.wiysoft.mvc.m;

import com.wiysoft.persistence.model.Booking;

import java.util.Date;

/**
 * Created by weiliyang on 1/11/16.
 */

public class RestfulBooking {
    private long id;
    private Date bookedFor;
    private RestfulBookable bookable;
    private RestfulUser holder;

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

    public RestfulBookable getBookable() {
        return bookable;
    }

    public void setBookable(final RestfulBookable bookable) {
        this.bookable = bookable;
    }

    public RestfulUser getHolder() {
        return holder;
    }

    public void setHolder(final RestfulUser holder) {
        this.holder = holder;
    }

    public static final RestfulBooking build(Booking booking) {
        if (booking == null)
            return null;

        RestfulBooking restfulBooking = new RestfulBooking();
        restfulBooking.setBookable(RestfulBookable.build(booking.getBookable()));
        restfulBooking.setBookedFor(booking.getBookedFor());
        restfulBooking.setHolder(RestfulUser.build(booking.getHolder()));
        restfulBooking.setId(booking.getId());

        return restfulBooking;
    }
}
