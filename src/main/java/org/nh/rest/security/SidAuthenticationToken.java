package org.nh.rest.security;

import org.springframework.security.authentication.AbstractAuthenticationToken;

public class SidAuthenticationToken extends AbstractAuthenticationToken {

	private static final long serialVersionUID = -4214570157938964709L;
	
	private String sid;

    public SidAuthenticationToken(String sid) {
        super(null);
        this.sid = sid;
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

	public String getSid() {
		return sid;
	}

}
