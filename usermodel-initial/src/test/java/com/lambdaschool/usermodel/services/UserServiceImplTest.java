package com.lambdaschool.usermodel.services;


import com.lambdaschool.usermodel.UserModelApplication;
import com.lambdaschool.usermodel.exceptions.ResourceNotFoundException;
import com.lambdaschool.usermodel.models.Role;
import com.lambdaschool.usermodel.models.User;
import com.lambdaschool.usermodel.models.UserRoles;
import com.lambdaschool.usermodel.models.Useremail;
import com.lambdaschool.usermodel.repository.RoleRepository;
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
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;


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
	private RoleRepository roleRepo;
	@MockBean
	private UserAuditing   userAuditing;

	@Before
	public void setUp() {
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

	@Test(expected = ResourceNotFoundException.class)
	public void findUserByIdNotFound() {
		Mockito.when(userRepo.findById(4L))
		       .thenReturn(Optional.empty());
		assertEquals(
				"admin",
				userService.findUserById(4)
				           .getUsername()
		);
	}

	@Test
	public void findByNameContaining() {
		Mockito.when(userRepo.findByUsernameContainingIgnoreCase("admin"))
		       .thenReturn(userList);
		//		System.out.println(userService.findByNameContaining("admin"));
		assertEquals(
				3,
				userService.findByNameContaining("admin")
				           .size()
		);
	}

	@Test
	public void findByNameContainingNoSuchName() {
		Mockito.when(userRepo.findByUsernameContainingIgnoreCase("zxoinasodfihgoiweh"))
		       .thenReturn(new ArrayList<>());
		assertEquals(
				0,
				userService.findByNameContaining("zxoinasodfihgoiweh")
				           .size()
		);
	}

	@Test
	public void findAll() {
		Mockito.when(userRepo.findAll())
		       .thenReturn(userList);
		assertEquals(
				3,
				userService.findAll()
				           .size()
		);
	}

	@Test
	public void findByName() {
		Mockito.when(userRepo.findByUsername("admin"))
		       .thenReturn(userList.get(0));
		assertEquals(
				"admin",
				userService.findByName("admin")
				           .getUsername()
		);
	}

	@Test(expected = ResourceNotFoundException.class)
	public void findByNameNotFound() {
		Mockito.when(userRepo.findByUsername("zxoinasodfihgoiweh"))
		       .thenReturn(null);
		assertEquals(
				"zxoinasodfihgoiweh",
				userService.findByName("zxoinasodfihgoiweh")
				           .getUsername()
		);
	}

	@Test
	public void delete() {
		Mockito.when(userRepo.findById(4L))
		       .thenReturn(Optional.of(userList.get(0)));
		Mockito.doNothing()
		       .when(userRepo)
		       .deleteById(4L);
		userService.delete(4);
		assertEquals(
				3,
				userList.size()
		);
	}

	@Test(expected = ResourceNotFoundException.class)
	public void deleteFailed() {
		Mockito.when(userRepo.findById(777L))
		       .thenReturn(Optional.empty());
		Mockito.doNothing()
		       .when(userRepo)
		       .deleteById(777L);
		userService.delete(777);
		assertEquals(
				3,
				userList.size()
		);
	}

	@Test
	public void save() {
		Role r3 = new Role("data");
		r3.setRoleid(3);

		String u3Name = "barnbarn";

		User u3 = new User(
				u3Name,
				"ILuvM4th!",
				"barnbarn@lambdaschool.local"
		);
		//		u3.setUserid(33);
		u3.getRoles()
		  .add(new UserRoles(
				  u3,
				  r3
		  ));
		u3.getUseremails()
		  .add(new Useremail(
				  u3,
				  "barnbarn@email.local"
		  ));

		Mockito.when(userRepo.save(any(User.class)))
		       .thenReturn(u3);
		Mockito.when(roleRepo.findById(3L))
		       .thenReturn(Optional.of(r3));
		Mockito.when(roleService.findRoleById(3))
		       .thenReturn(r3);

		User addedUser = userService.save(u3);
		assertNotNull(addedUser);
		assertEquals(
				u3Name,
				addedUser.getUsername()
		);

	}

	//	@Test
	@Test(expected = ResourceNotFoundException.class)
	public void saveRoleNotFound() {
		Role r3 = new Role("data");
		r3.setRoleid(3333);

		String u3Name = "barnbarn";

		User u3 = new User(
				u3Name,
				"ILuvM4th!",
				"barnbarn@lambdaschool.local"
		);
		//		u3.setUserid(33);
		u3.getRoles()
		  .add(new UserRoles(
				  u3,
				  r3
		  ));
		u3.getUseremails()
		  .add(new Useremail(
				  u3,
				  "barnbarn@email.local"
		  ));

		Mockito.when(userRepo.save(any(User.class)))
		       .thenReturn(u3);
		Mockito.when(roleRepo.findById(3333L))
		       .thenReturn(Optional.empty());
		Mockito.when(roleService.findRoleById(3333))
		       .thenThrow(ResourceNotFoundException.class);

		User addedUser = userService.save(u3);
		assertNotNull(addedUser);
		assertEquals(
				u3Name,
				addedUser.getUsername()
		);
	}

	@Test
	public void savePut() {
		Role r3 = new Role("data");
		r3.setRoleid(3);

		String u3Name = "barnbarn";

		User u3 = new User(
				u3Name,
				"ILuvM4th!",
				"barnbarn@lambdaschool.local"
		);
		u3.setUserid(33);
		u3.getRoles()
		  .add(new UserRoles(
				  u3,
				  r3
		  ));
		u3.getUseremails()
		  .add(new Useremail(
				  u3,
				  "barnbarn@email.local"
		  ));

		Mockito.when(userRepo.findById(33L))
		       .thenReturn(Optional.of(u3));
		Mockito.when(roleRepo.findById(3L))
		       .thenReturn(Optional.of(r3));
		Mockito.when(roleService.findRoleById(3))
		       .thenReturn(r3);

		Mockito.when(userRepo.save(any(User.class)))
		       .thenReturn(u3);

		User updatedUser = userService.save(u3);
		assertNotNull(updatedUser);
		assertEquals(
				u3Name,
				updatedUser.getUsername()
		);
	}

	@Test(expected = ResourceNotFoundException.class)
	public void savePutFail() {
		Role r3 = new Role("data");
		r3.setRoleid(3);

		String u3Name = "barnbarn";

		User u3 = new User(
				u3Name,
				"ILuvM4th!",
				"barnbarn@lambdaschool.local"
		);
		u3.setUserid(777);
		u3.getRoles()
		  .add(new UserRoles(
				  u3,
				  r3
		  ));
		u3.getUseremails()
		  .add(new Useremail(
				  u3,
				  "barnbarn@email.local"
		  ));

		Mockito.when(userRepo.findById(777L))
		       .thenReturn(Optional.empty());
		Mockito.when(roleRepo.findById(3L))
		       .thenReturn(Optional.of(r3));
		Mockito.when(roleService.findRoleById(3))
		       .thenReturn(r3);

		Mockito.when(userRepo.save(any(User.class)))
		       .thenReturn(u3);

		User updatedUser = userService.save(u3);
		assertNotNull(updatedUser);
		assertEquals(
				777L,
				updatedUser.getUserid()
		);
	}

//		@Test
//		public void update() {
//			Role r3 = new Role("data");
//			r3.setRoleid(3);
//
//			String u3Name = "barnbarn";
//
//			User u3 = new User(
//					u3Name,
//					"ILuvM4th!",
//					"barnbarn@lambdaschool.local"
//			);
//
//			u3.setUserid(33);
//
//			u3.getRoles()
//			  .add(new UserRoles(
//					  u3,
//					  r3
//			  ));
//			u3.getUseremails()
//			  .add(new Useremail(
//					  u3,
//					  "barnbarn@email.local"
//			  ));
//		}

	//	@Test(expected = ResourceNotFoundException.class)
	//	public void updateUserNotFound() {}
	//
	//	@Test(expected = ResourceNotFoundException.class)
	//	public void updateRoleNotFound() {}


}
