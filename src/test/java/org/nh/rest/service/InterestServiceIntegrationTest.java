package org.nh.rest.service;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import org.nh.rest.IntegrationTest;
import org.nh.rest.model.ds01.User;
import org.nh.rest.model.ds02.Product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.transaction.TestTransaction;

public class InterestServiceIntegrationTest extends IntegrationTest {

    @Autowired
    private InterestService interestService;

    @Autowired
    private UserService userService;

    @Autowired
    private ProductService productService;

    @Test
    public void basic() {

        double pprice = 10.01f;
        String pname = "thename01";
        Product psaved = productService.create(pname, pprice);

        String uemail = "the@email.com";
        String uname = "thename01";
        User usaved = userService.create(uname, uemail);

        Assert.assertTrue(usaved.getProductsOfInterest().isEmpty());
        Assert.assertTrue(psaved.getInterestedCustomers().isEmpty());

        interestService.addInterest(usaved, psaved);

        Assert.assertFalse(usaved.getProductsOfInterest().isEmpty());
        Assert.assertFalse(psaved.getInterestedCustomers().isEmpty());

        List<User> uu = interestService.getInterested(psaved);
        Assert.assertFalse(uu.isEmpty());
        Assert.assertEquals(1, uu.size());
        Assert.assertEquals(usaved.getId(), uu.get(0).getId());

        List<Product> pp = interestService.getInterest(usaved);
        Assert.assertFalse(pp.isEmpty());
        Assert.assertEquals(1, pp.size());
        Assert.assertEquals(psaved.getId(), pp.get(0).getId());
    }

    // transaction manager tests

    @Test(expected = DataIntegrityViolationException.class)
    public void ds01Exception() {
        String uemail = "the@email.com";
        String uname = "theUserName01";
        User usaved = userService.create(uname, uemail);

        Assert.assertNotNull(usaved);

        usaved = userService.create(uname, uemail);
    }

    @Test(expected = Exception.class)
    public void ds02Exception() {
        double pprice = 10.01f;
        String pname = "theProductName01";
        Product psaved = productService.create(pname, pprice);

        Assert.assertNotNull(psaved);

        psaved = productService.create(pname, pprice);
    }

    @Test
    public void distributedTransactionsOk() {
        String uemail = "theUser@email";
        String pname = "theProductName01";
        interestService.addInterest(uemail, pname);

        Product psaved = productService.get(pname);
        Assert.assertNotNull(psaved);
        User usaved = userService.getByEmail(uemail);
        Assert.assertNotNull(usaved);

        List<User> uu = interestService.getInterested(psaved);
        Assert.assertFalse(uu.isEmpty());
        Assert.assertEquals(1, uu.size());
        Assert.assertEquals(usaved.getId(), uu.get(0).getId());

        List<Product> pp = interestService.getInterest(usaved);
        Assert.assertFalse(pp.isEmpty());
        Assert.assertEquals(1, pp.size());
        Assert.assertEquals(psaved.getId(), pp.get(0).getId());
    }

    @Test
    public void distributedTransactionsOk2Products() {
        String uemail = "theUser@email";
        String pname01 = "theProductName01";
        String pname02 = "theProductName02";
        interestService.addInterest(uemail, pname01);
        interestService.addInterest(uemail, pname02);

        Product psaved01 = productService.get(pname01);
        Assert.assertNotNull(psaved01);
        Product psaved02 = productService.get(pname02);
        Assert.assertNotNull(psaved02);
        User usaved = userService.getByEmail(uemail);
        Assert.assertNotNull(usaved);

        List<User> uu = interestService.getInterested(psaved01);
        Assert.assertFalse(uu.isEmpty());
        Assert.assertEquals(1, uu.size());
        Assert.assertEquals(usaved.getId(), uu.get(0).getId());

        List<Product> pp = interestService.getInterest(usaved);
        Assert.assertFalse(pp.isEmpty());
        Assert.assertEquals(2, pp.size());
        Assert.assertEquals(psaved01.getId(), pp.get(0).getId());
        Assert.assertEquals(psaved02.getId(), pp.get(1).getId());
    }

    @Test
    public void distributedTransactionsOkRepeatedProducts() {
        String uemail = "theUser@email";
        String pname01 = "theProductName01";
        String pname02 = pname01;
        interestService.addInterest(uemail, pname01);
        interestService.addInterest(uemail, pname02);

        Product psaved01 = productService.get(pname01);
        Assert.assertNotNull(psaved01);
        Product psaved02 = productService.get(pname02);
        Assert.assertNotNull(psaved02);
        User usaved = userService.getByEmail(uemail);
        Assert.assertNotNull(usaved);

        List<User> uu = interestService.getInterested(psaved01);
        Assert.assertFalse(uu.isEmpty());
        Assert.assertEquals(1, uu.size());
        Assert.assertEquals(usaved.getId(), uu.get(0).getId());

        List<Product> pp = interestService.getInterest(usaved);
        Assert.assertFalse(pp.isEmpty());
        Assert.assertEquals(1, pp.size());
        Assert.assertEquals(psaved01.getId(), pp.get(0).getId());
    }

    @Test
    @DirtiesContext
    public void distributedTransactionsNOkMoreProductsListFull() {
        String uemail = "theUser@email";
        String pname01 = "theProductName01";
        String pname02 = "theProductName02";
        String pname03 = "theProductName03";
        String pname04 = "theProductName04";
        interestService.addInterest(uemail, pname01);

        TestTransaction.flagForCommit();
        TestTransaction.end();
        TestTransaction.start();

        interestService.addInterest(uemail, pname02);

        TestTransaction.flagForCommit();
        TestTransaction.end();
        TestTransaction.start();

        interestService.addInterest(uemail, pname03);

        TestTransaction.flagForCommit();
        TestTransaction.end();
        TestTransaction.start();

        try {
            interestService.addInterest(uemail, pname04);
            Assert.fail("previous addInterest is expected to fail");
        } catch (RuntimeException e) {
            Assert.assertEquals("Integrity: interest list is full", e.getMessage());
        }
        TestTransaction.end();

        Product psaved01 = productService.get(pname01);
        Assert.assertNotNull(psaved01);
        Product psaved02 = productService.get(pname02);
        Assert.assertNotNull(psaved02);
        Product psaved03 = productService.get(pname03);
        Assert.assertNotNull(psaved03);
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
