package com.wiysoft.mvc.m;

import java.util.List;

/**
 * Created by weiliyang on 1/13/16.
 */
public class CreateBookingRequest {

    private List<Item> items;

    public List<Item> getItems() {
        return items;
    }

    public void setItems(final List<Item> items) {
        this.items = items;
    }

    public static class Item {
        private String bookedFor;
        private long bookableId;

        public String getBookedFor() {
            return bookedFor;
        }

        public void setBookedFor(final String bookedFor) {
            this.bookedFor = bookedFor;
        }

        public long getBookableId() {
            return bookableId;
        }

        public void setBookableId(final long bookableId) {
            this.bookableId = bookableId;
        }
    }
}
