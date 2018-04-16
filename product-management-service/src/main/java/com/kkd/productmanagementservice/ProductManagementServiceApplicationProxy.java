package com.kkd.productmanagementservice;
import java.util.List;
import java.util.Optional;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import com.kkd.productmanagementservice.model.FarmerProductBean;
@FeignClient(name="farmer-product-service")
@RibbonClient(name="farmer-product-service")
public interface ProductManagementServiceApplicationProxy {
	
	@PostMapping("/farmer/product")
	public ResponseEntity<Optional<FarmerProductBean>> addFarmerProduct(@RequestBody FarmerProductBean product);

    @GetMapping("/farmer/{farmer_id}/product")
	public ResponseEntity<List<FarmerProductBean>> getFarmerProduct(@PathVariable("farmer_id") String farmer_id);

    @PutMapping("/farmer/product")
	public ResponseEntity<FarmerProductBean> updateFarmerProduct(@RequestBody FarmerProductBean product); 

    @DeleteMapping("/farmer/product/{product_id}")
	public ResponseEntity<Boolean> deleteFarmerProduct(@PathVariable("product_id") String product_id);
}
