package org.nh.rest.service;

import org.junit.Test;
import org.nh.rest.IntegrationTest;
import org.nh.rest.model.ds01.User;
import org.nh.rest.model.ds02.Product;
import org.springframework.beans.factory.annotation.Autowired;

public class InterestServiceIntegrationTest extends IntegrationTest {

    @Autowired
    private InterestService interestService;

    @Autowired
    private UserService userService;

    @Autowired
    private ProductService productService;

    @Test
    public void basic() {
    	// TODO complete JTA transaction manager configuration 
//        double pprice = 10.01f;
//        String pname = "thename01";
//        Product psaved = productService.create(pname, pprice);
//
//        String uemail = "the@email.com";
//        String uname = "thename01";
//        User usaved = userService.create(uname, uemail);
//
//        interestService.addInterest(usaved, psaved);
    }
}
