package com.wiysoft.mvc.c;

import com.wiysoft.common.CommonUtils;
import com.wiysoft.common.DateTimeUtils;
import com.wiysoft.mvc.m.RestfulBookable;
import com.wiysoft.persistence.model.Bookable;
import com.wiysoft.persistence.model.Booking;
import com.wiysoft.persistence.repository.BookableRepository;
import com.wiysoft.persistence.repository.BookingRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * Created by weiliyang on 7/24/15.
 */
@RestController
@RequestMapping("/rest/bookable/")
public class BookableRestfulController {

    private final static Logger logger = LoggerFactory.getLogger(BookableRestfulController.class);

    @Autowired
    private BookableRepository bookableRepository;
    @Autowired
    private BookingRepository bookingRepository;

    @RequestMapping("/list/")
    public Object getBookables(@RequestParam long ownerId, @DateTimeFormat(pattern = "yyyy-MM-dd") Date dateStart, @DateTimeFormat(pattern = "yyyy-MM-dd") Date dateEnd, HttpServletRequest request, HttpSession session) {
        List<Bookable> bookables = bookableRepository.findAllByOwnerId(ownerId);
        List<Booking> bookings = bookingRepository.findAllByOwnerIdAndBookedForBetween(ownerId, dateStart, DateTimeUtils.dateAdjust(dateEnd, Calendar.DAY_OF_YEAR, 1));

        List<RestfulBookable> restfulBookables = new ArrayList<RestfulBookable>();
        for (Bookable bookable : bookables) {
            restfulBookables.add(RestfulBookable.build(bookable));
        }

        Hashtable<String, List> hashtable = new Hashtable<String, List>();
        for (Booking booking : bookings) {
            String strDate = CommonUtils.parseStrFromDate(booking.getBookedFor(), "yyyy-MM-dd");
            if (!hashtable.containsKey(strDate)) {
                hashtable.put(strDate, new ArrayList());
            }

            hashtable.get(strDate).add(RestfulBookable.build(booking.getBookable()));
        }

        Hashtable hash = new Hashtable();
        hash.put("bookables", restfulBookables);
        hash.put("booked", hashtable);
        return hash;
    }
}
