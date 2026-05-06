package com.demo.vegetablerates.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "vegetable_rates")
public class VegetablePrice {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "rate_id")
	private int rateId;
	
	@ManyToOne
    @JoinColumn(name = "vegetable_Id")
    private Vegetable vegetable;
	@Column(name = "price")
	private Double price;
	@Column(name = "price_date")
	private LocalDate priceDate;

	
	public VegetablePrice(int i, Vegetable tomato, double d, LocalDate selectedDate) {
		super();
		this.rateId = rateId;
		this.vegetable = vegetable;
		this.price = price;
		this.priceDate = priceDate;
	}
	 

	public VegetablePrice() {
		// TODO Auto-generated constructor stub
	}


	public Vegetable getVegetable() {
		return vegetable;
	}

	public void setVegetable(Vegetable vegetable) {
		this.vegetable = vegetable;
	}

	

	/*
	 * public int getRateId() { return rateId; }
	 */

	public void setRateId(int rateId) {
		this.rateId = rateId;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public LocalDate getpriceDate() {
		return priceDate;
	}

	public void setPriceDate(LocalDate priceDate) {
		this.priceDate = priceDate;
	}
}
