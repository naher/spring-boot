package org.nh.rest.service;

import org.nh.rest.model.ds01.User;
import org.nh.rest.model.ds02.Product;

public interface InterestService {

    void addInterest(User user, Product product);
}
