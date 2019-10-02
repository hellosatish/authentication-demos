package org.sk.security.demo.config.security;

import java.security.Principal;

import org.sk.security.demo.db.repositories.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
/**
 * 
 * @author satish sharma
 * 
 *  Find and configure user details to be available as {@link Principal}
 * */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	private UserRepository userRepo;

	public UserDetailsServiceImpl(UserRepository userRepo) {
		this.userRepo = userRepo;
	}

	/**
	 *  User Principal finding logic.
	 */
	@Override
	public UserDetails loadUserByUsername(String loginId) throws UsernameNotFoundException {

		return this.userRepo.findByLoginId(loginId)
				.map(UserPrincipal::new)
				.orElseThrow(() -> new UsernameNotFoundException("LoginId: " + loginId + " not found"));
	}

}
