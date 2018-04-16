package com.kkd.productmanagementservice;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import com.kkd.productmanagementservice.controller.ProductManagementServiceController;
import com.kkd.productmanagementservice.model.FarmerProductBean;
import com.kkd.productmanagementservice.service.SendMessageService;

@RunWith(SpringRunner.class)
@WebMvcTest(value= ProductManagementServiceController.class)
public class ProductManagementServiceApplicationTests {

	@Autowired
	private MockMvc mockMvc;
    @MockBean
	ProductManagementServiceApplicationProxy proxy;
	@MockBean
	SendMessageService sendMessageService;
    @MockBean 
	ResponseEntity<List<FarmerProductBean>> expected ;
	@MockBean
    ResponseEntity<Optional<FarmerProductBean>> addedProduct ;
    FarmerProductBean mockFarmerProduct1 = new FarmerProductBean("productId","kkdFarmId","imageUrl","productName","description",10,8,100,true);
	FarmerProductBean mockFarmerProduct2 = new FarmerProductBean("productId","kkdFarmId","imageUrl","productName","description",12,10,100,false);
	List<FarmerProductBean> mockList1 = new ArrayList<FarmerProductBean>();
	List<FarmerProductBean> mockList2 = new ArrayList<FarmerProductBean>();
	String FarmerProductJson1 = "{\"productId\":\"productId\",\"kkdFarmId\":\"kkdFarmId\",\"imageUrl\":\"imageUrl\",\"productName\":\"productName\",\"description\":\"description\",\"price\":10,\"bulkOrderPrice\":8,\"quantity\":100,\"availability\":true}";
	String FarmerProductJson2 = "{\"productId\":\"productId\",\"kkdFarmId\":\"kkdFarmId\",\"imageUrl\":\"imageUrl\",\"productName\":\"productName\",\"description\":\"description\",\"price\":12,\"bulkOrderPrice\":10,\"quantity\":100,\"availability\":false}";
	ResponseEntity<List<FarmerProductBean>> productEntities;
	ResponseEntity<FarmerProductBean>  productsEntity1 = new ResponseEntity<FarmerProductBean>(mockFarmerProduct1,HttpStatus.OK);
	ResponseEntity<FarmerProductBean>  productsEntity2 = new ResponseEntity<FarmerProductBean>(mockFarmerProduct2,HttpStatus.OK);
	ResponseEntity<Boolean> deleteStatus1 = new ResponseEntity<Boolean>(true,HttpStatus.ACCEPTED);
	ResponseEntity<Boolean> deleteStatus2 = new ResponseEntity<Boolean>(false,HttpStatus.ACCEPTED);
	Optional<FarmerProductBean> product1 = Optional.of(mockFarmerProduct1);
	Optional<FarmerProductBean> product2 = Optional.of(mockFarmerProduct2);
	ResponseEntity<Optional<FarmerProductBean>>  addedProduct1 = new ResponseEntity<Optional<FarmerProductBean>>(product1,HttpStatus.OK);
	ResponseEntity<Optional<FarmerProductBean>>  addedProduct2 = new ResponseEntity<Optional<FarmerProductBean>>(product2,HttpStatus.OK);
	
