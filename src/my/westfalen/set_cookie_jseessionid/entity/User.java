package my.westfalen.set_cookie_jseessionid.entity;

import java.io.Serializable;

public class User implements Serializable{
	private String name;
	private String password;
	private String realname;
	
	public User(String name, String password, String realname) {
		super();
		this.name = name;
		this.password = password;
		this.realname = realname;
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

	public String getRealname() {
		return realname;
	}

	public void setRealname(String realname) {
		this.realname = realname;
	}
	
}
