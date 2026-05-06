package com.demo.vegetablerates.service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.vegetablerates.entity.VegetablePrice;
import com.demo.vegetablerates.respository.VegetablePriceRepository;

@Service
public class VegetableService {

	@Autowired
	private VegetablePriceRepository repo;

	public List<VegetablePrice> getRatesByDate(LocalDate date) {
		return repo.findByPriceDate(date);

	}

	public List<VegetablePrice> getRatesByRange(Integer vegetableId, LocalDate startDate, LocalDate endDate) {
		return repo.findByVegetableIdAndPriceDateBetween(vegetableId, startDate, endDate);

	}

	public List<VegetablePrice> getTrend(Integer vegetableId) {
		return repo.findByVegetableIdOrderByPriceDateAsc(vegetableId);

	}

	public Map<Integer, Double> getMonthlyAverage(Integer vegetableId, LocalDate startDate, LocalDate endDate) {
		List<Object[]> data = repo.getMonthyAverage(vegetableId, startDate, endDate);
		Map<Integer, Double> result = new HashMap<>();
		for (Object[] row : data) {
			result.put((Integer) row[0], (Double) row[1]);
		}
		return result;

	}

	public String getMaxPriceMonth(Integer vegetableId) {
		
		/*
		 * List<Object[]> data = repo.getMaxMonth(vegetable_Id); Map<Integer, Double>
		 * result = new HashMap<>(); for (Object[] row : data) { result.put((Integer)
		 * row[0], (Double) row[1]); } return result;
		 */
		 List<Object[]> data = repo.getMaxMonth(vegetableId);

		  if (data.isEmpty()) {
			  return "No Data";
		  }
		  else {
		  Integer month = (Integer) data.get(0)[0]; 
		  Double price = (Double)data.get(0)[1];
		  return "Highest average price in month: " + month + " with price: " + price;
		  }
	
		 
	}
	

	// Feature 6 – Price Volatility
	    public double calculateVolatility(Integer vegetableId, LocalDate startDate, LocalDate endDate) {

	        List<VegetablePrice> prices =
	        		repo.findByVegetableIdAndPriceDateBetween(vegetableId, startDate, endDate);

	        double avg = prices.stream()
	                .mapToDouble(p -> p.getPrice().doubleValue())
	                .average().orElse(0);

	        double variance = prices.stream()
	                .mapToDouble(p -> Math.pow(
	                        p.getPrice().doubleValue() - avg, 2))
	                .average().orElse(0);

	        return Math.sqrt(variance);
	    }


}