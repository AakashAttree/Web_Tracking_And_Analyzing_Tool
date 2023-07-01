package web.tracking.security.userdetails;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

public class WebTrackingUserDetails implements UserDetails {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String username;
	private String password;
	private List<GrantedAuthority> grantedAuthorities;
	private final String compId;

	public WebTrackingUserDetails(String username, String password, String[] authorities, String compId) {
		super();
		this.compId = compId;
		this.username = username;
		this.password = password;
		this.grantedAuthorities = AuthorityUtils.createAuthorityList(authorities);
		;
	}

	public Collection<? extends GrantedAuthority> getAuthorities() {
		return grantedAuthorities;
	}

	public String getPassword() {
		return password;
	}

	public String getUsername() {
		return username;
	}

	public boolean isAccountNonExpired() {
		return true;
	}

	public boolean isAccountNonLocked() {
		return true;
	}

	public boolean isCredentialsNonExpired() {
		return true;
	}

	public boolean isEnabled() {
		return true;
	}

	public String getCompId() {
		return compId;
	}

}
