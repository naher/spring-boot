package org.nh.rest.service;

import org.nh.rest.model.ds01.User;

public interface UserCacheService {

    void store(User user);

    boolean contains(Long id);

    User get(Long id);

    void remove(Long id);
}
