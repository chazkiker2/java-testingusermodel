package com.lambdaschool.usermodel.services;


import com.lambdaschool.usermodel.UserModelApplication;
import com.lambdaschool.usermodel.models.Role;
import com.lambdaschool.usermodel.models.User;
import com.lambdaschool.usermodel.models.UserRoles;
import com.lambdaschool.usermodel.repository.RoleRepository;
import com.lambdaschool.usermodel.repository.UserRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes= UserModelApplication.class, properties = {"command.line.runner.enabled=false"})
public class UserServiceImplTest {
	@Autowired
	private UserService userService;

	@MockBean
	private UserRepository userRepo;

	@MockBean
	private RoleService roleService;

	@MockBean
	private UserAuditing userAuditing;

	private final List<User> userList      = new ArrayList<>();
	private final List<Role> roleList = new ArrayList<>();

	@Before
	public void setUp() {}

	@After
	public void tearDown() throws Exception {}



}
