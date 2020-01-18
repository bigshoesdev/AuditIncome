package com.system.software.solutions.sts.controller;

import java.util.List;
import java.util.Locale;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.system.software.solutions.sts.model.User;
import com.system.software.solutions.sts.model.UserProfile;
import com.system.software.solutions.sts.service.UserProfileService;
import com.system.software.solutions.sts.service.UserService;
import org.springframework.validation.Errors;

@Controller
@RequestMapping("/admin")
@SessionAttributes("roles")
public class AdminController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserProfileService userProfileService;

    @Autowired
    private MessageSource messageSource;

    @RequestMapping(value = {"/", "/users"}, method = RequestMethod.GET)
    public String listUsers(ModelMap model) {
        System.out.println("+++++ load message " + messageSource.getMessage("delete.confirm", new Object[]{}, Locale.getDefault()));

        List<User> users = userService.findAllUsers();
        model.addAttribute("users", users);
        model.addAttribute("loggedinuser", getPrincipal());
        return "usersList";
    }

    /**
     * This method will list all existing users.
     */
    @RequestMapping(value = {"/roles"}, method = RequestMethod.GET)
    public String listRoles(ModelMap model) {
        List<UserProfile> roles = userProfileService.findAll();
        model.addAttribute("roles", roles);
        model.addAttribute("loggedinuser", getPrincipal());
        return "rolesList";
    }

    /**
     * This method will provide the medium to update an existing user.
     */
    @RequestMapping(value = {"/edit-role-{roleId}"}, method = RequestMethod.GET)
    public String editRole(@PathVariable int roleId, ModelMap model) {
        UserProfile role = userProfileService.findById(roleId);
        model.addAttribute("role", role);
        model.addAttribute("edit", true);
        model.addAttribute("loggedinuser", getPrincipal());
        return "addRole";
    }

    /**
     * This method will be called on form submission, handling POST request for
     * saving role in database. It also validates the user input
     */
    @RequestMapping(value = {"/edit-role-{roleId}"}, method = RequestMethod.POST)
    public String editRole(@Valid UserProfile role, BindingResult result,
            ModelMap model, @PathVariable int roleId) {

        if (result.hasErrors()) {
            return "addRole";
        }

        /*
         * Preferred way to achieve uniqueness of field [sso] should be implementing custom @Unique annotation 
         * and applying it on field [sso] of Model class [User].
         * 
         * Below mentioned peace of code [if block] is to demonstrate that you can fill custom errors outside the validation
         * framework as well while still using internationalized messages.
         * 
         */
        /*  if(!userService.isUserSSOUnique(user.getId(), user.getSsoId())){
         FieldError ssoError =new FieldError("user","ssoId",messageSource.getMessage("non.unique.ssoId", new String[]{user.getSsoId()}, Locale.getDefault()));
         result.addError(ssoError);
         return "registration";
         }*/
        userProfileService.save(role);

        model.addAttribute("success", "Role " + role.getName() + " " + role.getDescription() + " registered successfully");
        model.addAttribute("loggedinuser", getPrincipal());
        model.addAttribute("type", "roles");
        //return "success";
        return "registrationsuccess";
    }

    /**
     * This method will provide the medium to add a new Role.
     */
    @RequestMapping(value = {"/newRole"}, method = RequestMethod.GET)
    public String newRole(ModelMap model) {
        UserProfile role = new UserProfile();
        model.addAttribute("role", role);
        model.addAttribute("edit", false);
        model.addAttribute("loggedinuser", getPrincipal());
        return "addRole";
    }

    /**
     * This method will delete an user by it's SSOID value.
     */
    @RequestMapping(value = {"/delete-role-{id}"}, method = RequestMethod.GET)
    public String deleteRole(@PathVariable Integer id) {
        userProfileService.delete(id);
        return "redirect:/admin/roles";
    }

    /**
     * This method will be called on form submission, handling POST request for
     * saving role in database. It also validates the user input
     */
    @RequestMapping(value = {"/newRole"}, method = RequestMethod.POST)
    public String saveRole(@Valid UserProfile role, BindingResult result,
            ModelMap model) {

        if (result.hasErrors()) {
            return "addRole";
        }

        /*
         * Preferred way to achieve uniqueness of field [sso] should be implementing custom @Unique annotation 
         * and applying it on field [sso] of Model class [User].
         * 
         * Below mentioned peace of code [if block] is to demonstrate that you can fill custom errors outside the validation
         * framework as well while still using internationalized messages.
         * 
         */
        /*  if(!userService.isUserSSOUnique(user.getId(), user.getSsoId())){
         FieldError ssoError =new FieldError("user","ssoId",messageSource.getMessage("non.unique.ssoId", new String[]{user.getSsoId()}, Locale.getDefault()));
         result.addError(ssoError);
         return "registration";
         }*/
        userProfileService.save(role);

        model.addAttribute("success", "Role " + role.getName() + " " + role.getDescription() + " registered successfully");
        model.addAttribute("loggedinuser", getPrincipal());
        model.addAttribute("type", "roles");
        //return "success";
        return "registrationsuccess";
    }

    /**
     * This method will provide the medium to add a new user.
     */
    @RequestMapping(value = {"/newuser"}, method = RequestMethod.GET)
    public String newUser(ModelMap model) {
        User user = new User();
        model.addAttribute("user", user);
        model.addAttribute("edit", false);
        model.addAttribute("loggedinuser", getPrincipal());
        return "registration";
    }

    /**
     * This method will be called on form submission, handling POST request for
     * saving user in database. It also validates the user input
     */
    @RequestMapping(value = {"/newuser"}, method = RequestMethod.POST)
    public String saveUser(@Valid User user, BindingResult result,
            ModelMap model) {
        if (result.hasErrors()) {
            return "registration";
        }

        /*
         * Preferred way to achieve uniqueness of field [sso] should be implementing custom @Unique annotation 
         * and applying it on field [sso] of Model class [User].
         * 
         * Below mentioned peace of code [if block] is to demonstrate that you can fill custom errors outside the validation
         * framework as well while still using internationalized messages.
         * 
         */
        if (!userService.isUserSSOUnique(user.getId(), user.getSsoId())) {
            FieldError ssoError = new FieldError("user", "ssoId", messageSource.getMessage("There is an user with same sso ID", new String[]{user.getSsoId()}, Locale.getDefault()));
            model.addAttribute("edit", false);
            result.addError(ssoError);
            return "registration";
        }

        userService.saveUser(user);

        model.addAttribute("success", "User " + user.getFirstName() + " " + user.getLastName() + " registered successfully");
        model.addAttribute("loggedinuser", getPrincipal());
        model.addAttribute("type", "users");
        //return "success";
        return "registrationsuccess";
    }

    /**
     * This method will provide the medium to update an existing user.
     */
    @RequestMapping(value = {"/edit-user-{ssoId}"}, method = RequestMethod.GET)
    public String editUser(@PathVariable String ssoId, ModelMap model) {
        User user = userService.findBySSO(ssoId);
        model.addAttribute("user", user);
        model.addAttribute("edit", true);
        model.addAttribute("loggedinuser", getPrincipal());
        return "registration";
    }

    /**
     * This method will be called on form submission, handling POST request for
     * updating user in database. It also validates the user input
     */
    @RequestMapping(value = {"/edit-user-{ssoId}"}, method = RequestMethod.POST)
    public String updateUser(@Valid User user, BindingResult result,
            ModelMap model, @PathVariable String ssoId) {

        if (result.hasErrors()) {
            return "registration";
        }

        /*//Uncomment below 'if block' if you WANT TO ALLOW UPDATING SSO_ID in UI which is a unique key to a User.
         if(!userService.isUserSSOUnique(user.getId(), user.getSsoId())){
         FieldError ssoError =new FieldError("user","ssoId",messageSource.getMessage("non.unique.ssoId", new String[]{user.getSsoId()}, Locale.getDefault()));
         result.addError(ssoError);
         return "registration";
         }*/
        userService.updateUser(user);

        model.addAttribute("success", "User " + user.getFirstName() + " " + user.getLastName() + " updated successfully");
        model.addAttribute("loggedinuser", getPrincipal());
        model.addAttribute("type", "users");
        return "registrationsuccess";
    }

    /**
     * This method will delete an user by it's SSOID value.
     */
    @RequestMapping(value = {"/delete-user-{ssoId}"}, method = RequestMethod.GET)
    public String deleteUser(@PathVariable String ssoId) {
        userService.deleteUserBySSO(ssoId);
        return "redirect:/admin/";
    }

    /**
     * This method will provide UserProfile list to views
     */
    @ModelAttribute("roles")
    public List<UserProfile> initializeProfiles() {
        return userProfileService.findAll();
    }

    /**
     * This method returns the principal[user-name] of logged-in user.
     */
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

}
