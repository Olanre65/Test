package com.mytest.RestTest;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.net.http.HttpHeaders;
//import java.net.http.HttpHeaders;
import java.util.Arrays;

import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;

import com.mytest.RestTest.Model.Cake;


@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class RestTestApplicationIntegrationTest {
	 @LocalServerPort
	    private int port;
	
	@Autowired
	private TestRestTemplate Template;

	//private Object body;
	
	@Test
	public void contextLoads()throws JSONException  {
		String response = this.Template.getForObject("/cakes", String.class);
		JSONAssert.assertEquals("[{id:10001},{id:10002},{id:10003}]", 
				response, false);
	}
	
	@Test
	public void getCakeById() throws Exception{
		String response= this.Template.getForObject("/cakes/10001", String.class);
		JSONAssert.assertEquals("{id: 10001}", response, false);
		
	}
	
		
	 @Test
	    public void testAddCakewithnoauth() throws Exception {
	        Cake cake = new Cake ("carrot","other");
	        ResponseEntity<String> responseEntity = this.Template
	            .postForEntity("http://localhost:" + port + "/cakes", cake, String.class);
	            assertEquals(403, responseEntity.getStatusCodeValue());
	           //ected failure  because of Authentication config
	            }
	 
	 @Test 
	    public void  testaddCakeAuthentication() throws Exception{
		 
		// String url = "http://localhost:" + port + "/cakes";
		 String authname = "testUser";
	     String password = "password";
	     Cake cake = new Cake ("carrot","other");
	     ResponseEntity<String> responseEntity = this.Template
	        		.withBasicAuth(authname, password)
	        		.postForEntity("http://localhost:" + port + "/cakes", cake, String.class);
	         assertEquals(201,responseEntity.getStatusCodeValue());
	     
	     
	     
		 
	 }
}

     
