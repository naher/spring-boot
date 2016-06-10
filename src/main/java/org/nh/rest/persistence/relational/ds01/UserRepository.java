package org.nh.rest.persistence.relational.ds01;

import org.nh.rest.model.ds01.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {

    User findByName(String name);

}