package ua.rd.services;

import java.util.Optional;

import ua.rd.domain.User;

public interface UserService {
	User createNewUser(String name);
	User saveUser(User user);
	Optional<User> getUser(Long id);
	boolean createSubscription(User subscriber, Long targetId);
	boolean retweet(User retweeter, Long tweetId);
}
