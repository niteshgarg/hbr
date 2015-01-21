package com.oneminuut.hbr.dao.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "user")
public class User implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	@Column(name = "user_id")
	private long userId;

	@Column(name = "first_name", nullable = false, unique = true, length = 70)
	private String firstName;
	
	@Column(name = "last_name", length = 70)
	private String lastName;

	@Column(name = "email", nullable = false, unique = true, length = 70)
	private String email;

	@Column(name = "password", nullable = false, length = 15)
	private String password;

	@Column(name = "created")
	private Date created;

	@Column(name = "updated")
	private Date updated;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "hospital_id")
	private Hospital hospital;
	
	@ManyToOne(cascade = CascadeType.ALL)
	private Role role;

	
	
	public Date getUpdated() {
		return updated;
	}

	public void setUpdated(Date updated) {
		this.updated = updated;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	@Override
	public String toString() {
		return "User [user_id =" + userId + ", email =" + email + "]";
	}

}
