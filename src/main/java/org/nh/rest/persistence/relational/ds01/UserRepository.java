package org.nh.rest.persistence.relational.ds01;

import org.nh.rest.model.ds01.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByName(String name);

    User findByEmail(String userEmail);

}
