package com.gces.budget;

import com.gces.budget.domain.dto.UserDTO;
import com.gces.budget.domain.entity.User;
import com.gces.budget.repository.UserRepository;
import com.gces.budget.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import static org.junit.Assert.*;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = BudgetVisualizationApplication.class)
@WebAppConfiguration
public class BudgetVisualizationApplicationTests {

	private final Logger log = LoggerFactory.getLogger(BudgetVisualizationApplication.class);

	@Autowired
	private UserService userService;

	@Autowired
	private UserRepository userRepository;

	@Test
	public void contextLoads() {
	}

	@Test
	public void createUser() {
		UserDTO newUser = new UserDTO("admin", "budget", "np.msushil@gmail.com");
		log.info("UserDTO object created {}", newUser);
		User user = userService.registerNewUser(newUser);
		log.info("User registered {}", user);

		assertEquals(newUser.getUsername(), user.getUsername());

		assertFalse(newUser.getPassword().equalsIgnoreCase(user.getPassword()));

	}

	@Test
	public void findByUsername() {
		User user = userRepository.findOneByUsername("msushil");
		assertNotNull(user);
		log.info("user fetched : {}", user);
		assertTrue(user.getUsername().equalsIgnoreCase("msushil"));
	}

	@Test
	public void passwordEncryptTest(){
		String org = "password";
		log.info("Raw Data : {}",org);
		PasswordEncoder encoder = new BCryptPasswordEncoder();

		String encrypt = encoder.encode(org);
		log.info("Encrypt Data : {}",encrypt);

		assertTrue(encoder.matches(org,encrypt));


	}

}
