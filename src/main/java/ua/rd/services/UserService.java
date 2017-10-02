package ua.rd.services;

import java.util.Optional;

import ua.rd.domain.User;
import ua.rd.repository.UserRepository;

public interface UserService {
	User newUser(String name);
	User saveUser(User user);
	Optional<User> getUser(Long id);
	boolean createSubscription(User subscriber, Long targetId);
	boolean retweet(User retweeter, Long tweetId);
	void setUserRepository(UserRepository userRepository);
}
