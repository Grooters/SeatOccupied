package io.github.grooters.seatOccupied.model;

import java.util.List;

public class AllUsers {
	private List<User> users;

	public AllUsers() {
	}

	public AllUsers(List<User> users) {
		this.users = users;
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	public int getSize() {
		return users.size();
	}

	public User get(int i) {
		return users.get(i);
	}

	public void set(int i, User u) {
		users.set(i, u);
	}

}
