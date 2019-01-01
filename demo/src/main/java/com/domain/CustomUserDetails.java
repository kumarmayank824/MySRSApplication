package com.domain;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;

import com.services.UserService;
import com.util.CommonUtil;


public class CustomUserDetails extends User implements UserDetails {	
	
	private static final long serialVersionUID = 1L;
	private List<String> userRoles;
	
	public CustomUserDetails(User user,List<String> userRoles){
		super(user);
		this.userRoles=userRoles;
	}
	
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		
		String roles=StringUtils.collectionToCommaDelimitedString(userRoles);			
		return AuthorityUtils.commaSeparatedStringToAuthorityList(roles);
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}
	
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}
	@Override
	public boolean isEnabled() {
		return super.isEnabled();
	}

	@Override
	public String getEmail() {
		return super.getEmail();
	}
	
	@Override
	public String getPassword() {
		CommonUtil commonUtil = new CommonUtil();
		return commonUtil.decoder(super.getPassword());
	}


}
