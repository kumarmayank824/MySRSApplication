package com.domain;

import javax.persistence.*;

@Entity
@Table(name="user_roles")
public class UserRole {
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)    
    @Column(name="user_role_id")
	private Long userroleid;
	
	@Column(name="role")
	private String role;	
    
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId", nullable = false)
    private User user;
	
	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public Long getUserroleid() {
		return userroleid;
	}

	public void setUserroleid(Long userroleid) {
		this.userroleid = userroleid;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}	
	
	
	
}
