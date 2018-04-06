package com.kkd.farmerviewproductservice;


import java.util.ArrayList;

import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

@FeignClient(name="farmer-product-service")
@RibbonClient(name="farmer-product-service")
public interface FarmerViewproductServiceApplicationProxy {
    
	
    @GetMapping("/farmer/{farmerId}/product")
	ArrayList<FarmerProduct> viewProducts(@PathVariable("farmerId") String farmerId);
    
    @PutMapping("farmer/{farmerId}/product/{productId}")
	void editProducts(@PathVariable("farmerId") String farmerId,@PathVariable("productId") String productId, FarmerProduct farmerProduct);

	@DeleteMapping("farmer/{farmerId}/product/{productId}")
    void deleteProducts(@PathVariable("farmerId") String farmerId,@PathVariable("productId") String productId);

}
