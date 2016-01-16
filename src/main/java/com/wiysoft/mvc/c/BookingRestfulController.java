package com.wiysoft.mvc.c;

import com.wiysoft.common.CommonUtils;
import com.wiysoft.common.DateDescComparator;
import com.wiysoft.mvc.m.CreateBookingRequest;
import com.wiysoft.mvc.m.RestfulBooking;
import com.wiysoft.mvc.m.RestfulResponse;
import com.wiysoft.persistence.model.Bookable;
import com.wiysoft.persistence.model.Booking;
import com.wiysoft.persistence.model.User;
import com.wiysoft.persistence.repository.BookableRepository;
import com.wiysoft.persistence.repository.BookingRepository;
import com.wiysoft.service.BookingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by weiliyang on 7/24/15.
 */
@RestController
@RequestMapping("/rest/booking/")
public class BookingRestfulController {

    private final static Logger logger = LoggerFactory.getLogger(BookingRestfulController.class);

    @Autowired
    private BookableRepository bookableRepository;
    @Autowired
    private BookingRepository bookingRepository;
    @Autowired
    private BookingService bookingService;

    @RequestMapping(value = "/create/", method = RequestMethod.POST)
    public Object makeBooking(@RequestBody CreateBookingRequest createBookingRequest, HttpSession session, HttpServletResponse response) {
        if (session.getAttribute("user") == null || !(session.getAttribute("user") instanceof User)) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return null;
        }

        for (CreateBookingRequest.Item item : createBookingRequest.getItems()) {
            Date bookedFor = CommonUtils.parseStrToDate(item.getBookedFor(), "yyyy-MM-dd");

            Bookable bookable = bookableRepository.findOne(item.getBookableId());
            if (bookable == null) {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                return new RestfulResponse(HttpServletResponse.SC_NOT_FOUND, String.format("ID %d you are going to book doesn't exist.", item.getBookableId()));
            }

            User loginUser = (User) session.getAttribute("user");
            Booking booking = new Booking();
            booking.setBookable(bookable);
            booking.setBookedFor(bookedFor);
            booking.setHolder(loginUser);
            bookingRepository.save(booking);
        }
        return new RestfulResponse(200, "Booking created.", null);
    }

    @RequestMapping(value = "/list/", method = RequestMethod.GET)
    public Object listBooking(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date bookedFor, HttpSession session, HttpServletResponse response) {
        if (session.getAttribute("user") == null || !(session.getAttribute("user") instanceof User)) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return new RestfulResponse(HttpServletResponse.SC_UNAUTHORIZED, "Not logged in yet.", new ArrayList(0));
        }

        User loginUser = (User) session.getAttribute("user");

        List<Booking> bookings = bookingRepository.findAllByHolderAndBookedFor(loginUser, bookedFor);
        final List<RestfulBooking> restfulBookings = new ArrayList<RestfulBooking>();
        for (Booking b : bookings)
            restfulBookings.add(RestfulBooking.build(b));

        return new RestfulResponse(200, "", restfulBookings);
    }

    @RequestMapping(value = "/delete/", method = RequestMethod.GET)
    public Object deleteBooking(@RequestParam long bookingId, HttpSession session, HttpServletResponse response) {
        if (session.getAttribute("user") == null || !(session.getAttribute("user") instanceof User)) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return new RestfulResponse(HttpServletResponse.SC_UNAUTHORIZED, "Not logged in yet.", new ArrayList(0));
        }

        bookingRepository.delete(bookingId);
        return new RestfulResponse(200, "", null);
    }

    @RequestMapping(value = "/bookedfor/list/", method = RequestMethod.GET)
    public Object listBookedFor(@RequestParam(required = false) Integer page, HttpSession session, HttpServletResponse response) {
        if (session.getAttribute("user") == null || !(session.getAttribute("user") instanceof User)) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return new RestfulResponse(HttpServletResponse.SC_UNAUTHORIZED, "Not logged in yet.", new ArrayList(0));
        }

        User loginUser = (User) session.getAttribute("user");

        int pageIndex = (page == null ? 0 : page - 1);
        Page resultPage = bookingService.findAllBookedForByHolder(loginUser, pageIndex, 10);
        Hashtable<String, Booking> bookings = bookingService.findAllBookingsByHolderAndBookedFors(loginUser, resultPage.getContent());
        List<Date> bookedForDates = new ArrayList();
        for (String str : bookings.keySet()) {
            bookedForDates.add(CommonUtils.parseStrToDate(str, "yyyy-MM-dd"));
        }
        Collections.sort(bookedForDates, new DateDescComparator());

        List strs = bookedForDates.stream().map(bookedFor -> CommonUtils.parseStrFromDate(bookedFor, "yyyy-MM-dd")).collect(Collectors.toList());
        Hashtable hash = new Hashtable();
        hash.put("bookings", bookings);
        hash.put("bookedFors", strs);

        return new RestfulResponse(200, "", hash);
    }

    @RequestMapping(value = "/cancel/", method = RequestMethod.GET)
    public Object cancelBooking(@RequestParam long bookingId, HttpSession session, HttpServletResponse response) {
        if (session.getAttribute("user") == null || !(session.getAttribute("user") instanceof User)) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return new RestfulResponse(HttpServletResponse.SC_UNAUTHORIZED, "Not logged in yet.", new ArrayList(0));
        }

        User loginUser = (User) session.getAttribute("user");
        Integer countDeleted = bookingRepository.deleteByIdAndHolder(bookingId, loginUser);
        Hashtable hash = new Hashtable();
        hash.put("deleted", countDeleted);
        return new RestfulResponse(200, "", hash);
    }
}
