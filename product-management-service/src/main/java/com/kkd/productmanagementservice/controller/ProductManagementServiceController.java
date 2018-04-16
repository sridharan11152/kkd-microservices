package com.kkd.productmanagementservice.controller;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.kkd.productmanagementservice.ProductManagementServiceApplicationProxy;
import com.kkd.productmanagementservice.model.FarmerProductBean;
import com.kkd.productmanagementservice.service.SendMessageService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
@RestController
public class ProductManagementServiceController {
	FarmerProductBean mockFarmerProduct1 = new FarmerProductBean("productId","kkdFarmId","imageUrl","productName","description",10,8,100,true);
    /*
	 * CREATING AN INSTANCE OF LOGGERFACTORY
	 */
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	/* 
	 * CREATING A PROXY INSTANCE FOR FEIGN AND RIBBON CLIENTS
	 */
   @Autowired
   private ProductManagementServiceApplicationProxy proxy;
	/*
	 * CREATING A MESSAGESENDER SERVICE INSTANCE TO SEND MESSAGE TO RABBITMQ
	 */
	@Autowired
	private SendMessageService sendMessageToRabbit ;
	List<FarmerProductBean> farmerProducts = new ArrayList<FarmerProductBean>();
    ResponseEntity<List<FarmerProductBean>> productEntities ;
    ResponseEntity<Optional<FarmerProductBean>> addedProduct ;
    ResponseEntity<FarmerProductBean>  productsEntity;
	ResponseEntity<Boolean> deleteStatus;
      
    /*
	 * METHOD TO VIEW THE PRODUCTS OF A SPECIFIC FARMER
	 */
	@HystrixCommand(fallbackMethod = "fallbackForViewProducts")
	@GetMapping("/farmer/{farmerId}")
	public ResponseEntity<List<FarmerProductBean>> viewProducts(@PathVariable String farmerId) {
		logger.info("{}", "Calling farmer product service  to get the products  of farmer with id : " + farmerId);
		// calling the feign client (farmer-product-service) to get the products details
		productEntities = proxy.getFarmerProduct(farmerId);
		logger.info("{}", "Products of farmer id : " + farmerId + " are obtained");
		// sending message about successful retrieval of products details to rabbitMq
		this.sendMsg("Products of farmer id : "+farmerId+" are obtained ");
		return productEntities;
	}

	/*
	 * METHOD TO EDIT THE SPECIFIC PRODUCT OF A SPECIFIC FARMER
	 */
	@HystrixCommand(fallbackMethod = "fallbackForEditProducts")
	@PutMapping("/farmer/product")
	public ResponseEntity<FarmerProductBean> editProducts(@RequestBody FarmerProductBean farmerProduct) {
        logger.info("{}", "Calling farmer product service  to edit product ");
		// calling the feign client (farmer-product-service) to edit the product details
		productsEntity = proxy.updateFarmerProduct(farmerProduct);
		logger.info("{}", " product is edited");
		// sending message about successful editing of product details to rabbitMq
		this.sendMsg(" product  is edited");
		return productsEntity;
	}

	/*
	 * METHOD TO DELETE THE SPECIFIC PRODUCT OF A SPECIFIC FARMER
	 */
	@HystrixCommand(fallbackMethod = "fallbackForDeleteProducts")
	@DeleteMapping("/farmer/product/{productId}")
	public ResponseEntity<Boolean> deleteProducts( @PathVariable String productId) {
		logger.info("{}", "Calling farmer product service  to delete product (id : " + productId);
		// calling the feign client (farmer-product-service) to delete the product
		deleteStatus = proxy.deleteFarmerProduct( productId);
		logger.info("{}", " product (id : " + productId+"is deleted" );
		// sending message about successful deleting of product details to rabbitMq
		this.sendMsg(" product (id : "+ productId + " is deleted");
		return deleteStatus;
	}

	/*
	 * METHOD TO ADD A PRODUCT FOR A SPECIFIC FARMER
	 */
	@HystrixCommand(fallbackMethod="fallbackForAddProduct")
	@PostMapping("/farmer/{farmerId}")
	public ResponseEntity<Optional<FarmerProductBean>>  addProduct( @PathVariable String farmerId,@RequestBody FarmerProductBean farmerProduct ) {
      	logger.info("{}", "Calling farmer-product-service  to add a product  for farmer with id : "+farmerId );
		farmerProduct.setKkdFarmId(farmerId);
		addedProduct = proxy.addFarmerProduct(farmerProduct);
		logger.info("{}", "Product is added  for farmer id : "+farmerId );
		this.sendMsg("Product is added for farmer id : "+farmerId );
		return addedProduct;
	}

    /*
	 *  FALLBACK FUNCTION FOR ADDPRODUCT METHOD 
	 */
	public ResponseEntity<Optional<FarmerProductBean>>  fallbackForAddProduct(String farmerId,@RequestBody FarmerProductBean farmerProduct) {
		Optional<FarmerProductBean> product1 = Optional.of(mockFarmerProduct1);
		ResponseEntity<Optional<FarmerProductBean>>  addedProduct1 = new ResponseEntity<Optional<FarmerProductBean>>(product1,HttpStatus.OK);
		return addedProduct1;
	}

	/*
	 * FALLBACK FUNCTION FOR VIEWPRODUCTS METHOD
	 */
	public ResponseEntity<List<FarmerProductBean>> fallbackForViewProducts(String a) {
		FarmerProductBean mockFarmerProduct1 = new FarmerProductBean("fallback","kkdFarmId","imageUrl","productName","description",10,8,100,true);
		List<FarmerProductBean> mockList1 = new ArrayList<FarmerProductBean>();
		mockList1.add(mockFarmerProduct1);
		ResponseEntity<List<FarmerProductBean>> productEntitie = new ResponseEntity<List<FarmerProductBean>>(mockList1,HttpStatus.OK);
        return productEntitie;
	}

	/*
	 * FALLBACK FUNCTION FOR EDITPRODUCTS METHOD
	 */
	public ResponseEntity<FarmerProductBean> fallbackForEditProducts(FarmerProductBean farmerProduct) {
		FarmerProductBean mockFarmerProduct1 = new FarmerProductBean("fallback","kkdFarmId","imageUrl","productName","description",10,8,100,true);
		ResponseEntity<FarmerProductBean> productEntity = new ResponseEntity<FarmerProductBean>(mockFarmerProduct1,HttpStatus.OK);
        return productEntity ;
	}

	/*
	 * FALLBACK FUNCTION FOR DELETEPRODUCTS METHOD
	 */
	public ResponseEntity<Boolean> fallbackForDeleteProducts(String productId) {
		ResponseEntity<Boolean> deleteStatus1 = new ResponseEntity<Boolean>(true,HttpStatus.ACCEPTED);
		return deleteStatus1;
	}

	/*
	 * METHOD TO SEND MSG TO RABBITMQ
	 */
    public String sendMsg(String message){
		logger.info("Sending message...");
		sendMessageToRabbit.produceMsg(message); 
		return "Done";
	}
}