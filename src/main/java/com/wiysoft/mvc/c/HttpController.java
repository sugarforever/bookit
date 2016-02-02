package com.wiysoft.mvc.c;

import com.wiysoft.common.CommonUtils;
import com.wiysoft.common.DateDescComparator;
import com.wiysoft.exceptions.UserIdNotFoundException;
import com.wiysoft.exceptions.UserManagementException;
import com.wiysoft.exceptions.WrongPasswordException;
import com.wiysoft.mvc.m.forms.*;
import com.wiysoft.mvc.v.ViewConstants;
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
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
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
        return ViewConstants.VIEW_CLASSIC_TEMPLATE;
    }

    @RequestMapping(value = "/signup.html", method = RequestMethod.GET)
    public String signupGet(HttpServletRequest request) {
        ControllerUtils.fillTemplate(request, Arrays.asList(new String[]{"signup"}), true, false, true);
        return ViewConstants.VIEW_CLASSIC_TEMPLATE;
    }

    @RequestMapping(value = "/signup.html", method = RequestMethod.POST)
    public String signupPost(@Valid SignupForm signupForm, BindingResult bindingResult, HttpServletRequest request, HttpSession session) throws Exception {
        if (bindingResult.hasErrors()) {
            request.setAttribute("errors", bindingResult.getAllErrors());
            ControllerUtils.fillTemplate(request, Arrays.asList(new String[]{"signup"}), true, false, true);
            return ViewConstants.VIEW_CLASSIC_TEMPLATE;
        } else {
            try {
                User savedUser = userService.createUser(signupForm.getName(), signupForm.getEmail(), signupForm.getPassword());
                session.setAttribute("user", savedUser);
                return "redirect:/index.html";
            } catch (UserManagementException ex) {
                request.setAttribute("error", ex.getMessage());
                ControllerUtils.fillTemplate(request, Arrays.asList(new String[]{"signup"}), true, false, true);
                return ViewConstants.VIEW_CLASSIC_TEMPLATE;
            }
        }
    }

    @RequestMapping(value = "/profile.html", method = RequestMethod.GET)
    public String profileGet(HttpServletRequest request, HttpSession session) {
        Object loginUser = session.getAttribute("user");
        if (loginUser == null || !(loginUser instanceof User)) {
            return "redirect:/login.html";
        } else {
            ControllerUtils.fillTemplate(request, Arrays.asList(new String[]{"profile"}), true, true, true);
            return ViewConstants.VIEW_CLASSIC_TEMPLATE;
        }
    }

    @RequestMapping(value = "/profile.html", method = RequestMethod.POST)
    public String profilePost(@Valid @ModelAttribute ProfileForm profileForm, BindingResult bindingResult, HttpServletRequest request, HttpSession session) throws Exception {
        if (bindingResult.hasErrors()) {
            request.setAttribute("errors", bindingResult.getAllErrors());
            ControllerUtils.fillTemplate(request, Arrays.asList(new String[]{"profile"}));
            return ViewConstants.VIEW_CLASSIC_TEMPLATE;
        }

        Object loginUser = session.getAttribute("user");
        if (loginUser == null || !(loginUser instanceof User)) {
            return "redirect:/login.html";
        }

        User login = (User) loginUser;
        login.setEmail(profileForm.getEmail());
        User updated = userRepository.save(login);
        session.setAttribute("user", updated);
        return "redirect:/index.html";
    }

    @RequestMapping(value = "/login.html", method = RequestMethod.GET)
    public Object loginGet(HttpServletRequest request) {
        ModelAndView model = new ModelAndView(ViewConstants.VIEW_CLASSIC_TEMPLATE);
        model.addObject("loginForm", new LoginForm());
        ControllerUtils.fillTemplate(request, Arrays.asList(new String[]{"login"}), true, false, true);
        return model;
    }

    @RequestMapping(value = "/login.html", method = RequestMethod.POST)
    public Object loginPost(@Valid @ModelAttribute("loginForm") LoginForm loginForm, BindingResult bindingResult, HttpServletRequest request, HttpSession session) throws Exception {
        if (!bindingResult.hasErrors()) {
            User existed = userRepository.findByNameAndPassword(loginForm.getName(), loginForm.getPassword());
            session.setAttribute("user", existed);
            if (existed != null) {
                return "redirect:/index.html";
            } else {
                request.setAttribute("error", "用户名与密码不正确");
                ControllerUtils.fillTemplate(request, Arrays.asList(new String[]{"login"}), true, false, true);
                return ViewConstants.VIEW_CLASSIC_TEMPLATE;
            }
        } else {
            request.setAttribute("errors", bindingResult.getAllErrors());
            ControllerUtils.fillTemplate(request, Arrays.asList(new String[]{"login"}), true, false, true);
            return ViewConstants.VIEW_CLASSIC_TEMPLATE;
        }
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logoutGet(HttpSession session) throws Exception {
        session.setAttribute("user", null);
        return "redirect:/index.html";
    }

    @RequestMapping(value = "/create-bookable.html", method = RequestMethod.GET)
    public String createBookableGet(HttpServletRequest request, HttpSession session) {
        Object loginUser = session.getAttribute("user");
        if (loginUser == null || !(loginUser instanceof User)) {
            return "redirect:/login.html";
        } else {
            ControllerUtils.fillTemplate(request, Arrays.asList(new String[]{"createBookable"}));
            return ViewConstants.VIEW_CLASSIC_TEMPLATE;
        }
    }

    @RequestMapping(value = "/create-bookable.html", method = RequestMethod.POST)
    public String createBookablePost(@Valid CreateBookableForm form, BindingResult bindingResult, HttpServletRequest request, HttpSession session, RedirectAttributes redirectAttributes) throws Exception {
        if (bindingResult.hasErrors()) {
            request.setAttribute("errors", bindingResult.getAllErrors());
            ControllerUtils.fillTemplate(request, Arrays.asList(new String[]{"createBookable"}));
            return ViewConstants.VIEW_CLASSIC_TEMPLATE;
        }

        Object loginUser = session.getAttribute("user");
        if (loginUser == null || !(loginUser instanceof User)) {
            return "redirect:/login.html";
        } else {
            Bookable bookable = new Bookable();
            bookable.setName(form.getName());
            bookable.setQuantity(form.getQuantity());
            bookable.setUnit(form.getUnit());
            bookable.setLastModified(Calendar.getInstance().getTime());
            bookable.setOwner((User) loginUser);
            bookableRepository.save(bookable);
            return "redirect:/my-bookable.html";
        }
    }

    @RequestMapping(value = "/change-password.html", method = RequestMethod.GET)
    public String changePasswordGet(HttpServletRequest request, HttpSession session) {
        Object loginUser = session.getAttribute("user");
        if (loginUser == null || !(loginUser instanceof User)) {
            return "redirect:/login.html";
        } else {
            ControllerUtils.fillTemplate(request, Arrays.asList(new String[]{"changePassword"}));
            return ViewConstants.VIEW_CLASSIC_TEMPLATE;
        }
    }

    @RequestMapping(value = "/change-password.html", method = RequestMethod.POST)
    public String changePasswordPost(@Valid @ModelAttribute ChangePasswordForm changePasswordForm, BindingResult bindingResult, HttpServletRequest request, HttpSession session) throws Exception {
        if (bindingResult.hasErrors()) {
            request.setAttribute("errors", bindingResult.getAllErrors());
            ControllerUtils.fillTemplate(request, Arrays.asList(new String[]{"changePassword"}));
            return ViewConstants.VIEW_CLASSIC_TEMPLATE;
        }

        Object loginUser = session.getAttribute("user");
        if (loginUser == null || !(loginUser instanceof User)) {
            return "redirect:/login.html";
        } else {
            try {
                User updatedUser = userService.changePassword(((User) loginUser).getId(), changePasswordForm.getPassword(), changePasswordForm.getNewPassword());
                request.setAttribute("user", updatedUser);
            } catch (UserManagementException ex) {
                if (ex instanceof WrongPasswordException) {
                    request.setAttribute("error", "当前密码错误");
                } else if (ex instanceof UserIdNotFoundException) {
                    request.setAttribute("error", "用户id不存在");
                } else {
                    request.setAttribute("error", "内部错误，请重试");
                }
                ControllerUtils.fillTemplate(request, Arrays.asList(new String[]{"changePassword"}));
                return ViewConstants.VIEW_CLASSIC_TEMPLATE;
            }
            return "redirect:/index.html";
        }
    }

    @RequestMapping(value = "/modify-bookable.html", method = RequestMethod.GET)
    public Object modifyBookableGet(@RequestParam(required = false) Long id, HttpServletRequest request, HttpServletResponse response, HttpSession session) {
        Object loginUser = session.getAttribute("user");
        if (loginUser == null || !(loginUser instanceof User)) {
            return "redirect:/login.html";
        } else if (id == null) {
            return "redirect:/my-bookable.html";
        } else {
            Bookable bookable = bookableRepository.findOne(id);

            ModelAndView model = new ModelAndView();
            if (bookable != null) {
                ControllerUtils.fillTemplate(request, Arrays.asList(new String[]{"modifyBookable"}));

                ModifyBookableForm form = new ModifyBookableForm();
                form.setId(bookable.getId());
                form.setName(bookable.getName());
                form.setQuantity(bookable.getQuantity());
                form.setUnit(bookable.getUnit());

                model.addObject("modifyBookableForm", form);
                model.setViewName(ViewConstants.VIEW_CLASSIC_TEMPLATE);
            } else {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            }

            return model;
        }
    }

    @RequestMapping(value = "/modify-bookable.html", method = RequestMethod.POST)
    public Object modifyBookablePost(@Valid ModifyBookableForm form, BindingResult bindingResult, HttpServletRequest request, HttpServletResponse response, HttpSession session, RedirectAttributes redirectAttributes) throws Exception {
        if (bindingResult.hasErrors()) {
            request.setAttribute("errors", bindingResult.getAllErrors());
            ModelAndView model = new ModelAndView(ViewConstants.VIEW_CLASSIC_TEMPLATE);
            model.addObject("modifyBookableForm", form);
            ControllerUtils.fillTemplate(request, Arrays.asList(new String[]{"modifyBookable"}));
            return model;
        }

        Object loginUser = session.getAttribute("user");
        if (loginUser == null || !(loginUser instanceof User)) {
            return "redirect:/login.html";
        } else {
            Bookable bookable = bookableRepository.findOne(form.getId());
            if (bookable != null) {
                bookable.setName(form.getName());
                bookable.setQuantity(form.getQuantity());
                bookable.setUnit(form.getUnit());
                bookable.setLastModified(Calendar.getInstance().getTime());
                bookableRepository.save(bookable);
                return "redirect:/my-bookable.html";
            } else {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                return null;
            }
        }
    }

    @RequestMapping(value = "/my-bookable.html", method = RequestMethod.GET)
    public String bookableGet(@RequestParam(required = false) Integer page, HttpServletRequest request, HttpSession session) {
        User loginUser = null;
        Object o = session.getAttribute("user");
        if (o != null && (o instanceof User)) {
            loginUser = (User) o;
        } else {
            return "redirect:/login.html";
        }

        //Page<Bookable> result = bookableRepository.findAllByOwnerOrderByLastModifiedDesc(loginUser, new PageRequest(page == null ? 0 : page - 1, 20));
        List<Bookable> result = bookableRepository.findAllByOwnerOrderByLastModifiedDesc(loginUser);
        request.setAttribute("bookables", result);
        request.setAttribute("currentPage", page);
        ControllerUtils.fillTemplate(request, Arrays.asList(new String[]{"my-bookable"}));
        return ViewConstants.VIEW_CLASSIC_TEMPLATE;
    }

    @RequestMapping(value = "/bookable.html", method = RequestMethod.GET)
    public String bookableGet(@RequestParam long ownerId, HttpServletRequest request, HttpSession session) {
        User owner = userService.getUser(ownerId);
        if (owner != null)
            request.setAttribute("owner", owner);
        ControllerUtils.fillTemplate(request, Arrays.asList(new String[]{"bookable"}));
        return ViewConstants.VIEW_CLASSIC_TEMPLATE;
    }

    @RequestMapping(value = "/supplier.html", method = RequestMethod.GET)
    public String supplierGet(@RequestParam(required = false) Integer page, HttpServletRequest request) {
        int pageIndex = page == null ? 0 : page - 1;
        PageRequest pageRequest = new PageRequest(pageIndex, 100);
        Page resultPage = userRepository.findAll(pageRequest);

        List pages = new ArrayList();
        for (int i = 1; i <= resultPage.getTotalPages(); ++i) {
            pages.add(i);
        }
        request.setAttribute("pages", pages);
        request.setAttribute("suppliers", resultPage.getContent());
        request.setAttribute("currentPage", pageIndex + 1);
        ControllerUtils.fillTemplate(request, Arrays.asList(new String[]{"supplier"}));
        return ViewConstants.VIEW_CLASSIC_TEMPLATE;
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

        ControllerUtils.fillTemplate(request, Arrays.asList(new String[]{"booking"}));
        return ViewConstants.VIEW_CLASSIC_TEMPLATE;
    }

    @RequestMapping(value = "/my-bookable-status.html", method = RequestMethod.GET)
    public String myBookableStatusGet(@RequestParam(required = false) Integer page, HttpServletRequest request, HttpSession session) {
        User loginUser = null;
        Object o = session.getAttribute("user");
        if (o != null && (o instanceof User)) {
            loginUser = (User) o;
        } else {
            return "redirect:/login.html";
        }

        ControllerUtils.fillTemplate(request, Arrays.asList(new String[]{"myBookableStatus"}));
        return ViewConstants.VIEW_CLASSIC_TEMPLATE;
    }
}
