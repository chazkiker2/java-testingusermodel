package com.lambdaschool.usermodel.services;


import com.lambdaschool.usermodel.UserModelApplication;
import com.lambdaschool.usermodel.models.Role;
import com.lambdaschool.usermodel.models.User;
import com.lambdaschool.usermodel.models.UserRoles;
import com.lambdaschool.usermodel.models.Useremail;
import com.lambdaschool.usermodel.repository.UserRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = UserModelApplication.class,
                properties = {"command.line.runner.enabled=false"})
public class UserServiceImplTest {

	private final List<User> userList = new ArrayList<>();
	private final List<Role> roleList = new ArrayList<>();

	@Autowired
	private UserService    userService;
	@MockBean
	private RoleService    roleService;
	@MockBean
	private UserRepository userRepo;
	@MockBean
	private UserAuditing   userAuditing;

	@Before
	public void setUp() {
		//		userService.deleteAll();
		//		roleService.deleteAll();
		Role r1 = new Role("admin");
		Role r2 = new Role("user");
		Role r3 = new Role("data");

		//		r1 = roleService.save(r1);
		//		r2 = roleService.save(r2);
		//		r3 = roleService.save(r3);
		r1.setRoleid(1);
		r2.setRoleid(2);
		r3.setRoleid(3);


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

		MockitoAnnotations.initMocks(this);

	}

	@After
	public void tearDown()
			throws
			Exception {
	}

	@Test
	public void findUserById() {
		Mockito.when(userRepo.findById(4L))
		       .thenReturn(Optional.of(userList.get(0)));
		assertEquals(
				"admin",
				userService.findUserById(4)
				           .getUsername()
		);
	}

}
