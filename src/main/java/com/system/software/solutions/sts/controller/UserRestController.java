package com.system.software.solutions.sts.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.system.software.solutions.sts.model.Test;
import com.system.software.solutions.sts.service.TestService;

@RestController
@RequestMapping("/json")
public class UserRestController {
    @Autowired
    TestService testService;

    //-------------------Retrieve All Users--------------------------------------------------------
    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public ResponseEntity<List<Test>> listAllUsers(HttpServletRequest request) {

        String col = request.getParameter("col");
        String order = request.getParameter("order");
        String term = request.getParameter("term");
        int pageNumber = Integer.parseInt(request.getParameter("page"));
        int pageSize = Integer.parseInt(request.getParameter("pageSize"));

        System.out.println(order);
        System.out.println("col " + col);
        System.out.println("term " + term);

        List<Test> users = testService.find(term, col, pageSize, pageNumber);
        Test test = new Test();
        test.setName("marking");
        test.setGender("male");
        test.setCompany("att");
        //testService.saveTest(test);
        System.out.println("users++++ " + users.size() + order);
        if (users.isEmpty()) {
            return new ResponseEntity<List<Test>>(HttpStatus.NO_CONTENT);//You many decide to return HttpStatus.NOT_FOUND
        }
        return new ResponseEntity<List<Test>>(users, HttpStatus.OK);
    }
}
