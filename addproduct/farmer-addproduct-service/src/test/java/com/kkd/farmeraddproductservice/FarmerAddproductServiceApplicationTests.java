package com.kkd.farmeraddproductservice;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@RunWith(SpringRunner.class)
@WebMvcTest(value = FarmerAddproductServiceController.class, secure = false)
public class FarmerAddproductServiceApplicationTests {
	@Autowired
	private MockMvc mockMvc;
	@MockBean
	private FarmerAddproductServiceApplicationProxy proxy;
	private FarmerProduct farmerProduct;
    
    private FarmerProduct mockFarmerProduct = new FarmerProduct("productId","imageUrl","productName","description",10,8,100,true);
	String FarmerProductJson = "{\"productId\":\"productId\",\"imageUrl\":\"imageUrl\",\"productName\":\"productName\",\"description\":\"description\",\"price\":10,\"bulkOrderPrice\":8,\"quantity\":100,\"availability\":true}";

	@Test
	public void addProductTest() throws Exception {
		String id  = "farmer_id";
		Mockito.when(
				proxy.addProduct(Mockito.anyString(),
						Mockito.any(FarmerProduct.class))).thenReturn(id);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post(
				"/farmer/1/product").accept(
				MediaType.APPLICATION_JSON).content(FarmerProductJson)
				.contentType(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();
		assertEquals(HttpStatus.CREATED.value(), response.getStatus());
		String expected = "farmer_id";
		assertEquals(expected,response.getContentAsString());
	}

}
