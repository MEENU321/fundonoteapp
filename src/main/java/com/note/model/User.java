package com.note.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "user")
public class User {
	@Id
	@Column(name="id")
	private int id;
	
	@Column(name="name")
	private String name;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Boolean getStatus() {
		return status;
	}
	public void setStatus(Boolean status) {
		this.status = status;
	}
	public String getPhonenumber() {
		return phonenumber;
	}
	public void setPhonenumber(String phonenumber) {
		this.phonenumber = phonenumber;
	}
	@Column(name="password")
	private String password;
	@Column(name="email")
	private String email;
	@Column(name="status")
	private Boolean status;
	@Column(name="phonenumber")
	private String phonenumber;
	@Override
	public String toString() {
		return "UserInfo [id=" + id + ", name=" + name + ", password=" + password + ", email=" + email + ", status="
				+ status + ", phonenumber=" + phonenumber + "]";
	}
}