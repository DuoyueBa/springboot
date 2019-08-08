package com.cct.server.main;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;

import com.cct.beans.User;

import io.restassured.RestAssured;
import io.restassured.response.Response;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { SpringBootRest1Application.class }, webEnvironment = WebEnvironment.DEFINED_PORT)
public class SpringBootRest1ApplicationTests {
	
	private static final String GET_URL = "http://localhost:8083/user/alluser";
	private static final String REGI_URL = "http://localhost:8083/register/user";
	private static final String UPDATE_URL = "http://localhost:8083/update/user";
	private static final String DELETE_URL = "http://localhost:8083/delete/user";

	@Test
	public void contextLoads() {
	}
	
	@Test
    public void whenCreateNewUser_thenCreated() {
        final User user = createUser();

        Response response = RestAssured.given()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(user)
            .post(REGI_URL);
        assertEquals(HttpStatus.CREATED.value(), response.getStatusCode());
        
        response = RestAssured.delete(DELETE_URL + "/" + user.getEmail());
        assertEquals(HttpStatus.OK.value(), response.getStatusCode());
    }

    @Test
    public void whenInvalidUser_thenError() {
    	final User user = createUser();
        user.setName(null);
        final Response response = RestAssured.given()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(user)
            .post(REGI_URL);
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatusCode());
    }

    @Test
    public void whenUpdateCreatedUser_thenUpdated() {
    	final User user = createUser();

        Response response = RestAssured.given()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(user)
            .post(REGI_URL);
        assertEquals(HttpStatus.CREATED.value(), response.getStatusCode());

        user.setAge(55);
        response = RestAssured.given()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(user)
            .put(UPDATE_URL);
        assertEquals(HttpStatus.OK.value(), response.getStatusCode());

        response = RestAssured.get(GET_URL);
        User[] ua = response.getBody().as(User[].class);
        assertEquals(HttpStatus.OK.value(), response.getStatusCode());
        assertEquals(55, ua[0].getAge());
        
        response = RestAssured.delete(DELETE_URL + "/" + user.getEmail());
        assertEquals(HttpStatus.OK.value(), response.getStatusCode());

    }

    @Test
    public void whenDeleteCreatedUser_thenOk() {
    	final User user = createUser();

        Response response = RestAssured.given()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(user)
            .post(REGI_URL);
        assertEquals(HttpStatus.CREATED.value(), response.getStatusCode());

        response = RestAssured.delete(DELETE_URL + "/" + user.getEmail());
        assertEquals(HttpStatus.OK.value(), response.getStatusCode());

        response = RestAssured.get(GET_URL);
        assertEquals(HttpStatus.OK.value(), response.getStatusCode());
        User[] ua = response.getBody().as(User[].class);
        assertEquals(0, ua.length);
    }

    // ===============================

    private User createUser() {
        final User user = new User();
        user.setAge(25);
        user.setEmail("user@163.net");
        user.setName("user");
        return user;
    }

}
