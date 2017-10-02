package ua.rd.services;

import java.util.Collection;
import java.util.Optional;
import java.util.function.BiPredicate;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import ua.rd.config.TimelineConfigurer;
import ua.rd.domain.Tweet;
import ua.rd.domain.User;
import ua.rd.repository.TweetRepository;

public class SimpleTweetService implements TweetService {

	private TweetRepository tweetRepository;
	private TimelineConfigurer timelineConfigurer;
	
	public SimpleTweetService(TweetRepository tweetRepository,
							 TimelineConfigurer timelineConfigurer) {
		this.tweetRepository = tweetRepository;
		this.timelineConfigurer = timelineConfigurer;
	}

	public void setTweetRepository(TweetRepository tweetRepository) {
		this.tweetRepository = tweetRepository;
	}

	@Override
	public Iterable<Tweet> allUsersTweets(User user) {
		Collection<Tweet> tweets = tweetRepository.allTweets();
		return tweets.stream().filter(tweet -> tweet.getUser().equals(user)).collect(Collectors.toList());
	}

	@Override
	public Tweet createTweet(User user) {
		Tweet tweet = newTweet();
		tweet.setUser(user);
		return tweetRepository.save(tweet);
		 
	}

	@Lookup
	protected Tweet newTweet(){
		System.out.println("newTweet: I should not be here");
		return null;
	}

	@Override
	public Iterable<Tweet> userTimeline(User user) {
		Collection<Tweet> tweets = tweetRepository.allTweets();
		return tweets.stream()
				.filter(tweet->timelineConfigurer.getDefaultConfig().test(tweet,user) )
				.collect(Collectors.toList());
	}

	@Override
	public boolean likeTweet(Long tweetId) {
		Optional<Tweet> tweet = tweetRepository.getTweet(tweetId);
		return tweet.map(item -> {
			item.like();
			tweetRepository.save(item);
			return true;
		}).orElse(false);
	}

}
