package org.nh.rest.controllers;

import java.util.List;

import org.junit.Assert;
import org.nh.rest.IntegrationAbstractTest;
import org.nh.rest.model.ds01.User;
import org.nh.rest.model.ds02.Product;
import org.nh.rest.service.InterestService;
import org.nh.rest.service.ProductService;
import org.nh.rest.service.UserService;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * This abstraction allows us to have exactly the same test for both JTA and
 * non-JTA configuration
 * 
 * @author hernan.leoni
 *
 */
public class InterestControllerAbstractIntegrationTest extends ControllerAbstractIntegrationTest {

    private InterestService interestService;
    private UserService userService;
    private ProductService productService;

    public InterestControllerAbstractIntegrationTest(IntegrationAbstractTest concreteTest,
            InterestService interestService, UserService userService, ProductService productService) {
        super(concreteTest);
        this.interestService = interestService;
        this.userService = userService;
        this.productService = productService;
    }

    public void testDistributedExceptions() {

        String uemail = "theUserEmail_InterestControllerIntegrationTest";
        String pname01 = "theProductName01_InterestControllerIntegrationTest";
        String pname02 = "theProductName02_InterestControllerIntegrationTest";
        String pname03 = "theProductName03_InterestControllerIntegrationTest";
        String pname04 = "theProductName04_InterestControllerIntegrationTest";

        HttpHeaders headers = getHeaders();
        ResponseEntity<Void> entity = new TestRestTemplate().exchange(getURL("/interest/e/" + uemail + "/p/" + pname01),
                HttpMethod.POST, new HttpEntity<Void>(headers), Void.class);
        Assert.assertEquals(HttpStatus.OK, entity.getStatusCode());

        entity = new TestRestTemplate().exchange(getURL("/interest/e/" + uemail + "/p/" + pname02), HttpMethod.POST,
                new HttpEntity<Void>(headers), Void.class);
        Assert.assertEquals(HttpStatus.OK, entity.getStatusCode());

        entity = new TestRestTemplate().exchange(getURL("/interest/e/" + uemail + "/p/" + pname03), HttpMethod.POST,
                new HttpEntity<Void>(headers), Void.class);
        Assert.assertEquals(HttpStatus.OK, entity.getStatusCode());

        entity = new TestRestTemplate().exchange(getURL("/interest/e/" + uemail + "/p/" + pname04), HttpMethod.POST,
                new HttpEntity<Void>(headers), Void.class);
        Assert.assertEquals(HttpStatus.CONFLICT, entity.getStatusCode());

        Product psaved01 = productService.get(pname01);
        Assert.assertNotNull(psaved01);
        Product psaved02 = productService.get(pname02);
        Assert.assertNotNull(psaved02);
        Product psaved03 = productService.get(pname03);
        Assert.assertNotNull(psaved03);

        // product 4 is not present in the database, which means the transaction
        // was roll backed properly
        Product psaved04 = productService.get(pname04);
        Assert.assertNull(psaved04);
        User usaved = userService.getByEmail(uemail);
        Assert.assertNotNull(usaved);

        List<User> uu = interestService.getInterested(psaved01);
        Assert.assertFalse(uu.isEmpty());
        Assert.assertEquals(1, uu.size());
        Assert.assertEquals(usaved.getId(), uu.get(0).getId());

        List<Product> pp = interestService.getInterest(usaved);
        Assert.assertFalse(pp.isEmpty());
        Assert.assertEquals(3, pp.size());
        Assert.assertEquals(psaved01.getId(), pp.get(0).getId());
    }

}
