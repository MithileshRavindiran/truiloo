package com.example.demo.app;

import com.trulioo.normalizedapi.ApiException;
import com.trulioo.normalizedapi.model.VerifyResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by mravindran on 31/12/19.
 */
@RestController
public class WelcomeController {

    @Autowired
    TestAuthentication testAuthentication;

    @Autowired
    TruliooService truliooService;


    @RequestMapping(value="/home",method = RequestMethod.GET)
    public String hello() throws ApiException {
        //testAuthentication.testAuthentication();
        testAuthentication.testAuthenticationRest();

        return "Hello";
    }


    @GetMapping("/authenticate")
    public String authenticate() throws ApiException {
        return truliooService.authenticate();
    }

    @GetMapping("/countries")
    public List<String> getCountries() throws ApiException {
        return truliooService.getCountries();
    }

    @GetMapping("/verify")
    public VerifyResult verify() throws ApiException {
        return truliooService.verify();
    }

    @GetMapping("/verifyrest")
    public String verifyrest() throws ApiException {
        return truliooService.verifyUsingREST();
    }
}
