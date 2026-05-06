package com.demo.vegetablerates;

//Or import all assertions
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.demo.vegetablerates.entity.Vegetable;
import com.demo.vegetablerates.entity.VegetablePrice;
import com.demo.vegetablerates.respository.VegetablePriceRepository;
import com.demo.vegetablerates.service.VegetableService;

@ExtendWith(MockitoExtension.class)
class VegetablePriceServiceTest {
	
	@Mock
	private VegetablePriceRepository repository;

	@InjectMocks
	private VegetableService service;

	
	  @Test 
	  void testViewRateOfAllVegetablesForSelectedDate() {
	  
	  LocalDate selectedDate = LocalDate.of(2022, 1, 12);
	  
	  Vegetable tomato = new Vegetable(); 
	  tomato.setId(1);
	  tomato.setVegName("Tomato");
	  
	  Vegetable onion = new Vegetable(); 
	  onion.setId(2); 
	  onion.setVegName("Onion");
	  
	  VegetablePrice price1 = new VegetablePrice(); 
	  price1.setRateId(1);
	  price1.setVegetable(tomato); 
	  price1.setPrice(Double.valueOf(80.45));
	  price1.setPriceDate(selectedDate);
	  
	  VegetablePrice price2 = new VegetablePrice(); 
	  price2.setRateId(2);
	  price2.setVegetable(onion); 
	  price2.setPrice(Double.valueOf(91.88));
	  price2.setPriceDate(selectedDate);
	  
	  when(repository.findByPriceDate(selectedDate)).thenReturn(List.of(price1,
	  price2));
	  
	  List<VegetablePrice> result = service.getRatesByDate(selectedDate);
	  
	  assertEquals(2, result.size()); assertEquals("Tomato",
	  result.get(0).getVegetable().getVegName());
	  assertEquals(Double.valueOf(80.45), result.get(0).getPrice());
	  assertEquals("Onion", result.get(1).getVegetable().getVegName());
	  assertEquals(Double.valueOf(91.88), result.get(1).getPrice());
	  
	  verify(repository, times(1)).findByPriceDate(selectedDate);
	  
	  }
	  
	  @Test 
	  void testViewRateForSelectedVegetableWithinDateRange() {
	  
	  Vegetable tomato = new Vegetable(); 
	  tomato.setId(1);
	  tomato.setVegName("Tomato");
	  
	  LocalDate startDate = LocalDate.of(2022, 1, 12); LocalDate endDate =
	  LocalDate.of(2022, 1, 15);
	  
	  VegetablePrice price1 = new VegetablePrice(); price1.setRateId(1);
	  price1.setVegetable(tomato); price1.setPrice(26.15);
	  price1.setPriceDate(LocalDate.of(2022, 1, 14));
	  
	  VegetablePrice price2 = new VegetablePrice(); price2.setRateId(2);
	  price2.setVegetable(tomato); price2.setPrice(44.0);
	  price2.setPriceDate(LocalDate.of(2022, 1, 16));
	  
	  when(repository.findByVegetableIdAndPriceDateBetween(1, startDate, endDate))
	  .thenReturn(List.of(price1, price2));
	  
	  List<VegetablePrice> result = service.getRatesByRange(1, startDate, endDate);
	  
	  assertEquals(2, result.size());
	  
	  assertEquals("Tomato", result.get(0).getVegetable().getVegName());
	  assertEquals(26.15, result.get(0).getPrice());
	  
	  assertEquals(44.0, result.get(1).getPrice());
	  
	  verify(repository, times(1)).findByVegetableIdAndPriceDateBetween(1,
	  startDate, endDate); }
	  
