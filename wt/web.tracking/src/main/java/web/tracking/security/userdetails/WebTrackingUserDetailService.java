package web.tracking.security.userdetails;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import web.tracking.db.dao.AccountRepository;
import web.tracking.db.dto.AccountDTO;

public class WebTrackingUserDetailService implements UserDetailsService {

	@Autowired
	AccountRepository accountRepository;

	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		AccountDTO accountDTO = accountRepository.findByUserName(username);
		WebTrackingUserDetails userDetails = new WebTrackingUserDetails(username, accountDTO.getPassword(),
				accountDTO.getRoles(), accountDTO.getCompId());
		return userDetails;
	}

}
