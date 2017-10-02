package ua.rd.services;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.AdditionalAnswers.*;
import static org.mockito.Matchers.any;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Test;

import org.junit.runner.RunWith;
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
public class TweetServiceTest {

	@Autowired
	TweetService tweetService;
	@Autowired
	UserService userService;
	@Autowired
	TimelineConfigurer timelineConfigurer;

	@Test
	public void getUserTweetsTest() {
		User user = userService.newUser("aaa");
		List<Tweet> tweets = new ArrayList<>();
		tweets.add(tweetService.createTweet(user));
		tweets.add(tweetService.createTweet(user));
		tweets.add(tweetService.createTweet(user));
		User otherUser = userService.newUser("bbb");
		tweets.add(tweetService.createTweet(otherUser));

		TweetRepository tweetRepository = mock(TweetRepository.class);
		TweetService service = new SimpleTweetService(tweetRepository, timelineConfigurer);
		when(tweetRepository.allTweets()).thenReturn(tweets);
		Iterable<Tweet> result = service.allUsersTweets(user);
		int itemCounter = 0;
		for (Tweet t : result) {
			itemCounter++;
		}
		assertEquals(3, itemCounter);
	}

	@Test
	public void getUserTimelineTest() {
		User user = userService.newUser("user");
		List<Tweet> tweets = new ArrayList<>();
		tweets.add(tweetService.createTweet(user));
		tweets.add(tweetService.createTweet(user));
		tweets.add(tweetService.createTweet(user));


		User otherUser = userService.newUser("otherUser");
		tweets.add(tweetService.createTweet(otherUser));

		user.subscribe(otherUser);
		
		User anotherUser = userService.newUser("anotherUser");
		Tweet tweet = tweetService.createTweet(anotherUser);
		tweet.setTweetId(4L);
		tweets.add(tweet);
		
		otherUser.setRetweet(tweet);
		
		User leftUser = userService.newUser("leftUser");
		tweets.add(tweetService.createTweet(leftUser));
		tweets.add(tweetService.createTweet(leftUser));
		tweets.add(tweetService.createTweet(leftUser));


		TweetRepository tweetRepository = mock(TweetRepository.class);
		TweetService service = new SimpleTweetService(tweetRepository, timelineConfigurer);
		when(tweetRepository.allTweets()).thenReturn(tweets);
		Iterable<Tweet> result = service.userTimeline(user);
		int itemCounter = 0;
		for (Tweet t : result) {
			System.out.println(t);
			itemCounter++;
		}
		assertEquals(5, itemCounter);
	}

	@Test
	public void likeTweetTest() {

		Tweet tweet =tweetService.createTweet(userService.newUser(null));

		TweetRepository tweetRepository = mock(TweetRepository.class);
		when(tweetRepository.getTweet(0L)).thenReturn(Optional.of(tweet));

		TweetService service = new SimpleTweetService(tweetRepository, timelineConfigurer);
		Long tweetId = 0L;
		long oldLikeCount = tweet.getLikeCounter();
		
		service.likeTweet(tweetId);
		
		long newLikeCount = tweet.getLikeCounter();
		long diff = newLikeCount - oldLikeCount;
		assertEquals(1, diff);
	}

	@Test
	public void createTweetTest() {
		User user = new User("aaa");
		TweetRepository tweetRepository = mock(TweetRepository.class);
		when(tweetRepository.save(any(Tweet.class))).then(returnsFirstArg());
		tweetService.setTweetRepository(tweetRepository);
		Tweet tweet = tweetService.createTweet(user);
		assertEquals(user, tweet.getUser());
	}
	
	@Test
	public void retweetCounterTest() {
		List<Tweet> tweets = new ArrayList<>();

		User retweeter = userService.newUser("retweeter");
		
		User retweetTarget = userService.newUser("retweetTarget");
		Long TweetId = 0L;
		Tweet tweet =  tweetService.createTweet(retweetTarget);
		tweet.setTweetId(TweetId);
		tweets.add(tweet);
		
		TweetRepository tweetRepository = mock(TweetRepository.class);
		when(tweetRepository.getTweet(TweetId)).thenReturn(Optional.of(tweet));
		
		UserRepository userRepository = mock(UserRepository.class);
		UserService userService = new SimpleUserService(userRepository, tweetRepository);
		userService.retweet(retweeter, TweetId);
		assertEquals(1, tweet.getRetweetCounter());
		
	}
	@Test
	public void repeatedRetweetCounterTest() {

		List<Tweet> tweets = new ArrayList<>();

		User retweeter = new User("retweeter");
		
		User retweetTarget = new User("retweetTarget");
		Long TweetId = 0L;
		Tweet tweet =  tweetService.createTweet(retweetTarget);
		tweet.setTweetId(TweetId);
		tweets.add(tweet);
		
		TweetRepository tweetRepository = mock(TweetRepository.class);
		when(tweetRepository.getTweet(TweetId)).thenReturn(Optional.of(tweet));
		
		UserRepository userRepository = mock(UserRepository.class);
		UserService userService = new SimpleUserService(userRepository, tweetRepository);
		userService.retweet(retweeter, TweetId);
		userService.retweet(retweeter, TweetId);
		assertEquals(1, tweet.getRetweetCounter());
		
	}
	@Test
	public void severalUsersRetweetCounterTest() {
		List<Tweet> tweets = new ArrayList<>();

		User retweeter1 = new User("retweeter1");
		User retweeter2 = new User("retweeter2");
		
		User retweetTarget = new User("retweetTarget");
		
		Long TweetId = 0L;
		Tweet tweet =  tweetService.createTweet(retweetTarget);
		tweet.setTweetId(TweetId);
		tweets.add(tweet);
		
		TweetRepository tweetRepository = mock(TweetRepository.class);
		when(tweetRepository.getTweet(TweetId)).thenReturn(Optional.of(tweet));
		
		UserRepository userRepository = mock(UserRepository.class);
		UserService userService = new SimpleUserService(userRepository, tweetRepository);
		userService.retweet(retweeter1, TweetId);
		userService.retweet(retweeter2, TweetId);
		assertEquals(2, tweet.getRetweetCounter());
		
	}
	
}
