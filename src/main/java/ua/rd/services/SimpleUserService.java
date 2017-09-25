package ua.rd.services;

import java.util.Optional;

import ua.rd.domain.Tweet;
import ua.rd.domain.User;
import ua.rd.repository.TweetRepository;
import ua.rd.repository.UserRepository;

public class SimpleUserService implements UserService {

	UserRepository userRepository;
	TweetRepository tweetRepository;

	SimpleUserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public SimpleUserService(UserRepository userRepository, TweetRepository tweetRepository) {
		this.userRepository = userRepository;
		this.tweetRepository = tweetRepository;
	}

	@Override
	public User saveUser(User user) {
		return userRepository.save(user);
	}

	@Override
	public User getUser(Long id) {
		return userRepository.get(id);
	}

	@Override
	public User createNewUser(String name) {
		return new User(name);
	}

	@Override
	public boolean createSubscription(User subscriber, Long targetUserId) {
		User targetUser = getUser(targetUserId);
		boolean chenged = subscriber.subscribe(targetUser);
		if (chenged) {
			saveUser(subscriber);
		}
		return chenged;
	}

	@Override
	public boolean retweet(User retweeter, Long tweetId) {
		Optional<Tweet> targetTweet = tweetRepository.getTweet(tweetId);
		return targetTweet.map(tweet -> {
			boolean chenged = retweeter.setRetweet(tweet);
			if (chenged) {
				saveUser(retweeter);
			}
			return chenged;
		}).orElse(false);
	}
}