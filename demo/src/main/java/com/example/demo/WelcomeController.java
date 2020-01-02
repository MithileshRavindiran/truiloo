package com.example.demo;

import com.trulioo.normalizedapi.ApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by mravindran on 31/12/19.
 */
@Controller
public class WelcomeController {

    @Autowired
    TestAuthentication testAuthentication;


    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public @ResponseBody  String hello() throws ApiException {
        testAuthentication.testAuthentication();
        return "Hello";
    }
}
