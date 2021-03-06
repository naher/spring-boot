package org.nh.rest.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.nh.rest.Constants;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

@Service
public class SessionServiceImpl implements SessionService {

    protected final Log logger = LogFactory.getLog(getClass());

    // using hashmap as sample, use a distributed cache system for productive
    // applications
    private Map<String, SessionToken> tokenCache = new HashMap<String, SessionToken>();

    @Override
    public SessionToken authenticate(String email, String password) {
        // verify if it is an user which is valid, existent, enabled, etc
        if ((email == null) || (!email.endsWith("@mail.com"))) {
            logger.warn(String.format("Unable to retrieve user with email %s", email));
            throw new BadCredentialsException(String.format("Unable to authenticate email %s", email));
        }

        // verify password
        try {
            if ((Constants.ADMIN_MAIL_COM.equals(email) && (!Constants.ADMINPASS.equals(password)))
                    || (Constants.USER_MAIL_COM.equals(email) && (!Constants.USERPASS.equals(password)))) {
                logger.info("Unable to authenticate email: " + email);
                throw new BadCredentialsException(String.format("Unable to authenticate email %s", email));
            }
        } catch (RuntimeException r) {
            logger.info("Unable to authenticate email: " + email);
            throw new BadCredentialsException(String.format("Unable to authenticate email %s", email));
        }

        // add authorities, (hard-coded as sample)
        Collection<GrantedAuthority> userAuthorities = new ArrayList<GrantedAuthority>();
        if (Constants.ADMIN_MAIL_COM.equals(email)) {
            userAuthorities.add(new Role.GrantedAuthority(Role.ROLE_ADMIN));
        } else if (Constants.USER_MAIL_COM.equals(email)) {
            userAuthorities.add(new Role.GrantedAuthority(Role.ROLE_USER));
        }

        // generate session token
        AuthenticatedAuthenticationToken authentication = new AuthenticatedAuthenticationToken(email, userAuthorities);
        SessionToken token = new SessionToken(generateNewToken(), email, authentication);
        authentication.setSessionToken(token);

        return token;
    }

    @Override
    public SessionToken createSession(String sltk) {
        // validate encrypted token

        if ("adminEncryptedSilentToken".equals(sltk))
            return createSessionWithHash(Constants.ADMIN_MAIL_COM, Constants.ADMINPASS);
        if ("userEncryptedSilentToken".equals(sltk))
            return createSessionWithHash(Constants.USER_MAIL_COM, Constants.USERPASS);

        throw new BadCredentialsException(String.format("Unable to authenticate with token"));
    }

    @Override
    public SessionToken createSessionWithHash(String email, String passhash) {
        SessionToken token = authenticate(email, passhash);
        store(token.getSid(), token);
        return token;
    }

    @Override
    public boolean contains(String sid) {
        return tokenCache.get(sid) != null;
    }

    @Override
    public SessionToken retrieve(String sid) {
        return tokenCache.get(sid);
    }

    protected void store(String sid, SessionToken token) {
        tokenCache.put(sid, token);
    }

    @Override
    public void remove(String sid) {
        tokenCache.put(sid, null);
    }

    public static String generateNewToken() {
        return UUID.randomUUID().toString();
    }
}
