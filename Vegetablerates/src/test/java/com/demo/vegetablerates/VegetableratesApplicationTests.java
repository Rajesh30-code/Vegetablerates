package com.demo.vegetablerates;

import org.junit.jupiter.api.Test;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootTest;

import com.demo.vegetablerates.VegetableratesApplication;

@SpringBootTest(classes = VegetableratesApplication.class)
class VegetableratesApplicationTests {
	

   @Test
    void mainMethodRunsWithoutException() {
        VegetableratesApplication.main(new String[] {});
    }
	
	@Test
	void contextLoads() {
		
	}

}
