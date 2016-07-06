package org.nh.rest.service;

import java.util.List;

import org.nh.rest.model.ds01.User;
import org.nh.rest.model.ds02.Product;

public interface InterestService {

    void addInterest(String userEmail, String productName);

    void addInterest(User user, Product product);

    List<Product> getInterest(User user);

    List<User> getInterested(Product product);

}
