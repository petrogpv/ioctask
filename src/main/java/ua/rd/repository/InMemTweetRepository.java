package ua.rd.repository;

import org.springframework.stereotype.Repository;
import ua.rd.domain.Tweet;
import ua.rd.domain.User;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository("tweetRepository")
public class InMemTweetRepository implements TweetRepository {
	private static Long idCounter = Long.valueOf(0);
	private Map<Long, Tweet> tweets = new HashMap<>() ;

	public InMemTweetRepository() {
	}

	@Override
	public Collection<Tweet> allTweets() {
		return tweets.values();

	}

	@Override
	public Optional<Tweet> getTweet(Long id) {
		return Optional.ofNullable(tweets.get(id));
	}
	
	@Override
	public Tweet save(Tweet tweet) {
		if (tweet.getTweetId() == null) {
			tweet.setTweetId(idCounter++);
		}
		put(tweet);
		return tweet;
	}

	private Tweet put(Tweet tweet) {
		return tweets.put(tweet.getTweetId(), tweet);
	}
}
