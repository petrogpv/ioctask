package ua.rd.services;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.BiPredicate;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ua.rd.config.RepoConfig;
import ua.rd.config.ServiceConfig;
import ua.rd.config.TimelineConfigurer;
import ua.rd.domain.*;
import ua.rd.repository.TweetRepository;
import ua.rd.repository.UserRepository;

@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(classes = {ServiceConfig.class, RepoConfig.class})
@ContextConfiguration(locations = { "classpath:config.xml" })
public class UserServiceTest {

	@Autowired
	UserService userService;
	@Autowired
	TweetService tweetService;

	@Test
	public void createNewUserTest() {
		UserRepository repo = mock(UserRepository.class);
		userService.setUserRepository(repo);
		String username="aaa";
		User actual = userService.newUser(username);
		assertEquals(username, actual.getName());
		
	}
	@Test
	public void saveNewUserTest() {
		UserRepository repo = mock(UserRepository.class);
		when(repo.save(any(User.class))).thenReturn(null);

		userService.setUserRepository(repo);
		String username="aaa";
		User newUser = userService.newUser(username);
		User overridedUser = userService.saveUser(newUser);
		assertNull(overridedUser);
		
	}
	@Test
	public void updateUserTest() {
		UserRepository repo = mock(UserRepository.class);
		String oldName="aaa";
		User oldUser = userService.newUser(oldName);
		
		when(repo.save(any(User.class))).then(new Answer<User>() {
			@Override
			public User answer(InvocationOnMock invocation) throws Throwable {
				return oldUser;
			}
		});
		userService.setUserRepository(repo);
		
		String newUsername="bbb";
		User updatedUser = userService.newUser(newUsername);
		updatedUser.setName(newUsername);
		
		User owerridedUser = userService.saveUser(updatedUser);
		
		assertEquals(oldUser, owerridedUser);
	}
	
	@Test
	public void addSubsciptionTest() {
		User target = userService.newUser(null);
		Long targetId = 1L;
		target.setId(targetId);
		
		UserRepository userRepository = mock(UserRepository.class);
		when(userRepository.save(any(User.class))).thenReturn(target);
		when(userRepository.get(targetId)).thenReturn(Optional.of(target));


		userService.setUserRepository(userRepository);
		User subscriber = new User();
		userService.createSubscription(subscriber,targetId);
		verify(userRepository, times(1)).save(subscriber);
		assertEquals(1, subscriber.getSubscriptions().size());
	}
	@Test
	public void addExistingSubsciptionTest() {
		User target = userService.newUser(null);
		Long targetId = 1L;
		target.setId(targetId);
		
		UserRepository userRepository = mock(UserRepository.class);
		when(userRepository.save(any(User.class))).thenReturn(target);
		when(userRepository.get(targetId)).thenReturn(Optional.of(target));
		UserService userService = new SimpleUserService(userRepository);
		
		
		User subscriber = new User();
		userService.createSubscription(subscriber,targetId);
		boolean result =userService.createSubscription(subscriber,targetId);
		verify(userRepository, times(1)).save(any(User.class));
		assertFalse(result);
	}

	@Test
	public void retweetTest() {
		List<Tweet> tweets = new ArrayList<>();

		User retweeter = userService.newUser("retweeter");
		tweets.add(tweetService.createTweet(retweeter));
		
		User retweetTarget = userService.newUser("retweetTarget");
		Long TweetId = 0L;
		Tweet tweet =  tweetService.createTweet(retweetTarget);
		tweet.setTweetId(TweetId);
		tweets.add(tweet);
		
		TweetRepository tweetRepository = mock(TweetRepository.class);
		when(tweetRepository.getTweet(TweetId)).thenReturn(Optional.of(tweet));
		
		UserRepository userRepository = mock(UserRepository.class);
		when(userRepository.save(any(User.class))).thenReturn(retweeter);
		UserService userService = new SimpleUserService(userRepository, tweetRepository);
		userService.retweet(retweeter, TweetId);
		verify(userRepository, times(1)).save(retweeter);
		assertEquals(1, retweeter.getRetweets().size());
		
	}
}
