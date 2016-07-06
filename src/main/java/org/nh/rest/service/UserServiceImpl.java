package org.nh.rest.service;

import java.util.List;

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
    public User save(User user) {
        return repository.save(user);
    }

    @Override
    public User get(String name) {
        return repository.findByName(name);
    }

    @Override
    public User getByEmail(String userEmail) {
        return repository.findByEmail(userEmail);
    }

    @Override
    public List<User> findAll(List<Long> ids) {
        return repository.findAll(ids);
    }

}
