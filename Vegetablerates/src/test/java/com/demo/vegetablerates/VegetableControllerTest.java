package com.demo.vegetablerates;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.demo.vegetablerates.VegetableratesApplication;
import com.demo.vegetablerates.controller.VegetableController;
import com.demo.vegetablerates.entity.Vegetable;
import com.demo.vegetablerates.entity.VegetablePrice;
import com.demo.vegetablerates.service.VegetableService;


@WebMvcTest(VegetableController.class)
@ContextConfiguration(classes = VegetableratesApplication.class)
class VegetableControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private VegetableService vegetableService;

  //  private ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void testGetRatesByDate() throws Exception {
		Vegetable tomato = new Vegetable();
		tomato.setId(1);
		tomato.setVegName("Tomato");
		Vegetable onion = new Vegetable();
		onion.setId(2);
		onion.setVegName("Onion");
        List<VegetablePrice> mockResponse = List.of(
                new VegetablePrice(1,tomato, 80.45, LocalDate.of(2022, 1, 12)),
                new VegetablePrice(2,onion, 91.88, LocalDate.of(2022, 1, 12))
        );

        Mockito.when(vegetableService.getRatesByDate(
                LocalDate.of(2022, 1, 12)))
                .thenReturn(mockResponse);

        mockMvc.perform(get("/api/vegetables/rates").param("date", "2022-01-12"))
        .andExpect(status().isOk())
		.andExpect(jsonPath("$.size()").value(2));
	//	.andExpect(jsonPath("$[0].vegetableName").value("Tomato"));
		 
    }
    
    @Test
    void testGetRatesForVegetableInRange() throws Exception {
    	Vegetable tomato = new Vegetable();
		tomato.setId(1);
		tomato.setVegName("Tomato");
		
		VegetablePrice price1 = new VegetablePrice(); price1.setRateId(1);
		  price1.setVegetable(tomato); price1.setPrice(26.15);
		  price1.setPriceDate(LocalDate.of(2022, 1, 14));
		  
		  VegetablePrice price2 = new VegetablePrice(); price2.setRateId(2);
		  price2.setVegetable(tomato); price2.setPrice(44.0);
		  price2.setPriceDate(LocalDate.of(2022, 1, 16));
    	 
		  List<VegetablePrice> mockResponse = List.of(
                 new VegetablePrice(1,tomato, 26.15, LocalDate.of(2022, 1, 12)),
                 new VegetablePrice(2,tomato, 44.0, LocalDate.of(2022, 1, 15))
         );

        Mockito.when(
                vegetableService.getRatesByRange(1,LocalDate.of(2022, 1, 12),LocalDate.of(2022, 1, 15)))
                .thenReturn(mockResponse);

        mockMvc.perform(get("/api/vegetables/1/range")
                        .param("startDate", "2022-01-12")
                        .param("endDate", "2025-01-15"))
                .andExpect(status().isOk());
              //  .andExpect(jsonPath("$[1].rate").value(45));
    }
    @Test
    void testGetRateTrend() throws Exception {
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
  	  
     List<VegetablePrice> trend = List.of(rateDay1, rateDay2, rateDay3);

        Mockito.when(
                vegetableService.getTrend(3))
                .thenReturn(trend);

        mockMvc.perform(get("/api/vegetables/3/trend"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(3));
            //    .andExpect(jsonPath("$[1].rate").value(50));
    }
    
	/*
	 * @Test void testGetMonthlyAverage() throws Exception { Vegetable onion = new
	 * Vegetable(); onion.setId(2); onion.setVegName("Onion"); VegetablePrice price1
	 * = new VegetablePrice(); price1.setRateId(1); price1.setVegetable(onion);
	 * price1.setPrice(69.89); price1.setPriceDate(LocalDate.of(2022, 1, 1));
	 * 
	 * VegetablePrice price2 = new VegetablePrice(); price2.setRateId(2);
	 * price2.setVegetable(onion); price2.setPrice(75.94);
	 * price2.setPriceDate(LocalDate.of(2022, 2, 28));
	 * 
	 * VegetablePrice price3 = new VegetablePrice(); price3.setRateId(3);
	 * price2.setVegetable(onion); price2.setPrice(70.67);
	 * price2.setPriceDate(LocalDate.of(2022, 3, 31));
	 * 
	 * // Map<Integer, Double> janAvg = Map.of(1, 69.89); Map<Integer, Double>
	 * febAvg = Map.of(2, 75.94); // Map<Integer, Double> marAvg = Map.of(3, 70.67);
	 * 
	 * // Map<Integer, Double> mockresponse = (Map<Integer, Double>)
	 * List.of(janAvg,febAvg,marAvg);
	 * 
	 * LocalDate startDate = LocalDate.of(2022, 1, 1); LocalDate endDate =
	 * LocalDate.of(2022, 3, 31);
	 * 
	 * Mockito.when( vegetableService.getMonthlyAverage(2,startDate,endDate))
	 * .thenReturn(febAvg);
	 * 
	 * mockMvc.perform(get("/api/vegetables/2/monthly-average"))
	 * .andExpect(status().isOk()); //
	 * .andExpect(jsonPath("$[0].month").value("January")); }
	 */
    

    @Test
    void shouldReturnMonthlyAveragePriceForDateRange() throws Exception {
        // Arrange
        LocalDate startDate = LocalDate.of(2022, 1, 1);
        LocalDate endDate = LocalDate.of(2022, 3, 31);

        Map<Integer, Double> mockResponse = new HashMap<>();
        mockResponse.put(1, 69.89);
        mockResponse.put(2, 75.94);
        mockResponse.put(3, 70.67);

        Mockito.when(vegetableService.getMonthlyAverage(2,startDate, endDate))
               .thenReturn(mockResponse);

        // Act & Assert
        mockMvc.perform(get("/api/vegetables/2/monthly-average")
        		.param("startDate", "2022-01-01")
                .param("endDate", "2022-03-31"))
             //   .contentType(MediaType.APPLICATION_JSON)
                 .andExpect(status().isOk());
               /* .andExpect(jsonPath("$.1").value(69.89))
                .andExpect(jsonPath("$.2").value(75.94)
                .andExpect(jsonPath("$.3").value(70.67);*/

        // Verify service call
    //    Mockito.verify(vegetableService).getMonthlyAverage(2,startDate, endDate);
    }


    @Test
    void testGetInsights() throws Exception {
        String insight = "Highest average price in month: Feb with price:80.99";

        Mockito.when(
                vegetableService.getMaxPriceMonth(1))
                .thenReturn(insight);

        mockMvc.perform(get("/api/vegetables/2/insights"))
                .andExpect(status().isOk());
              //  .andExpect(content().string(insight));
    }
    
    @Test 
	  void testcheckvolatility() throws Exception {
	  
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
	  
	  Mockito.when(vegetableService.getRatesByRange(1, startDate, endDate))
	  .thenReturn(List.of(price1, price2));
	  
	  Double variance = vegetableService.calculateVolatility(1, startDate, endDate);
	  
	  mockMvc.perform(get("/api/vegetables/2/volatility")
      		.param("startDate", "2022-01-01")
              .param("endDate", "2022-03-31"))
	  .andExpect(status().isOk());
	  assertEquals(0.0, variance);
}

}
