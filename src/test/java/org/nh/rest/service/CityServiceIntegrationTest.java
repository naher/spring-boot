package org.nh.rest.service;

import java.util.ArrayList;
import java.util.Collection;

import org.junit.Assert;
import org.junit.Test;

import org.nh.rest.IntegrationTest;
import org.nh.rest.model.City;
import org.nh.rest.security.Role.GrantedAuthority;
import org.nh.rest.security.Role;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

public class CityServiceIntegrationTest extends IntegrationTest {

    @Autowired
    private CityService service;

    @Test
    public void testCreateCity() {

        SecurityContext ctx = SecurityContextHolder.createEmptyContext();
        SecurityContextHolder.setContext(ctx);
        Collection<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        authorities.add(new GrantedAuthority(Role.ROLE_ADMIN));
        ctx.setAuthentication(new AnonymousAuthenticationToken("anon", "nymous", authorities));

        String name = "name";
        String country = "country";
        String state = "state";
        String map = "-37.813187, 144.96298";

        City saved = service.create(name, state, country, map);

        City retrieved = service.getCity(name, country);

        Assert.assertNotNull(retrieved.getId());
        Assert.assertEquals(saved.getId(), retrieved.getId());
    }

    @Test(expected = AccessDeniedException.class)
    public void testCreateCityAnonymous() {

        SecurityContext ctx = SecurityContextHolder.createEmptyContext();
        SecurityContextHolder.setContext(ctx);
        Collection<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        authorities.add(new GrantedAuthority(Role.ROLE_ANONYMOUS));
        ctx.setAuthentication(new AnonymousAuthenticationToken("anon", "nymous", authorities));

        String name = "name";
        String country = "country";
        String state = "state";
        String map = "-37.813187, 144.96298";

        service.create(name, state, country, map);
    }
}
