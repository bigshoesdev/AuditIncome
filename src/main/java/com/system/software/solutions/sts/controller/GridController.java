package com.system.software.solutions.sts.controller;

import com.system.software.solutions.sts.model.GRID;
import com.system.software.solutions.sts.model.User;
import com.system.software.solutions.sts.service.GridService;
import com.system.software.solutions.sts.service.UserService;
import java.util.HashMap;
import java.util.Map;
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
@RequestMapping("/grid")
public class GridController {

    @Autowired
    private GridService gridService;

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/add", method = RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    Map<String, Object> addGrid(@RequestBody MultiValueMap<String, String> formParams) {
        String model = formParams.get("model").get(0);
        String options = formParams.get("options").get(0);

        GRID grid = new GRID();
        grid.setModel(model);
        grid.setOptions(options);
        grid.setUserID(this.getAuthenticatedUser().getId());

        Integer id = gridService.addGrid(grid);

        Boolean status;
        if (id > 0) {
            status = true;
        } else {
            status = false;
        }

        Map<String, Object> object = new HashMap<String, Object>();
        object.put("status", status);

        return object;
    }

    @RequestMapping(value = "/get", method = RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    Map<String, Object> getGrid(@RequestBody MultiValueMap<String, String> formParams) {
        String model = formParams.get("model").get(0);
        int userID = this.getAuthenticatedUser().getId();

        GRID grid = gridService.getGrid(userID, model);

        Boolean status;

        if (grid != null) {
            status = true;
        } else {
            status = false;
        }

        Map<String, Object> object = new HashMap<String, Object>();
        object.put("status", status);
        object.put("data", grid);

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
