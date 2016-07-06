package org.nh.rest.service;

import java.util.List;

import org.nh.rest.model.ds02.Product;

public interface ProductService {

    Product create(String name, double price);

    Product save(Product product);

    Product get(String name);

    List<Product> findAll(List<Long> ids);
}
