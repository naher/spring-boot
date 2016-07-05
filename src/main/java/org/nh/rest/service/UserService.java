package org.nh.rest.service;

import org.nh.rest.model.ds01.User;

public interface UserService {

    User create(String name, String email);

    User save(User user);

    User get(String name);

    User getByEmail(String userEmail);

}