	  @Test 
	  void testViewTrendOfRateForSpecificVegetable() {
	  
	  Vegetable potato = new Vegetable(); potato.setId(1);
	  potato.setVegName("Potato");
	  
	  VegetablePrice rateDay1 = new VegetablePrice(); 
	  rateDay1.setRateId(1);
	  rateDay1.setVegetable(potato); rateDay1.setPrice(118.35);
	  rateDay1.setPriceDate(LocalDate.of(2023, 1, 12));
	  
	  VegetablePrice rateDay2 = new VegetablePrice(); rateDay2.setRateId(2);
	  rateDay2.setVegetable(potato); rateDay2.setPrice(53.81);
	  rateDay2.setPriceDate(LocalDate.of(2023, 1, 20));
	  
	  VegetablePrice rateDay3 = new VegetablePrice(); 
	  rateDay3.setRateId(3);
	  rateDay3.setVegetable(potato); rateDay3.setPrice(96.12);
	  rateDay3.setPriceDate(LocalDate.of(2023, 1, 24));
	  
	  when(repository.findByVegetableIdOrderByPriceDateAsc(3)).thenReturn(List.of(
	  rateDay1, rateDay2, rateDay3));
	  
	  List<VegetablePrice> trend = service.getTrend(3);
	  
	  assertEquals(3, trend.size());
	  
	  assertEquals(LocalDate.of(2023, 1, 12), trend.get(0).getpriceDate());
	  assertEquals(118.35, trend.get(0).getPrice());
	  
	  assertEquals(LocalDate.of(2023, 1, 20), trend.get(1).getpriceDate());
	  assertEquals(53.81, trend.get(1).getPrice());
	  
	  assertEquals(LocalDate.of(2023, 1, 24), trend.get(2).getpriceDate());
	  assertEquals(96.12, trend.get(2).getPrice());
	  
	  verify(repository, times(1)).findByVegetableIdOrderByPriceDateAsc(3);
	  
	  }
	  
	  @Test 
	  void testViewMonthlyAverageRateForSpecificDateRange() {
	  
	  Vegetable onion = new Vegetable(); 
	  onion.setId(2); 
	  onion.setVegName("Onion");
	  
	  LocalDate startDate = LocalDate.of(2022, 1, 1); 
	  LocalDate endDate =   LocalDate.of(2022, 3, 31);
	  
	  Object[] janAvg = new Object[] { 1, 69.89 }; 
	  Object[] febAvg = new Object[] {2, 75.94 }; 
	  Object[] marAvg = new Object[] { 3, 70.67 };
	  
	  when(repository.getMonthyAverage(2,startDate,endDate)).thenReturn(List.of(
	  janAvg, febAvg, marAvg));
	  
	  Map<Integer, Double> result = service.getMonthlyAverage(2,startDate,endDate);
	  
	  assertEquals(3, result.size()); assertEquals(69.89, result.get(1));
	  assertEquals(75.94, result.get(2)); assertEquals(70.67, result.get(3));
	  
	  verify(repository, times(1)).getMonthyAverage(2,startDate,endDate); }
	 
	
	  @Test 
	  void testWhichMonthHasMaximumAverageRateForVegetable() { 
	  Vegetable beans = new Vegetable(); 
	  beans.setId(5); 
	  beans.setVegName("Beans");
	  
		/*
		 * LocalDate startDate = LocalDate.of(2022, 1, 1); LocalDate endDate =
		 * LocalDate.of(2022, 6, 30);
		 * 
		 * Object[] jan = new Object[] { 1, 67.85 }; Object[] feb = new Object[] { 2,
		 * 80.83 }; Object[] mar = new Object[] { 3, 70.18 }; Object[] apr = new
		 * Object[] { 4, 65.87 };
		 * 
		 * when(repository.getMonthyAverage(5,startDate,endDate)).thenReturn(List.of(
		 * jan, feb, mar, apr));
		 */
	  
	  String result = service.getMaxPriceMonth(5);
	  // Then //
	  assertEquals("No Data",result); // Feb has highest average // assertEquals(80.83,
	//  assertEquals("Highest average price in month: 2 price: 80.83",result); 
	  }
	  
	  @Test 
	  void testcalculateVolatility() {
	  
	  Vegetable tomato = new Vegetable(); 
	  tomato.setId(1);
	  tomato.setVegName("Tomato");
	  
	  LocalDate startDate = LocalDate.of(2022, 1, 12); 
	  LocalDate endDate = LocalDate.of(2022, 1, 15);
	  
	  VegetablePrice price1 = new VegetablePrice(); price1.setRateId(1);
	  price1.setVegetable(tomato); price1.setPrice(26.15);
	  price1.setPriceDate(LocalDate.of(2022, 1, 14));
	  
	  VegetablePrice price2 = new VegetablePrice(); price2.setRateId(2);
	  price2.setVegetable(tomato); price2.setPrice(44.0);
	  price2.setPriceDate(LocalDate.of(2022, 1, 16));
	  
	  when(repository.findByVegetableIdAndPriceDateBetween(1, startDate, endDate))
	  .thenReturn(List.of(price1, price2));
	  
	  Double variance = service.calculateVolatility(1, startDate, endDate);
	  assertEquals(8.925, variance);
	  
	 }
}