package org.nh.rest.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.nh.rest.Application;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

public class AuthenticationFilter extends GenericFilterBean {

    public static final String TOKEN_SESSION_KEY = "token";
    public static final String USER_SESSION_KEY = "user";
    private AuthenticationManager authenticationManager;

    public AuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
            ServletException {
        HttpServletRequest httpRequest = asHttp(request);

        String email = httpRequest.getHeader(Application.X_AUTH_EMAIL);
        String password = httpRequest.getHeader(Application.X_AUTH_PASS);

        String sltk = httpRequest.getHeader(Application.X_AUTH_SLNTKN);

        String sid = httpRequest.getHeader(Application.X_AUTH_SID);

        try {
            if ((email != null) && (password != null)) {
                processEmailPasswordAuthentication(email, password);
            } else if (sltk != null) {
                processSilentAuthentication(sltk);
            } else if (sid != null) {
                processSessionCheckAuthentication(sid);
            }
        } catch (InternalAuthenticationServiceException internalAuthenticationServiceException) {
            SecurityContextHolder.clearContext();
            logger.error("Internal authentication service exception", internalAuthenticationServiceException);
        } catch (AuthenticationException authenticationException) {
            SecurityContextHolder.clearContext();
            logger.warn("AuthenticationException: " + authenticationException.getMessage());
        }

        logger.debug("AuthenticationFilter is passing request down the filter chain");
        chain.doFilter(request, response);
    }

    private HttpServletRequest asHttp(ServletRequest request) {
        return (HttpServletRequest) request;
    }

    private void processSessionCheckAuthentication(String sid) {
        Authentication requestAuthentication = new SidAuthenticationToken(sid);
        Authentication resultOfAuthentication = tryToAuthenticate(requestAuthentication);
        SecurityContextHolder.getContext().setAuthentication(resultOfAuthentication);
    }

    private void processSilentAuthentication(String sltk) {
        Authentication requestAuthentication = new SilentAuthenticationToken(sltk);
        Authentication resultOfAuthentication = tryToAuthenticate(requestAuthentication);
        SecurityContextHolder.getContext().setAuthentication(resultOfAuthentication);
    }

    private void processEmailPasswordAuthentication(String username, String password) throws IOException {
        UsernamePasswordAuthenticationToken requestAuthentication = new UsernamePasswordAuthenticationToken(username,
                password);
        Authentication resultOfAuthentication = tryToAuthenticate(requestAuthentication);
        SecurityContextHolder.getContext().setAuthentication(resultOfAuthentication);
    }

    private Authentication tryToAuthenticate(Authentication requestAuthentication) {
        Authentication responseAuthentication = authenticationManager.authenticate(requestAuthentication);
        if (responseAuthentication == null || !responseAuthentication.isAuthenticated()) {
            throw new InternalAuthenticationServiceException(
                    "Unable to authenticate Domain User for provided credentials");
        }
        return responseAuthentication;
    }
}