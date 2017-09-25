package ua.rd.services;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.AdditionalAnswers.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Test;

import ua.rd.domain.Tweet;
import ua.rd.domain.User;
import ua.rd.repository.TweetRepository;

public class TweetServiceTest {

	@Test
	public void getUserTweetsTest() {
		User user = new User("aaa");
		List<Tweet> tweets = new ArrayList<>();
		tweets.add(new Tweet("", user));
		tweets.add(new Tweet("", user));
		tweets.add(new Tweet("", user));
		User otherUser = new User("bbb");
		tweets.add(new Tweet("", otherUser));

		TweetRepository tweetRepository = mock(TweetRepository.class);
		TweetService service = new SimpleTweetService(tweetRepository);
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
		User user = new User("user");
		List<Tweet> tweets = new ArrayList<>();
		tweets.add(new Tweet("", user));
		tweets.add(new Tweet("", user));
		tweets.add(new Tweet("", user));
		
		User otherUser = new User("otherUser");
		tweets.add(new Tweet("", otherUser));
		user.subscribe(otherUser);
		
		User yetAnotherUser = new User("yetAnotherUser");
		Tweet tweet =new Tweet("", yetAnotherUser);
		tweet.setTweetId(4L);
		tweets.add(tweet);
		
		otherUser.setRetweet(tweet);
		
		User leftUser = new User("leftUser");
		tweets.add(new Tweet("", leftUser));
		tweets.add(new Tweet("", leftUser));
		tweets.add(new Tweet("", leftUser));
		
		TweetRepository tweetRepository = mock(TweetRepository.class);
		TweetService service = new SimpleTweetService(tweetRepository);
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
		Tweet tweet = new Tweet("", new User());

		TweetRepository tweetRepository = mock(TweetRepository.class);
		when(tweetRepository.getTweet(0L)).thenReturn(Optional.of(tweet));

		TweetService service = new SimpleTweetService(tweetRepository);
		Long teweetId = 0L;
		long oldLikeCount = tweet.getLikeCounter();
		
		service.likeTweet(teweetId);
		
		long newLikeCount = tweet.getLikeCounter();
		long diff = newLikeCount - oldLikeCount;
		assertEquals(1, diff);
	}

	@Test
	public void createTweetTest() {
		User user = new User("aaa");
		TweetRepository tweetRepository = mock(TweetRepository.class);
		when(tweetRepository.save(any(Tweet.class))).then(returnsFirstArg());
		TweetService service = new SimpleTweetService(tweetRepository);
		Tweet tweet = service.newTweet(user);
		assertEquals(user, tweet.getUser());
	}
	
}
