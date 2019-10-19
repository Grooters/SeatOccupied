package io.github.grooters.seatOccupied.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "administrator")
public class Administrator {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	@NotNull
	private String number;
	@NotNull
	private String name;
	@NotNull
	private String pass;
	@NotNull
	private int key;

	public Administrator() {
	}

	public Administrator(String number, String name, String pass, int key) {
		this.number = number;
		this.name = name;
		this.pass = pass;
		this.key = key;
	}

	public int getKey() {
		return key;
	}

	public void setKey(int key) {
		this.key = key;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}
}
