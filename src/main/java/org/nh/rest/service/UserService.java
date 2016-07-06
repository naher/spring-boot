package org.nh.rest.service;

import java.util.List;

import org.nh.rest.model.ds01.User;

public interface UserService {

    User create(String name, String email);

    User save(User user);

    User get(String name);

    User getByEmail(String userEmail);

    List<User> findAll(List<Long> ids);

}
