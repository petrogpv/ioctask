package ua.rd.domain;

import static org.junit.Assert.*;

import org.junit.Test;

public class UserTest {

	@Test
	public void addSubsciptionTest() {
		User subscriber = new User();
		User target = new User();
		assertTrue(subscriber.subscribe(target));
	}
	@Test
	public void addExistingSubsciptionTest() {
		User subscriber = new User();
		User target = new User();
		subscriber.subscribe(target);
		assertFalse(subscriber.subscribe(target));
	}

}