	/*                 POSITIVE TEST CASE FOR METHOD : viewProducts()  
	 * Method to test the viewProducts method
	 */@Test
	public void viewProductsTest1(){
        mockList1.add(mockFarmerProduct1);
		ResponseEntity<List<FarmerProductBean>> productEntities = new ResponseEntity<List<FarmerProductBean>>(mockList1,HttpStatus.OK);
		Mockito.when(
				proxy.getFarmerProduct(Mockito.anyString())).thenReturn(productEntities);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(
				"/farmer/1").accept(
						MediaType.APPLICATION_JSON);
		try {
			MvcResult result = mockMvc.perform(requestBuilder).andReturn();
			System.out.println(result.getResponse().getContentAsString());
			String expected =  "[{\"productId\":\"productId\",\"kkdFarmId\":\"kkdFarmId\",\"imageUrl\":\"imageUrl\",\"productName\":\"productName\",\"description\":\"description\",\"price\":10.0,\"bulkOrderPrice\":8.0,\"quantity\":100.0,\"availability\":true}]";
            assertEquals(expected, result.getResponse().getContentAsString());
            assertEquals(HttpStatus.OK.value(),result.getResponse().getStatus());
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
}
	
		/*                 NEGATIVE TEST CASE FOR METHOD : viewProducts()  
	 * Method to test the viewProducts method
	 */@Test
	public void viewProductsTest2(){
		mockList2.add(mockFarmerProduct2);
		ResponseEntity<List<FarmerProductBean>> productEntities = new ResponseEntity<List<FarmerProductBean>>(mockList2,HttpStatus.OK);
		Mockito.when(
				proxy.getFarmerProduct(Mockito.anyString())).thenReturn(productEntities);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(
				"/farmer/1").accept(
						MediaType.APPLICATION_JSON);
		try {
			MvcResult result = mockMvc.perform(requestBuilder).andReturn();
			System.out.println(result.getResponse().getContentAsString());
            assertEquals(HttpStatus.OK.value(),result.getResponse().getStatus());
            String expected =  "[{\"productId\":\"productId\",\"kkdFarmId\":\"kkdFarmId\",\"imageUrl\":\"imageUrl\",\"productName\":\"productName\",\"description\":\"description\",\"price\":10.0,\"bulkOrderPrice\":8.0,\"quantity\":100.0,\"availability\":true}]";
            assertNotEquals(expected, result.getResponse()
					.getContentAsString());
      }
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
}
	
	/*                 POSITIVE TEST CASE FOR METHOD : editProducts() 
	 * Method to test the editProducts method 
	 */	@Test
	public void editProductsTest1(){
		Mockito.when(
				proxy.updateFarmerProduct(Mockito.any(FarmerProductBean.class)))
		.thenReturn(productsEntity1);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.put(
				"/farmer/product").accept(
						MediaType.APPLICATION_JSON).content(FarmerProductJson1)
				                 .contentType(MediaType.APPLICATION_JSON);
		try {
			MvcResult result = mockMvc.perform(requestBuilder).andReturn();
			System.out.println(result.getResponse().getContentAsString());
			MockHttpServletResponse response = result.getResponse();
			assertEquals(HttpStatus.OK.value(), response.getStatus());
			String expected =  "{\"productId\":\"productId\",\"kkdFarmId\":\"kkdFarmId\",\"imageUrl\":\"imageUrl\",\"productName\":\"productName\",\"description\":\"description\",\"price\":10.0,\"bulkOrderPrice\":8.0,\"quantity\":100.0,\"availability\":true}";
            assertEquals(expected, result.getResponse()
					.getContentAsString());
            }
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
}
	
	/*                 NEGATIVE TEST CASE FOR METHOD : editProducts() 
	 * Method to test the editProducts method 
	 */@Test
	public void editProductsTest2(){
         Mockito.when(
				proxy.updateFarmerProduct(
						Mockito.any(FarmerProductBean.class))).thenReturn(productsEntity2);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.put(
				"/farmer/product").accept(
						MediaType.APPLICATION_JSON).content(FarmerProductJson2)
				                 .contentType(MediaType.APPLICATION_JSON);
		try {
        	MvcResult result = mockMvc.perform(requestBuilder).andReturn();
			MockHttpServletResponse response = result.getResponse();
			assertEquals(HttpStatus.OK.value(), response.getStatus());
			String expected =  "{\"productId\":\"productId\",\"kkdFarmId\":\"kkdFarmId\",\"imageUrl\":\"imageUrl\",\"productName\":\"productName\",\"description\":\"description\",\"price\":10.0,\"bulkOrderPrice\":8.0,\"quantity\":100.0,\"availability\":true}";
            assertNotEquals(expected, result.getResponse()
					.getContentAsString());
            }
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
     }
	
	/*                    POSITIVE TEST CASE FOR METHOD : deleteProducts() 
	 * Method to test the deleteProducts method
	 */@Test
	public void deleteProductsTest1(){
	    Mockito.when(
				proxy.deleteFarmerProduct(Mockito.anyString()))
                        .thenReturn(deleteStatus1);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.delete(
				"/farmer/product/1").accept(
						MediaType.APPLICATION_JSON);
		try {
            MvcResult result = mockMvc.perform(requestBuilder).andReturn();
			System.out.println(result.getResponse().getContentAsString());
			MockHttpServletResponse response = result.getResponse();
			assertEquals(HttpStatus.ACCEPTED.value(), response.getStatus());
			String expected = "true";
            assertEquals(expected, result.getResponse().getContentAsString());
            }
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
      }
	
    /*                      NEGATIVE TEST CASE FOR METHOD : deleteProducts() 
	 * Method to test the deleteProducts method
     */
	@Test
	public void deleteProductsTest2(){
	    Mockito.when(
				proxy.deleteFarmerProduct(Mockito.anyString()))
                        .thenReturn(deleteStatus2);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.delete(
				"/farmer/product/1").accept(
						MediaType.APPLICATION_JSON);
		try {
     		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
			System.out.println(result.getResponse().getContentAsString());
			MockHttpServletResponse response = result.getResponse();
			assertEquals(HttpStatus.ACCEPTED.value(), response.getStatus());
			String expected = "true";
            assertNotEquals(expected, result.getResponse().getContentAsString());
    	}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
      }
	
	/*                      POSITIVE TEST CASE FOR METHOD : addProduct()
	 * Method to test the addProduct function which adds a product for a 
	 * farmer(with farmer id :1) and returns a generated product id (product id is '1'
	 * in this case)
	 */@Test
	public void addProductTest1() throws Exception {
		Mockito.when(proxy.addFarmerProduct(
				Mockito.any(FarmerProductBean.class))).thenReturn(addedProduct1);
        RequestBuilder requestBuilder = MockMvcRequestBuilders.post(
				"/farmer/1").accept(
						MediaType.APPLICATION_JSON).content(FarmerProductJson1)
				.contentType(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();
		System.out.println(result.getResponse().getContentAsString());
        assertEquals(HttpStatus.OK.value(), response.getStatus());
		String expected =  "{\"productId\":\"productId\",\"kkdFarmId\":\"kkdFarmId\",\"imageUrl\":\"imageUrl\",\"productName\":\"productName\",\"description\":\"description\",\"price\":10.0,\"bulkOrderPrice\":8.0,\"quantity\":100.0,\"availability\":true}";
        assertEquals(expected,result.getResponse().getContentAsString());
	}

    /*                      NEGATIVE TEST CASE FOR METHOD : addProduct()
     * Method to test the addProduct function which adds a product for a 
	 * farmer(with farmer id :1) and returns a generated product id (product id is '2'
	 * in this case)
	 */@Test
	public void addProductTest2() throws Exception {
		Mockito.when(
				proxy.addFarmerProduct(
						Mockito.any(FarmerProductBean.class))).thenReturn(addedProduct2);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post(
				"/farmer/1").accept(
						MediaType.APPLICATION_JSON).content(FarmerProductJson2)
				.contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();
		System.out.println(result.getResponse().getStatus());
        assertEquals(HttpStatus.OK.value(), response.getStatus());
		String expected =  "{\"productId\":\"productId\",\"kkdFarmId\":\"kkdFarmId\",\"imageUrl\":\"imageUrl\",\"productName\":\"productName\",\"description\":\"description\",\"price\":10.0,\"bulkOrderPrice\":8.0,\"quantity\":100.0,\"availability\":true}";
        assertNotEquals(expected,result.getResponse().getContentAsString());
	}
}
