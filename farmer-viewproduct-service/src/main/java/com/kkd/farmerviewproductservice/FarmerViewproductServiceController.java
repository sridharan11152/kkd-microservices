package com.kkd.farmerviewproductservice;

import org.springframework.web.bind.annotation.RestController;
import java.util.ArrayList;
import org.apache.commons.lang.math.RandomUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;


@RestController
public class FarmerViewproductServiceController {
    
	
	/*
     * CREATING AN INSTANCE OF LOGGERFACTORY
     */
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	/*
	 * CREATING A PROXY INSTANCE FOR FEIGN AND RIBBON CLIENTS
	 */
	@Autowired
	private FarmerViewproductServiceApplicationProxy proxy ;
	
	/*
	 * CREATING A MESSAGESENDER SERVICE INSTANCE TO SEND MESSAGE TO RABBITMQ
	 */
	@Autowired
	private MessageSender sendMessageToRabbit;

	
	ArrayList<FarmerProduct> farmerProducts=new ArrayList<FarmerProduct>();
	
	/*
	 * TEST METHOD
	 */
	@HystrixCommand(fallbackMethod="fallback")
	@GetMapping("/bye")
	public String sayBye() {
		logger.info("{}", "hi");

		if(RandomUtils.nextBoolean()) {
			throw new RuntimeException("Failed loadin Bye");
		}
		return "bye";
	}

    /*
     * METHOD TO VIEW THE PRODUCTS OF A SPECIFIC FARMER
     */
	@HystrixCommand(fallbackMethod="fallbackForViewProducts")
	@GetMapping("/farmer/{farmerId}/product")
	public void  viewProducts( @PathVariable String farmerId) {
		logger.info("{}", "Calling farmer product service  to get the products  of farmer with id : "+farmerId );
		//calling the feign client (farmer-product-service) to get the products details of the farmer
 		farmerProducts = proxy.viewProducts(farmerId);
 		// log message about successful retrieval of products details
		logger.info("{}", "Products of farmer id : "+farmerId+" are obtained");
		// sending message about successful retrieval of products details to rabbitMq
		this.sendMsg("Products of farmer id : "+farmerId+" are obtained ");
	}
    
	/*
	 * METHOD TO EDIT THE SPECIFIC PRODUCT OF A SPECIFIC FARMER
	 */
	@HystrixCommand(fallbackMethod="fallbackForEditProducts")
	@PutMapping("/farmer/{farmerId}/product/{productId}")
	public void editProducts(@PathVariable String farmerId,@PathVariable String productId,@RequestBody FarmerProduct farmerProduct) {

		logger.info("{}", "Calling farmer product service  to edit product (id : "+ productId + " )of farmer with id : "+farmerId );
		//calling the feign client (farmer-product-service) to edit the product details of the farmer
		proxy.editProducts(farmerId,productId,farmerProduct);
		// log message about successful editing of products details
		logger.info("{}", " product (id : "+ productId + " )of farmer with id : "+farmerId+ " is edited" );
		//sending message about successful editing of product details to rabbitMq
		this.sendMsg(" product (id : "+ productId + " )of farmer with id : "+farmerId+ " is edited");
	}

    /*
     * METHOD TO DELETE THE SPECIFIC PRODUCT OF A SPECIFIC FARMER
     */
	@HystrixCommand(fallbackMethod="fallbackForDeleteProducts")
	@DeleteMapping("/farmer/{farmerId}/product/{productId}")
	public void deleteProducts(@PathVariable String farmerId,@PathVariable String productId) {
		logger.info("{}", "Calling farmer product service  to delete product (id : "+ productId + " )of farmer with id : "+farmerId );
		//calling the feign client (farmer-product-service) to delete the product details of the farmer
		proxy.deleteProducts(farmerId,productId);
		// log message about successful deleting of product details
        logger.info("{}", " product (id : "+ productId + " )of farmer with id : "+farmerId+ " is deleted" );
		//sending message about successful deleting of product details to rabbitMq
        this.sendMsg(" product (id : "+ productId + " )of farmer with id : "+farmerId+ " is deleted");
	}

	/*
	 *  FALLBACK FUNCTION FOR VIEWPRODUCTS METHOD 
	 */
	public String fallbackForViewProducts() {

		return "Product cannot be viewed right now";
	}

	/*
	 *  FALLBACK FUNCTION FOR EDITPRODUCTS METHOD 
	 */
	public String fallbackForEditProducts() {

		return "Product cannot be edited right now";
	}

	/*
	 *  FALLBACK FUNCTION FOR DELETEPRODUCTS METHOD 
	 */
	public String fallbackForDeleteProducts() {
		

		return "Product cannot be deleted right now";
	}

	/*
	 *  FALLBACK FUNCTION FOR TEST METHOD 
	 */
    public String fallback() {

		return "fallback for test method";
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
