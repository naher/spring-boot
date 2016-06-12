package org.nh.rest.security;

import org.springframework.security.core.Authentication;

public class SessionToken {

    private String sid;

    private String email;

    private Authentication authentication;

    public SessionToken(String sid, String email,
			AuthenticatedAuthenticationToken authentication) {
		this.sid = sid;
		this.email = email;
		this.authentication = authentication;
	}

	public String getSid() {
		return sid;
	}

	public String getEmail() {
		return email;
	}

	public Authentication getAuthentication() {
		return authentication;
	}

	@Override
	public String toString() {
		return "SessionToken [sid=" + sid + ", email=" + email
				+ ", authentication=" + authentication + "]";
	}

	@Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((sid == null) ? 0 : sid.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        SessionToken other = (SessionToken) obj;
        if (sid == null) {
            if (other.sid != null)
                return false;
        } else if (!sid.equals(other.sid))
            return false;
        return true;
    }

}
