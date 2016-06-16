package org.nh.rest.security;

import java.io.Serializable;

import org.apache.log4j.Logger;
import org.nh.rest.model.City;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
public class PermissionEvaluator implements org.springframework.security.access.PermissionEvaluator {

    protected final Logger logger = Logger.getLogger(PermissionEvaluator.class);

    @Override
    public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {

        // verifying authentication
        if (authentication == null) {
            logger.info("Null Authentication object received.");
            return false;
        }
        if (authentication instanceof AnonymousAuthenticationToken) {
            logger.info("Access denied to Anonymous user.");
            return false;
        }
        if (targetDomainObject == null) {
            logger.warn("Null targetDomainObject object, allowing access.");
            return true;
        }

        // verifying role
        Role role = null;
        if (permission == null) {
            throw new IllegalArgumentException(String.format("Invalid null permission object"));
        } else {
            try {
                if (permission instanceof String) {
                    role = Role.valueOf((String) permission);
                } else if (permission instanceof Role) {
                    role = (Role) permission;
                } else {
                    throw new IllegalArgumentException(String.format("Invalid permission object: %s", permission));
                }

                // verifying authorities
                if (!role.equals(Role.ROLE_ANONYMOUS)
                        && !authentication.getAuthorities().contains(role.getGrantedAuthority())) {
                    logger.info("Role not found on authentication: " + role + ", " + authentication);
                    return false;
                }
            } catch (IllegalArgumentException iae) {
                logger.info("not role based permission: " + iae.getMessage());
            }
        }

        // getting user and verifying user permissions over the target domain
        // object
        // String email = (String) authentication.getPrincipal();
        // User user = userRepository.findByEmail(email);

        // TODO note this is just a hardcoded example, in a real productive
        // implementation this must me designed and implemented properly
        if (targetDomainObject instanceof City) {
            City city = (City) targetDomainObject;
            if (city.getName().equals("Tandil"))
                return true;
            else
                return false;
        }

        throw new IllegalArgumentException(String.format("Invalid targetDomainObject object: %s", targetDomainObject));
    }

    @Override
    public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType,
            Object permission) {
        throw new RuntimeException("Id and Class permissions are not supported by this application");
    }

}
