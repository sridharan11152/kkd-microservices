package com.kkd.productmanagementservice;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import org.json.JSONException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.kkd.productmanagementservice.model.FarmerProductBean;
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProductManagementServiceApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProductManagementServiceApplicationIntegrationTests {
	
	@LocalServerPort
	private int port;
	TestRestTemplate restTemplate = new TestRestTemplate();
    HttpHeaders headers = new HttpHeaders();
/*
 * Positive test case for viewProducts method
 */@Test
	public void testViewProducts1() throws JSONException {
		HttpEntity<String> entity = new HttpEntity<String>(null, headers);
        ResponseEntity<String> response = restTemplate.exchange(
				createURLWithPort("farmer/kkdfarm04"),
				HttpMethod.GET, entity,String.class);
    String expected =  "[{\"productId\":\"P2020\",\"kkdFarmId\":\"kkdfarm04\","
			           + "\"imageUrl\":\"http://www.alansfruitandveg.co.uk/wp-content"
			           + "/uploads/2016/11/new-potatoes.jpg\",\"productName\":\"Onions\","
			           + "\"description\":\"They are very good.\",\"price\":45.0,"
			           + "\"bulkOrderPrice\":9.5,\"quantity\":50.0,\"availability\":false},"
			           + "{\"productId\":\"\",\"kkdFarmId\":\"kkdfarm04\","
			           + "\"imageUrl\":\"http://www.alansfruitandveg.co.uk/wp-content/uploads"
			           + "/2016/11/new-potatoes.jpg\",\"productName\":\"potatoes\","
			           + "\"description\":\"They are very good.\",\"price\":45.0,"
			           + "\"bulkOrderPrice\":9.5,\"quantity\":50.0,\"availability\":false},"
			           + "{\"productId\":\"kkdprod2001\",\"kkdFarmId\":\"kkdfarm04\","
			           + "\"imageUrl\":\"http://www.alansfruitandveg.co.uk/wp-content/uploads"
			           + "/2016/11/new-potatoes.jpg\",\"productName\":\"cucumber\","
			           + "\"description\":\"They are very good.\",\"price\":45.0,"
			           + "\"bulkOrderPrice\":9.5,\"quantity\":50.0,\"availability\":false}]";  	                   
    System.out.println(response.getBody());
    assertEquals(expected, response.getBody());
    assertEquals(HttpStatus.OK.value(), response.getStatusCodeValue());
	}
	
	/*
	 * Negative test case for viewProducts method
	 */@Test
	public void testViewProducts2() throws JSONException {
		HttpEntity<String> entity = new HttpEntity<String>(null, headers);
        ResponseEntity<String> response = restTemplate.exchange(
				createURLWithPort("farmer/kkdfarm04"),
				HttpMethod.GET, entity,String.class);
    String expected =  "[{\"productId\":\"P2020\",\"kkdFarmId\":\"kkdfarm04\","
			           + "\"imageUrl\":\"http://www.alansfruitandveg.co.uk/wp-content"
			           + "/uploads/2016/11/new-potatoes.jpg\",\"productName\":\"cucumber\","
			           + "\"description\":\"They are very good.\",\"price\":45.0,"
			           + "\"bulkOrderPrice\":9.5,\"quantity\":50.0,\"availability\":false},"
			           + "{\"productId\":\"\",\"kkdFarmId\":\"kkdfarm04\","
			           + "\"imageUrl\":\"http://www.alansfruitandveg.co.uk/wp-content/uploads"
			           + "/2016/11/new-potatoes.jpg\",\"productName\":\"potatoes\","
			           + "\"description\":\"They are very good.\",\"price\":45.0,"
			           + "\"bulkOrderPrice\":9.5,\"quantity\":50.0,\"availability\":false},"
			           + "{\"productId\":\"kkdprod2001\",\"kkdFarmId\":\"kkdfarm04\","
			           + "\"imageUrl\":\"http://www.alansfruitandveg.co.uk/wp-content/uploads"
			           + "/2016/11/new-potatoes.jpg\",\"productName\":\"cucumber\","
			           + "\"description\":\"They are very good.\",\"price\":45.0,"
			           + "\"bulkOrderPrice\":9.5,\"quantity\":50.0,\"availability\":false}]";  	                   
    System.out.println(response.getBody());
    assertNotEquals(expected, response.getBody());
    assertEquals(HttpStatus.OK.value(), response.getStatusCodeValue());
	}
	
	/*
	 * Positive test case for editProducts method
	 */@Test
	public void testEditProducts1() throws JSONException {
		FarmerProductBean mockFarmerProduct1 = new FarmerProductBean("kkdprod2001","kkdfarm04","http://www.alansfruitandveg.co.uk/wp-content/uploads/2016/11/new-potatoes.jpg","guava","They are very good.",45.0,9.5,50.0,false);
        HttpEntity<FarmerProductBean> entity = new HttpEntity<FarmerProductBean>(mockFarmerProduct1, headers);
        ResponseEntity<String> response = restTemplate.exchange(
				createURLWithPort("/farmer/product"),
				HttpMethod.PUT, entity, String.class);
        String expected = "{\"productId\":\"kkdprod2001\",\"kkdFarmId\":\"kkdfarm04\","
        + "\"imageUrl\":\"http://www.alansfruitandveg.co.uk/wp-content/uploads"
        + "/2016/11/new-potatoes.jpg\",\"productName\":\"guava\","
        + "\"description\":\"They are very good.\",\"price\":45.0,"
        + "\"bulkOrderPrice\":9.5,\"quantity\":50.0,\"availability\":false}";
        assertEquals(expected, response.getBody());
        assertEquals(HttpStatus.ACCEPTED.value(), response.getStatusCodeValue());
	}
	
	/*
	 * Negative test case for editProducts method
	 */@Test
	public void testEditProducts2() throws JSONException {
		FarmerProductBean mockFarmerProduct1 = new FarmerProductBean("kkdprod2001","kkdfarm04","http://www.alansfruitandveg.co.uk/wp-content/uploads/2016/11/new-potatoes.jpg","guava","They are very good.",45.0,9.5,50.0,false);
        HttpEntity<FarmerProductBean> entity = new HttpEntity<FarmerProductBean>(mockFarmerProduct1, headers);
        ResponseEntity<String> response = restTemplate.exchange(
				createURLWithPort("/farmer/product"),
				HttpMethod.PUT, entity, String.class);
        String expected = "{\"productId\":\"kkdprod2001\",\"kkdFarmId\":\"kkdfarm04\","
        + "\"imageUrl\":\"http://www.alansfruitandveg.co.uk/wp-content/uploads"
        + "/2016/11/new-potatoes.jpg\",\"productName\":\"mango\","
        + "\"description\":\"They are very good.\",\"price\":45.0,"
        + "\"bulkOrderPrice\":9.5,\"quantity\":50.0,\"availability\":false}";
        assertNotEquals(expected, response.getBody());
        assertEquals(HttpStatus.ACCEPTED.value(), response.getStatusCodeValue());
	}
	
	/*
	 *Positive test case for addProducts method 
	 */@Test
	public void testAddProducts1() throws JSONException {
		FarmerProductBean mockFarmerProduct1 = new FarmerProductBean("kkdprod2002","kkdfarm04","http://www.alansfruitandveg.co.uk/wp-content/uploads/2016/11/new-potatoes.jpg","tomatoes","They are very good.",45.0,9.5,50.0,false);
        HttpEntity<FarmerProductBean> entity = new HttpEntity<FarmerProductBean>(mockFarmerProduct1, headers);
        ResponseEntity<String> response = restTemplate.exchange(
				createURLWithPort("/farmer/kkdfarm04"),
				HttpMethod.POST, entity, String.class);
        String expected = "{\"productId\":\"kkdprod2002\",\"kkdFarmId\":\"kkdfarm04\","
        + "\"imageUrl\":\"http://www.alansfruitandveg.co.uk/wp-content/uploads"
        + "/2016/11/new-potatoes.jpg\",\"productName\":\"tomatoes\","
        + "\"description\":\"They are very good.\",\"price\":45.0,"
        + "\"bulkOrderPrice\":9.5,\"quantity\":50.0,\"availability\":false}";
        assertEquals(expected, response.getBody());
        assertEquals(HttpStatus.CREATED.value(), response.getStatusCodeValue());
	}
	
	/*
	 *Negative test case for addProducts method 
	 */@Test
	public void testAddProducts2() throws JSONException {
		FarmerProductBean mockFarmerProduct1 = new FarmerProductBean("kkdprod2002","kkdfarm04","http://www.alansfruitandveg.co.uk/wp-content/uploads/2016/11/new-potatoes.jpg","tomatoes","They are very good.",45.0,9.5,50.0,false);
        HttpEntity<FarmerProductBean> entity = new HttpEntity<FarmerProductBean>(mockFarmerProduct1, headers);
        ResponseEntity<String> response = restTemplate.exchange(
				createURLWithPort("/farmer/kkdfarm04"),
				HttpMethod.POST, entity, String.class);
        String expected = "{\"productId\":\"kkdprod2001\",\"kkdFarmId\":\"kkdfarm04\","
        + "\"imageUrl\":\"http://www.alansfruitandveg.co.uk/wp-content/uploads"
        + "/2016/11/new-potatoes.jpg\",\"productName\":\"tomatoes\","
        + "\"description\":\"They are very good.\",\"price\":45.0,"
        + "\"bulkOrderPrice\":9.5,\"quantity\":50.0,\"availability\":false}";
        assertNotEquals(expected, response.getBody());
        assertNotEquals(HttpStatus.BAD_REQUEST.value(), response.getStatusCodeValue());
	}
	
	/*
	 *Positive test case for addProducts method 
	 */@Test
	public void testDeleteProducts1() throws JSONException {
        HttpEntity<Boolean> entity = new HttpEntity<Boolean>(true, headers);
        ResponseEntity<String> response = restTemplate.exchange(
				createURLWithPort("/farmer/product/kkdprod2002"),
				HttpMethod.DELETE, entity, String.class);
        String expected = "true";
        assertEquals(expected, response.getBody());
        assertEquals(HttpStatus.ACCEPTED.value(), response.getStatusCodeValue());
	}
	
	/*
	 *Negative test case for deleteProducts method 
	 */@Test
	public void testDeleteProducts2() throws JSONException {
        HttpEntity<Boolean> entity = new HttpEntity<Boolean>(true, headers);
        ResponseEntity<String> response = restTemplate.exchange(
				createURLWithPort("/farmer/product/kkdprod2002"),
				HttpMethod.DELETE, entity, String.class);
        String expected = "false";
        assertNotEquals(expected, response.getBody());
        assertEquals(HttpStatus.ACCEPTED.value(), response.getStatusCodeValue());
	}
	private String createURLWithPort(String uri) {
		return "http://localhost:" + port + uri;
	}

}
