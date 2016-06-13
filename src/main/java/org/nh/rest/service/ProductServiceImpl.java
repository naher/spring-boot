package org.nh.rest.service;

import org.nh.rest.model.ds02.Product;
import org.nh.rest.persistence.relational.ds02.ProductRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(transactionManager = "ds01TransactionManager")
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository repository;

    @Override
    public Product create(String name, double price) {
        Product product = new Product(name, price);
        return repository.save(product);
    }

    @Override
    public Product get(String name) {
        return repository.findByName(name);
    }

}
