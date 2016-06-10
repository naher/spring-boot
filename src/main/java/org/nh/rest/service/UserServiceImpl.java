package org.nh.rest.service;

import org.nh.rest.model.ds01.User;
import org.nh.rest.persistence.relational.ds01.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(transactionManager = "ds02TransactionManager")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository repository;

    @Override
    public User create(String name, String email) {
        User user = new User(name, email);
        return repository.save(user);
    }

    @Override
    public User get(String name) {
        return repository.findByName(name);
    }

}
