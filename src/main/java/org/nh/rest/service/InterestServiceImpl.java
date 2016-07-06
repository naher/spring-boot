package org.nh.rest.service;

import java.util.List;

import org.nh.rest.model.ds01.User;
import org.nh.rest.model.ds02.Product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * distributed transactions sample service
 * 
 * See also:
 * 
 * JTA:
 * http://docs.spring.io/spring/docs/current/spring-framework-reference/html/
 * transaction.html
 * http://www.thedevpiece.com/configuring-multiple-datasources-using-springboot-
 * and-atomikos/
 * https://spring.io/blog/2011/08/15/configuring-spring-and-jta-without-full-
 * java-ee/
 * http://stackoverflow.com/questions/25247664/atomikos-xaresource-raised-0-
 * unknown
 * 
 * non-JTA: org.springframework.data.transaction.ChainedTransactionManager
 * http://gharshangupta.blogspot.com.ar/2015/03/spring-distributed-transactions-
 * using_2.html
 * 
 * @author hernan.leoni
 *
 */

@Service
@Transactional("distributedTransactionManager")
public class InterestServiceImpl implements InterestService {

    @Autowired
    private UserService userService;

    @Autowired
    private ProductService productService;

    @Override
    public void addInterest(User user, Product product) {
        user.getProductsOfInterest().add(product.getId());
        product.getInterestedCustomers().add(user.getId());

        userService.save(user);
        productService.save(product);
    }

    @Override
    public void addInterest(String userEmail, String productName) {

        Product p = productService.get(productName);
        if (p == null) {
            p = productService.create(productName, 0);
        }

        User u = userService.getByEmail(userEmail);
        if (u == null) {
            u = userService.create(userEmail, userEmail);
        }

        if (u.getProductsOfInterest().size() == 3) {
            throw new DataIntegrityViolationException("Integrity: interest list is full");
        }
        if (p.getInterestedCustomers().size() == 3) {
            throw new DataIntegrityViolationException("Integrity: interest list is full");
        }
        addInterest(u, p);
    }

    @Override
    public List<Product> getInterest(User user) {
        return productService.findAll(user.getProductsOfInterest());
    }

    @Override
    public List<User> getInterested(Product product) {
        return userService.findAll(product.getInterestedCustomers());
    }
}
