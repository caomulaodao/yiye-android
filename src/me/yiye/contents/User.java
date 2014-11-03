package me.yiye.contents;

public class User {
	public String email;
	public String username;
	public String password;
	
	@Override
	public String toString() {
		return "[user:" + username + ",password:" + password + "]";
	}
}
