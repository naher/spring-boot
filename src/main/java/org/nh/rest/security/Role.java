package org.nh.rest.security;

public enum Role {

    //@formatter:off
	ROLE_ADMIN("Admin user"),
	ROLE_USER("User"),
    ROLE_ANONYMOUS("Anonymous user");
    //@formatter:on

    private String description;
    private GrantedAuthority grantedAuthority;

    private Role(String description) {
        this.description = description;
        this.grantedAuthority = new GrantedAuthority(this);
    }
        
    public String getDescription() {
        return description;
    }

    public GrantedAuthority getGrantedAuthority() {
        return grantedAuthority;
    }        
    
    public static class GrantedAuthority implements org.springframework.security.core.GrantedAuthority {
    	
		private static final long serialVersionUID = 1515995351841158080L;
		protected Role role;
        
        public GrantedAuthority(Role role) 
        { this.role = role; 
        }
        
        @Override
        public String getAuthority() {
            return role.name();
        }

        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + ((role == null) ? 0 : role.hashCode());
            return result;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (obj == null)
                return false;
            if ((obj instanceof Role) && ((Role) obj).equals(role))
                return true;
            if (getClass() != obj.getClass())
                return false;
            GrantedAuthority other = (GrantedAuthority) obj;
            return getAuthority().equals(other.getAuthority());
        }
    };
}
