package com.system.software.solutions.sts.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.system.software.solutions.sts.model.UserProfile;
import com.system.software.solutions.sts.service.UserProfileService;
import com.system.software.solutions.sts.service.UserService;

@Controller
@RequestMapping
public class IndexController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserProfileService userProfileService;

    @RequestMapping(method = RequestMethod.GET)
    public String getIndexPage(ModelMap model, HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        session.setAttribute("edit", canEdit());
        return "grid";
    }

    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public String mainGrid(ModelMap model, HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        model.addAttribute("loggedinuser", getPrincipal());
        session.setAttribute("edit", canEdit());
        return "main";
    }

    @RequestMapping(value = "/rma", method = RequestMethod.GET)
    public String rmaGrid(ModelMap model, HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        model.addAttribute("loggedinuser", getPrincipal());
        session.setAttribute("edit", canEdit());
        return "rma";
    }

    @RequestMapping(value = "/lcd", method = RequestMethod.GET)
    public String lcdGrid(ModelMap model, HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        model.addAttribute("loggedinuser", getPrincipal());
        session.setAttribute("edit", canEdit());
        return "lcd";
    }

    @RequestMapping(value = "/dailyaudited", method = RequestMethod.GET)
    public String dailyauditedGrid(ModelMap model, HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        model.addAttribute("loggedinuser", getPrincipal());
        session.setAttribute("edit", canEdit());
        return "dailyaudited";
    }

    @RequestMapping(value = "/dashboard", method = RequestMethod.GET)
    public String dashboard(ModelMap model, HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        model.addAttribute("loggedinuser", getPrincipal());
        session.setAttribute("edit", canEdit());
        return "dashboard";
    }

    @RequestMapping(value = "/report", method = RequestMethod.GET)
    public String reprot(ModelMap model, HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        model.addAttribute("loggedinuser", getPrincipal());
        session.setAttribute("edit", canEdit());
        return "report";
    }

    private String getPrincipal() {
        String userName = null;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UserDetails) {
            userName = ((UserDetails) principal).getUsername();
        } else {
            userName = principal.toString();
        }
        return userName;
    }

    private boolean canEdit() {
        boolean edit = false;

        UserProfile profile = new UserProfile();

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            for (GrantedAuthority rol : ((UserDetails) principal).getAuthorities()) {

                profile = userProfileService.findByName(rol.getAuthority().replace("ROLE_", ""));
                if (profile.isEditable()) {
                    edit = true;
                    break;
                }

                System.out.println("role: " + rol.getAuthority());
            }
        }

        return edit;
    }
}
