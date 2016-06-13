package org.nh.rest.security;

import org.springframework.security.authentication.AbstractAuthenticationToken;

public class SilentAuthenticationToken extends AbstractAuthenticationToken {

    private static final long serialVersionUID = -3637724108844945125L;

    private String sltk;

    public SilentAuthenticationToken(String sltk) {
        super(null);
        this.sltk = sltk;
        setAuthenticated(false);
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return null;
    }

    public String getSltk() {
        return sltk;
    }

}
