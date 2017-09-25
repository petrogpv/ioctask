package ua.rd.services;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import ua.rd.domain.Tweet;
import ua.rd.domain.User;
import ua.rd.repository.TweetRepository;

@Service("tweetService")
public class SimpleTweetService implements TweetService {

	private TweetRepository tweetRepository;

	public SimpleTweetService(TweetRepository tweetRepository) {
		this.tweetRepository = tweetRepository;
	}

	@Override
	public Iterable<Tweet> allUsersTweets(User user) {
		Collection<Tweet> tweets = tweetRepository.allTweets();
		return tweets.stream().filter(tweet -> tweet.getUser().equals(user)).collect(Collectors.toList());
	}

	@Override
	public Tweet newTweet(User user) {
		Tweet tweet = createNewTweet();
		tweet.setUser(user);
		return tweetRepository.save(tweet);
		 
	}
	
	
	
	@Override
	public Tweet createNewTweet() {
		return new Tweet();
	}

	@Override
	public Iterable<Tweet> userTimeline(User user) {
		Collection<Tweet> tweets = tweetRepository.allTweets();
		
		Predicate<Tweet> ownTweet = (tweet)->tweet.getUser().equals(user);
		Predicate<Tweet> subscriptionsTweet = (tweet) -> user.getSubscriptions().contains(tweet.getUser());
		
		Predicate<Tweet> ownRetweet = (tweet)->user.getRetweets().contains(tweet);
		
		Predicate<Tweet> subscriptionsretweet = (tweet) -> user.getSubscriptions().stream()
												.flatMap(targetUser -> targetUser.getRetweets().stream())
												.anyMatch(item-> item.equals(tweet));
		
		return tweets.stream().filter(ownTweet.or(subscriptionsTweet.or(ownRetweet.or(subscriptionsretweet))))
				.collect(Collectors.toList());
	}

	@Override
	public boolean likeTweet(Long tweetId) {
		Optional<Tweet> tweet = tweetRepository.getTweet(tweetId);
		return tweet.map(item -> {
			item.like();
			return true;
		}).orElse(false);
	}

}
