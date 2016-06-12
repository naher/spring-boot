package org.nh.rest.security;

public interface SessionService {

	SessionToken authenticate(String email, String password);

	SessionToken createSession(String sltk);

	SessionToken createSessionWithHash(String email, String passhash);

	boolean contains(String sid);

	SessionToken retrieve(String sid);

	void remove(String sid);
}
