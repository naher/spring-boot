package org.nh.rest.service;

import org.nh.rest.model.ds01.User;
import org.nh.rest.persistence.gemfire.UserGemfireRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional("gemfireTransactionManager")
public class UserCacheServiceImpl implements UserCacheService {

    @Autowired
    private UserGemfireRepository repository;

    @Override
    public void store(User user) {
        repository.save(user);
    }

    @Override
    public boolean contains(Long id) {
        return repository.exists(id);
    }

    @Override
    public User get(Long id) {
        return repository.findOne(id);
    }

    @Override
    public void remove(Long id) {
        repository.delete(id);
    }

}
