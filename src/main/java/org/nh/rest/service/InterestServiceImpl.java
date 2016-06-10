package org.nh.rest.service;

import org.nh.rest.model.ds01.User;
import org.nh.rest.model.ds02.Product;
import org.nh.rest.persistence.relational.ds01.UserRepository;
import org.nh.rest.persistence.relational.ds02.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class InterestServiceImpl implements InterestService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    @Override
    public void addInterest(User user, Product product) {
        user.getProductsOfInterest().add(product.getId());
        product.getInterestedCustomers().add(user.getId());

        userRepository.save(user);
        productRepository.save(product);
    }

}
