package com.wiysoft.mvc.c;

import com.wiysoft.common.CommonUtils;
import com.wiysoft.common.DateDescComparator;
import com.wiysoft.exceptions.UserManagementException;
import com.wiysoft.persistence.model.Bookable;
import com.wiysoft.persistence.model.User;
import com.wiysoft.persistence.repository.BookableRepository;
import com.wiysoft.persistence.repository.UserRepository;
import com.wiysoft.service.BookingService;
import com.wiysoft.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * Created by weiliyang on 7/24/15.
 */
@Controller
@RequestMapping("/")
public class HttpController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BookableRepository bookableRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private BookingService bookingService;

    @RequestMapping("/")
    public String root() {
        return "index";
    }

    @RequestMapping("/index.html")
    public String indexHtml() {
        return "index";
    }

    @RequestMapping(value = "/signup.html", method = RequestMethod.GET)
    public String signupGet() {
        return "signup";
    }

    @RequestMapping(value = "/signup.html", method = RequestMethod.POST)
    public String signupPost(@RequestParam String name, @RequestParam String password, @RequestParam String email, HttpServletRequest request, HttpSession session) throws Exception {
        try {
            User savedUser = userService.createUser(name, email, password);
            session.setAttribute("user", savedUser);
            return "redirect:/index.html";
        } catch (UserManagementException ex) {
            request.setAttribute("error", ex.getMessage());
            return "signup";
        }
    }

    @RequestMapping(value = "/login.html", method = RequestMethod.GET)
    public String loginGet() {
        return "login";
    }

    @RequestMapping(value = "/login.html", method = RequestMethod.POST)
    public String loginPost(@RequestParam String name, @RequestParam String password, HttpServletRequest request, HttpSession session, RedirectAttributes redirectAttributes) throws Exception {
        User existed = userRepository.findByNameAndPassword(name, password);

        session.setAttribute("user", existed);
        if (existed != null) {
            return "redirect:/index.html";
        } else {
            request.setAttribute("error", "用户名与密码不正确");
            return "login";
        }
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logoutGet(HttpSession session) throws Exception {
        session.setAttribute("user", null);
        return "redirect:/index.html";
    }

    @RequestMapping(value = "/create-bookable.html", method = RequestMethod.GET)
    public String createBookableGet(HttpSession session) {
        Object loginUser = session.getAttribute("user");
        if (loginUser == null || !(loginUser instanceof User)) {
            return "redirect:/login.html";
        } else {
            return "createBookable";
        }
    }

    @RequestMapping(value = "/create-bookable.html", method = RequestMethod.POST)
    public String createBookablePost(@RequestParam String name, HttpServletRequest request, HttpSession session, RedirectAttributes redirectAttributes) throws Exception {
        Object loginUser = session.getAttribute("user");
        if (loginUser == null || !(loginUser instanceof User)) {
            return "redirect:/login.html";
        } else {
            Bookable bookable = new Bookable();
            bookable.setName(name);
            bookable.setLastModified(Calendar.getInstance().getTime());
            bookable.setOwner((User) loginUser);
            bookableRepository.save(bookable);
            return "redirect:/my-bookable.html";
        }
    }

    @RequestMapping(value = "/my-bookable.html", method = RequestMethod.GET)
    public String bookableGet(@RequestParam(required = false) Integer page, HttpServletRequest request, HttpSession session) {
        User loginUser = null;
        Object o = session.getAttribute("user");
        if (o != null && (o instanceof User)) {
            loginUser = (User) o;
        }

        //Page<Bookable> result = bookableRepository.findAllByOwnerOrderByLastModifiedDesc(loginUser, new PageRequest(page == null ? 0 : page - 1, 20));
        List<Bookable> result = bookableRepository.findAllByOwnerOrderByLastModifiedDesc(loginUser);
        request.setAttribute("bookables", result);
        request.setAttribute("currentPage", page);
        return "my-bookable";
    }

    @RequestMapping(value = "/bookable.html", method = RequestMethod.GET)
    public String bookableGet(@RequestParam long ownerId, HttpServletRequest request, HttpSession session) {
        User owner = userService.getUser(ownerId);
        if (owner != null)
            request.setAttribute("owner", owner);
        return "bookable";
    }

    @RequestMapping(value = "/supplier.html", method = RequestMethod.GET)
    public String supplierGet(@RequestParam(required = false) Integer page, HttpServletRequest request) {
        int pageIndex = page == null ? 0 : page - 1;
        PageRequest pageRequest = new PageRequest(pageIndex, 100);
        Page resultPage = userRepository.findAll(pageRequest);
        int totalPages = resultPage.getTotalPages();

        List pages = new ArrayList();
        for (int i = 1; i <= resultPage.getTotalPages(); ++i) {
            pages.add(i);
        }
        request.setAttribute("pages", pages);
        request.setAttribute("suppliers", resultPage.getContent());
        request.setAttribute("currentPage", pageIndex + 1);
        return "supplier";
    }

    @RequestMapping(value = "/booking.html", method = RequestMethod.GET)
    public String bookingGet(@RequestParam(required = false) Integer page, HttpServletRequest request, HttpSession session) {
        Object loginUser = session.getAttribute("user");
        if (loginUser == null || !(loginUser instanceof User)) {
            return "redirect:/login.html";
        }
        int pageIndex = (page == null ? 0 : page - 1);
        Page resultPage = bookingService.findAllBookedForByHolder((User) session.getAttribute("user"), pageIndex, 10);

        Hashtable<String, List> bookings = bookingService.findAllBookingsByHolderAndBookedFors((User) session.getAttribute("user"), (List<Date>) resultPage.getContent());
        List<Date> bookedForDates = new ArrayList();
        for (String str : bookings.keySet()) {
            bookedForDates.add(CommonUtils.parseStrToDate(str, "yyyy-MM-dd"));
        }
        Collections.sort(bookedForDates, new DateDescComparator());

        List strs = new ArrayList();
        for (Date bookedFor : bookedForDates)
            strs.add(CommonUtils.parseStrFromDate(bookedFor, "yyyy-MM-dd"));

        List pages = new ArrayList();
        for (int i = 1; i <= resultPage.getTotalPages(); ++i) {
            pages.add(i);
        }
        request.setAttribute("bookings", bookings);
        request.setAttribute("bookedFors", strs);
        request.setAttribute("pages", pages);
        request.setAttribute("currentPage", pageIndex + 1);


        return "booking";
    }
}
