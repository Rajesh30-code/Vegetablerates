package com.demo.vegetablerates.respository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.demo.vegetablerates.entity.VegetablePrice;

@Repository
public interface VegetablePriceRepository extends JpaRepository<VegetablePrice, Integer> {

	// View rate of all vegetables for a selected date
	List<VegetablePrice> findByPriceDate(LocalDate date);

	// View rate for a month/specific date range for the selected vegetable

	List<VegetablePrice> findByVegetableIdAndPriceDateBetween(Integer vegId, LocalDate startDate, LocalDate endDate);

	// Trend (ordered data)

	List<VegetablePrice> findByVegetableIdOrderByPriceDateAsc(Integer vegId);

	// Monthty average

	@Query("SELECT MONTH(v.priceDate), AVG(v.price)" + " FROM VegetablePrice v " + " WHERE v.vegetable.id = :vegId "
			+ " AND v.priceDate BETWEEN :startDate AND :endDate " + " GROUP BY MONTH(v.priceDate)")
	List<Object[]> getMonthyAverage(Integer vegId, LocalDate startDate, LocalDate endDate);

	// Max month insight

	@Query("SELECT MONTH(v.priceDate), AVG(v.price) as avgPrice" + " FROM VegetablePrice v "
			+ " WHERE v.vegetable.id = :vegId " + " GROUP BY MONTH(v.priceDate) ORDER BY avgPrice DESC")
	List<Object[]> getMaxMonth(Integer vegId);

}
