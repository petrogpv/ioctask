package ua.rd.domain;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ua.rd.config.RepoConfig;
import ua.rd.config.ServiceConfig;
import ua.rd.services.UserService;

@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(classes = {ServiceConfig.class, RepoConfig.class})
@ContextConfiguration(locations = { "classpath:config.xml" })

public class UserTest {

	@Autowired
	UserService userService;

	@Test
	public void addSubsciptionTest() {
		User subscriber = userService.newUser(null);
		User target = userService.newUser(null);
		assertTrue(subscriber.subscribe(target));
	}
	@Test
	public void addExistingSubsciptionTest() {
		User subscriber = userService.newUser(null);
		User target = userService.newUser(null);
		subscriber.subscribe(target);
		assertFalse(subscriber.subscribe(target));
	}

}
