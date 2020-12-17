package com.lambdaschool.usermodel.controllers;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.lambdaschool.usermodel.UserModelApplication;
import com.lambdaschool.usermodel.models.Role;
import com.lambdaschool.usermodel.models.User;
import com.lambdaschool.usermodel.models.UserRoles;
import com.lambdaschool.usermodel.models.Useremail;
import com.lambdaschool.usermodel.services.UserService;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT,
                classes = UserModelApplication.class,
                properties = {"command.line.runner.enabled=false"})
@AutoConfigureMockMvc
public class UserControllerTest {

	@Autowired
	private WebApplicationContext webApplicationContext;
	private MockMvc               mockMvc;

	@MockBean
	private UserService userService;
	private List<User>  userList;

	@Before
	public void setUp()
			throws
			Exception {
		userList = new ArrayList<>();

		//		userService.deleteAll();
		//		roleService.deleteAll();
		Role r1 = new Role("admin");
		r1.setRoleid(1);
		Role r2 = new Role("user");
		r2.setRoleid(2);
		Role r3 = new Role("data");
		r3.setRoleid(3);

		//		r1 = roleService.save(r1);
		//		r2 = roleService.save(r2);
		//		r3 = roleService.save(r3);


		// admin, data, user
		User u1 = new User(
				"admin",
				"password",
				"admin@lambdaschool.local"
		);
		u1.setUserid(11);

		u1.getRoles()
		  .add(new UserRoles(
				  u1,
				  r1
		  ));
		u1.getRoles()
		  .add(new UserRoles(
				  u1,
				  r2
		  ));
		u1.getRoles()
		  .add(new UserRoles(
				  u1,
				  r3
		  ));
		u1.getUseremails()
		  .add(new Useremail(
				  u1,
				  "admin@email.local"
		  ));
		u1.getUseremails()
		  .add(new Useremail(
				  u1,
				  "admin@mymail.local"
		  ));

		//		userService.save(u1);
		userList.add(u1);

		// data, user
		User u2 = new User(
				"cinnamon",
				"1234567",
				"cinnamon@lambdaschool.local"
		);

		u2.setUserid(22);

		u2.getRoles()
		  .add(new UserRoles(
				  u2,
				  r2
		  ));
		u2.getRoles()
		  .add(new UserRoles(
				  u2,
				  r3
		  ));
		u2.getUseremails()
		  .add(new Useremail(
				  u2,
				  "cinnamon@mymail.local"
		  ));
		u2.getUseremails()
		  .add(new Useremail(
				  u2,
				  "hops@mymail.local"
		  ));
		u2.getUseremails()
		  .add(new Useremail(
				  u2,
				  "bunny@email.local"
		  ));
		//		userService.save(u2);
		userList.add(u2);

		// user
		User u3 = new User(
				"barnbarn",
				"ILuvM4th!",
				"barnbarn@lambdaschool.local"
		);
		u3.setUserid(33);
		u3.getRoles()
		  .add(new UserRoles(
				  u3,
				  r2
		  ));
		u3.getUseremails()
		  .add(new Useremail(
				  u3,
				  "barnbarn@email.local"
		  ));
		//		userService.save(u3);
		userList.add(u3);

		RestAssuredMockMvc.webAppContextSetup(webApplicationContext);
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
		                         .build();
	}

	@After
	public void tearDown()
			throws
			Exception {}

	@Test
	public void listAllUsers()
			throws
			Exception {
		String apiUrl = "/users/users/";
		Mockito.when(userService.findAll())
		       .thenReturn(userList);
		RequestBuilder rb     = MockMvcRequestBuilders.get(apiUrl)
		                                              .accept(MediaType.APPLICATION_JSON);
		MvcResult      r      = mockMvc.perform(rb)
		                               .andReturn();
		String         tr     = r.getResponse()
		                         .getContentAsString();
		ObjectMapper   mapper = new ObjectMapper();
		String         er     = mapper.writeValueAsString(userList);

		System.out.println(er);
		assertEquals(er, tr);
	}

//	@Test
//	public void getUserById()
//			throws
//			Exception {
//		String apiUrl = "/users/user/3";
//
//	}

	public void getUserByIdNotFound()
			throws
			Exception {}

	public void getUserByName()
			throws
			Exception {}

	public void getUserByNameNotFound()
			throws
			Exception {}

	public void getUserLikeName()
			throws
			Exception {}

	public void addNewUser()
			throws
			Exception {}

	public void updateFullUser()
			throws
			Exception {}

	public void updateUser()
			throws
			Exception {}

	public void deleteUserById()
			throws
			Exception {}


}
