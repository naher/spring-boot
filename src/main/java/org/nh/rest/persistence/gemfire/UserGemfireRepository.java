package org.nh.rest.persistence.gemfire;

import org.nh.rest.model.ds01.User;
import org.springframework.data.gemfire.repository.GemfireRepository;

public interface UserGemfireRepository extends GemfireRepository<User, Long> {

    // User findByName(String name);
    //
    // User findByEmail(String email);
}
