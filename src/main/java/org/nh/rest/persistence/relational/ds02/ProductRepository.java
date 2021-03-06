package org.nh.rest.persistence.relational.ds02;

import org.nh.rest.model.ds02.Product;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {

    Product findByName(String name);

}
