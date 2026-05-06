package com.demo.vegetablerates.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "vegetable")
public class Vegetable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "veg_Id")
	private int id;
	
	@Column(name = "veg_name", nullable = false)
	private String vegName;

	
	/*
	 * public int getId() { return id; }
	 */

	public void setId(int id) {
		this.id = id;
	}

	public String getVegName() {
		return vegName;
	}

	public void setVegName(String vegName) {
		this.vegName = vegName;
	}


}
