package org.nh.rest.service;

import org.nh.rest.model.ds02.Product;

public interface ProductService {

    Product create(String name, double price);

    Product get(String name);
}
