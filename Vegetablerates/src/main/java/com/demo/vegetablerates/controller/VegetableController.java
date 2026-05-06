package com.demo.vegetablerates.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.demo.vegetablerates.entity.VegetablePrice;
import com.demo.vegetablerates.service.VegetableService;

@RestController
@RequestMapping(value = "/api/vegetables")
public class VegetableController {

	@Autowired
	private VegetableService vegetableService;

	/*
	 * @GetMapping("/rates") public List<VegetablePrice> getRates(@RequestParam
	 * LocalDate date) { return vegetableService.getRatesByDate(date); }
	 */
	

    @GetMapping("/rates")
    public ResponseEntity<List<VegetablePrice>> getRates(
            @RequestParam LocalDate date) {
        return ResponseEntity.ok(vegetableService.getRatesByDate(date));
    }


	/*
	 * @GetMapping("/{id}/range") public List<VegetablePrice> getRange(@PathVariable
	 * Integer id, @RequestParam LocalDate startDate,
	 * 
	 * @RequestParam LocalDate endDate) { return
	 * vegetableService.getRatesByRange(id, startDate, endDate); }
	 */
	
	@GetMapping("/{id}/range")
	public ResponseEntity<List<VegetablePrice>> getRange(@PathVariable Integer id, @RequestParam LocalDate startDate,
			@RequestParam LocalDate endDate) {
		return ResponseEntity.ok(vegetableService.getRatesByRange(id, startDate, endDate));
	}
	
	/*
	 * @GetMapping("/{id}/trend") public List<VegetablePrice> getTrend(@PathVariable
	 * Integer id) { return vegetableService.getTrend(id); }
	 */
	
	@GetMapping("/{id}/trend")
	public ResponseEntity<List<VegetablePrice>> getTrend(@PathVariable Integer id) {
		return ResponseEntity.ok(vegetableService.getTrend(id));
	}

	/*
	 * @GetMapping("/{id}/monthly-average") public Map<Integer, Double>
	 * getAverage(@PathVariable Integer id, @RequestParam LocalDate startDate,
	 * 
	 * @RequestParam LocalDate endDate) { return
	 * vegetableService.getMonthlyAverage(id, startDate, endDate); }
	 */
	
	@GetMapping("/{id}/monthly-average")
	public ResponseEntity<Map<Integer, Double>> getAverage(@PathVariable Integer id, @RequestParam LocalDate startDate,

			@RequestParam LocalDate endDate) {
		return ResponseEntity.ok(vegetableService.getMonthlyAverage(id, startDate, endDate));
	}
	
	/*
	 * @GetMapping("/{id}/insights") public String getInsights(@PathVariable Integer
	 * id) { return vegetableService.getMaxPriceMonth(id); }
	 */
	
	@GetMapping("/{id}/insights")
	public ResponseEntity<String> getInsights(@PathVariable Integer id) {
		return ResponseEntity.ok(vegetableService.getMaxPriceMonth(id));
	}
	
	/*
	 * @GetMapping("/{id}/volatility") public double volatility(@PathVariable
	 * Integer id, @RequestParam LocalDate startDate,
	 * 
	 * @RequestParam LocalDate endDate) { return
	 * vegetableService.calculateVolatility(id, startDate, endDate);
	 */
		
		@GetMapping("/{id}/volatility")
		public ResponseEntity<Double> checkvolatility(@PathVariable Integer id, @RequestParam LocalDate startDate,
				@RequestParam LocalDate endDate) {
			return ResponseEntity.ok(vegetableService.calculateVolatility(id, startDate, endDate));
	}

}
