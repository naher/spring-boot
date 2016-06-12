package org.nh.rest.security;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;

public class AuthenticatedAuthenticationToken extends PreAuthenticatedAuthenticationToken {

    private static final long serialVersionUID = 6542624038478775647L;

    public AuthenticatedAuthenticationToken(String email, Collection<GrantedAuthority> userAuthorities) {
        super(email, null, userAuthorities);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("AuthenticatedAuthenticationToken: [");
        sb.append("Principal: ").append(this.getPrincipal()).append("; ");
        sb.append("Credentials: [PROTECTED]; ");
        sb.append("Authenticated: ").append(this.isAuthenticated()).append("; ");

        if (!getAuthorities().isEmpty()) {
            sb.append("Granted Authorities: ");

            int i = 0;
            for (org.springframework.security.core.GrantedAuthority authority : getAuthorities()) {
                if (i++ > 0) {
                    sb.append(", ");
                }

                sb.append(authority);
            }
        } else {
            sb.append("Not granted any authorities");
        }
        sb.append("]");
        return sb.toString();
    }

    public void setSessionToken(SessionToken token) {
        setDetails(token);
    }

}
