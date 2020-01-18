package com.system.software.solutions.sts.controller;

import com.system.software.solutions.sts.model.Dashboard;
import com.system.software.solutions.sts.model.User;
import com.system.software.solutions.sts.service.DashboardService;
import com.system.software.solutions.sts.service.UserService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/dashboard")
public class DashboardController {

    @Autowired
    private DashboardService dashboardService;

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/add", method = RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    Map<String, Object> addDashboard(@RequestBody MultiValueMap<String, String> formParams) {
        String type = formParams.get("type").get(0);
        String title = formParams.get("title").get(0);
        String model = formParams.get("model").get(0);
        String options = formParams.get("options").get(0);

        Dashboard dashboard = new Dashboard();
        dashboard.setModel(model);
        dashboard.setOptions(options);
        dashboard.setTitle(title);
        dashboard.setType(type);
        dashboard.setUserID(this.getAuthenticatedUser().getId());

        Integer id;
        if (this.getAuthenticatedUser().getUserDashboards().size() >= 10) {
            id = 0;
        } else {
            id = dashboardService.addDashboard(dashboard);
        }

        Boolean status;
        if (id > 0) {
            status = true;
        } else {
            status = false;
        }

        Map<String, Object> object = new HashMap<String, Object>();
        object.put("id", id);
        object.put("status", status);

        return object;
    }

    @RequestMapping(value = "/delete", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    Map<String, Object> deleteDashboard(@RequestBody MultiValueMap<String, String> formParams) {
        Integer id = Integer.parseInt(formParams.get("id").get(0));
        Boolean status = dashboardService.deleteDashboard(id);
        Map<String, Object> object = new HashMap<String, Object>();
        object.put("status", status);
        return object;
    }

    @RequestMapping(value = "/update", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    Map<String, Object> updateDashboard(@RequestBody MultiValueMap<String, String> formParams) {
        Integer id = Integer.parseInt(formParams.get("id").get(0));
        String text = formParams.get("text").get(0);
        Boolean status = dashboardService.updateDashboardTitle(id, text);
        Map<String, Object> object = new HashMap<String, Object>();
        object.put("status", status);
        return object;
    }

    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> listDashboard() {

        Set<Dashboard> dashboardList = this.getAuthenticatedUser().getUserDashboards();

        Map<String, Object> object = new HashMap<String, Object>();
        object.put("data", dashboardList);

        return object;
    }

    private User getAuthenticatedUser() {
        String userName = null;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            userName = ((UserDetails) principal).getUsername();
        } else {
            userName = principal.toString();
        }

        return userService.findBySSO(userName);
    }
}
