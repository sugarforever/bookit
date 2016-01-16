package com.wiysoft.service;

import com.wiysoft.common.CommonUtils;
import com.wiysoft.common.DateTimeUtils;
import com.wiysoft.exceptions.DuplicateUserEmailException;
import com.wiysoft.exceptions.DuplicateUserNameException;
import com.wiysoft.exceptions.UserManagementException;
import com.wiysoft.mvc.m.RestfulBooking;
import com.wiysoft.persistence.model.Bookable;
import com.wiysoft.persistence.model.Booking;
import com.wiysoft.persistence.model.User;
import com.wiysoft.persistence.repository.BookingRepository;
import com.wiysoft.persistence.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * Created by weiliyang on 1/11/16.
 */
@Service
public class BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Transactional
    public Booking book(User user, Bookable bookable, Date bookedFor) throws UserManagementException {
        Booking booking = new Booking();
        booking.setBookable(bookable);
        booking.setHolder(user);
        booking.setBookedFor(bookedFor);

        return bookingRepository.save(booking);
    }

    @Transactional
    public Page findAllBookedForByHolder(User holder, int page, int size) {
        PageRequest pageRequest = new PageRequest(page, size,
                new Sort(Sort.Direction.DESC, Arrays.asList(new String[]{"bookedFor"})));

        return bookingRepository.findBookedForByHolder(holder, pageRequest);
    }

    @Transactional
    public Hashtable findAllBookingsByHolderAndBookedFors(User holder, List<Date> bookedFors) {
        Hashtable<String, List> hash = new Hashtable();
        Date min = null;
        Date max = null;
        for (Date bookedFor : bookedFors) {
            if (min == null || min.after(bookedFor))
                min = bookedFor;

            if (max == null || max.before(bookedFor))
                max = bookedFor;

            hash.put(CommonUtils.parseStrFromDate(bookedFor, "yyyy-MM-dd"), new ArrayList());
        }

        Date start = min;
        Date end = DateTimeUtils.dateAdjust(max, Calendar.DAY_OF_YEAR, 1);

        List<Booking> bookings = bookingRepository.findAllByHolderAndBookedForBetween(holder, start, end);
        for (Booking booking : bookings) {
            String strBookedFor = CommonUtils.parseStrFromDate(booking.getBookedFor(), "yyyy-MM-dd");
            if (hash.containsKey(strBookedFor)) {
                hash.get(strBookedFor).add(RestfulBooking.build(booking));
            }
        }
        return hash;
    }

    @Transactional
    public Integer deleteByIdAndHolder(long id, User holder) {
        return bookingRepository.deleteByIdAndHolder(id, holder);
    }
}
