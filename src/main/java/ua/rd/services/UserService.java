package ua.rd.services;

import ua.rd.domain.User;

public interface UserService {
	User createNewUser(String name);
	User saveUser(User user);
	User getUser(Long id);
	boolean createSubscription(User subscriber, Long targetId);
	boolean retweet(User retweeter, Long tweetId);
}
