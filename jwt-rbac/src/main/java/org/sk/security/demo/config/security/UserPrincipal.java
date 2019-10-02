package org.sk.security.demo.config.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.sk.security.demo.db.entities.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * 
 * @author satish sharma
 *
 */
public class UserPrincipal implements UserDetails {
	private final User user;

	public UserPrincipal(User user) {
		this.user = user;
	}
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<SimpleGrantedAuthority> authorities = new ArrayList<>();
		authorities.add(new SimpleGrantedAuthority(this.user.getRole()));
		return authorities;
	}
	@Override
	public String getPassword() {
		return user.getPassword();
	}
	@Override
	public String getUsername() {
		return user.getFirstName();
	}
	@Override
	public boolean isAccountNonExpired() {
		return !user.isActive();
	}
	@Override
	public boolean isAccountNonLocked() {
		return !user.isActive();
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return !user.isActive();
	}

	@Override
	public boolean isEnabled() {
		return user.isActive();
	}
}
