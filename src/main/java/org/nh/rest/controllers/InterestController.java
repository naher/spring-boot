package org.nh.rest.controllers;

import org.nh.rest.service.InterestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/interest")
public class InterestController extends AbstractController {

    @Autowired
    public InterestService intrestService;

    @RequestMapping(value = "/e/{userEmail}/p/{productName}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody void addInterest(@PathVariable("userEmail") String userEmail,
            @PathVariable("productName") String productName) {
        intrestService.addInterest(userEmail, productName);
    }
}
