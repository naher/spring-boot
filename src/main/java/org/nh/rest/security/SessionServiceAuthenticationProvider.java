package org.nh.rest.security;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.rememberme.RememberMeAuthenticationException;
import org.springframework.security.web.authentication.session.SessionAuthenticationException;
import org.springframework.stereotype.Component;

@Component
public class SessionServiceAuthenticationProvider implements AuthenticationProvider {

    protected final Log logger = LogFactory.getLog(getClass());

    @Autowired
    private SessionServiceImpl sessionService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        SessionToken token;
        try {
            if (UsernamePasswordAuthenticationToken.class.equals(authentication.getClass())) {
                token = authenticate((UsernamePasswordAuthenticationToken) authentication);
            } else if (SidAuthenticationToken.class.equals(authentication.getClass())) {
                token = authenticate((SidAuthenticationToken) authentication);
            } else if (SilentAuthenticationToken.class.equals(authentication.getClass())) {
                token = authenticate((SilentAuthenticationToken) authentication);
            } else {
                logger.warn("Invalid authentication object");
                throw new BadCredentialsException("Invalid authentication object");
            }
        } catch (RuntimeException rte) {
            logger.debug(rte.getMessage());
            throw rte;
        }

        logger.info("Authentication complete: " + token);

        authentication = token.getAuthentication();
        return authentication;
    }

    public SessionToken authenticate(UsernamePasswordAuthenticationToken authentication) {
        String email = null;
        String password = null;

        try {
            email = (String) authentication.getPrincipal();
        } catch (RuntimeException r) {
            logger.info("Invalid email on authentication: " + email);
            throw new BadCredentialsException(String.format("Unable to authenticate email %s", email));
        }

        try {
            password = (String) authentication.getCredentials();
        } catch (RuntimeException r) {
            logger.info("Invalid password on authentication with email: " + email);
            throw new BadCredentialsException(String.format("Unable to authenticate email %s", email));
        }

        return sessionService.authenticate(email, password);
    }

    public SessionToken authenticate(SidAuthenticationToken authentication) {
        String sid = (String) authentication.getSid();

        if (sessionService.contains(sid)) {
            return sessionService.retrieve(sid);
        } else {
            logger.info("Invalid session id: " + sid);
            throw new SessionAuthenticationException(String.format("Invalid session id: '%s'", sid));
        }

    }

    public SessionToken authenticate(SilentAuthenticationToken authentication) {
        String sltk = (String) authentication.getSltk();

        try {
            return sessionService.createSession(sltk);
        } catch (RuntimeException r) {
            logger.info("Invalid silent signin token: " + sltk);
            throw new RememberMeAuthenticationException("Invalid silent signin token", r);
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return (UsernamePasswordAuthenticationToken.class.equals(authentication)
                || SidAuthenticationToken.class.equals(authentication) || SilentAuthenticationToken.class
                    .equals(authentication));

    }

}
