package gr.opengov.agora.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

public class WebClientFactory implements IClientFactory {

	@Override
	public IClient getClient() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if ( auth instanceof IClient ) {
			return (IClient)auth;
		}
		for ( GrantedAuthority ga : auth.getAuthorities() ) {
			if ( ga.getAuthority().equals( "ROLE_ANONYMOUS" ) ) {
				return new AnonymousUser();
			}
		}
		return null;
	}
}
