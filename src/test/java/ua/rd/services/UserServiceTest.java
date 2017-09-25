package ua.rd.services;

import static org.junit.Assert.*;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Ignore;
import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import ua.rd.config.RepoConfig;
import ua.rd.config.ServiceConfig;
import ua.rd.domain.Profile;
import ua.rd.domain.Tweet;
import ua.rd.domain.User;
import ua.rd.repository.TweetRepository;
import ua.rd.repository.UserRepository;

public class UserServiceTest {

	@Test
	public void createNewUserTest() {
		UserRepository repo = mock(UserRepository.class);
		UserService userService = new SimpleUserService(repo);
		String username="aaa";
		User actual = userService.createNewUser(username);
		assertEquals(username, actual.getName());
		
	}
	@Test
	public void saveNewUserTest() {
		UserRepository repo = mock(UserRepository.class);
		when(repo.save(any(User.class))).thenReturn(null);
		
		UserService userService = new SimpleUserService(repo);
		String username="aaa";
		User newUser = userService.createNewUser(username);
		User overridedUser = userService.saveUser(newUser);
		assertNull(overridedUser);
		
	}
	@Test
	public void updateUserTest() {
		UserRepository repo = mock(UserRepository.class);
		
		String oldName="aaa";
		User oldUser = new User(oldName);
		
		when(repo.save(any(User.class))).then(new Answer<User>() {
			@Override
			public User answer(InvocationOnMock invocation) throws Throwable {
				return oldUser;
			}
		});
		UserService userService = new SimpleUserService(repo);
		
		String newUsername="bbb";
		User updatedUser = userService.createNewUser(newUsername);
		updatedUser.setName(newUsername);
		
		User owerridedUser = userService.saveUser(updatedUser);
		
		assertEquals(oldUser, owerridedUser);
	}
	
	@Test
	public void addSubsciptionTest() {
		User target = new User();
		target.setId(1L);
		
		UserRepository userRepository = mock(UserRepository.class);
		when(userRepository.save(any(User.class))).thenReturn(target);
		
		UserService userService = new SimpleUserService(userRepository);
		User subscriber = new User();
		userService.createSubscription(subscriber,1L);
		verify(userRepository, times(1)).save(subscriber);
		assertEquals(1, subscriber.getSubscriptions().size());
	}
	@Test
	public void addExistingSubsciptionTest() {
		User target = new User();
		target.setId(1L);
		
		UserRepository userRepository = mock(UserRepository.class);
		when(userRepository.save(any(User.class))).thenReturn(target);
		UserService userService = new SimpleUserService(userRepository);
		
		User subscriber = new User();
		userService.createSubscription(subscriber,1L);
		boolean result =userService.createSubscription(subscriber,1L);
		verify(userRepository, times(1)).save(any(User.class));
		assertFalse(result);
	}

	@Test
	public void retweetTest() {
		List<Tweet> tweets = new ArrayList<>();

		User retweeter = new User("retweeter");
		tweets.add(new Tweet("", retweeter));
		
		User retweetTarget = new User("retweetTarget");
		Long TweetId = 0L;
		Tweet tweet =  new Tweet("", retweetTarget);
		tweet.setTweetId(TweetId);
		tweets.add(tweet);
		
		TweetRepository tweetRepository = mock(TweetRepository.class);
		when(tweetRepository.getTweet(TweetId)).thenReturn(Optional.of(tweet));
		
		UserRepository userRepository = mock(UserRepository.class);
		when(userRepository.save(any(User.class))).thenReturn(retweeter);
		UserService userService = new SimpleUserService(userRepository,tweetRepository);
		userService.retweet(retweeter, TweetId);
		verify(userRepository, times(1)).save(retweeter);
		assertEquals(1, retweeter.getRetweets().size());
		
	}
}
