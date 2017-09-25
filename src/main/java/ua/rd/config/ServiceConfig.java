package ua.rd.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import ua.rd.domain.Tweet;
import ua.rd.domain.User;
import ua.rd.repository.TweetRepository;
import ua.rd.repository.UserRepository;
import ua.rd.services.SimpleTweetService;
import ua.rd.services.SimpleUserService;
import ua.rd.services.TweetService;
import ua.rd.services.UserService;

@Configuration
public class ServiceConfig {
	
	@Bean
	@Autowired
	public TweetService tweetService(TweetRepository tweetRepository) {
		return new SimpleTweetService(tweetRepository) {
			
			@Override
			public Tweet createNewTweet() {
				return new Tweet();
			}
		}; 
	}
	
	@Bean
	@Autowired
	public UserService userService(UserRepository userRepository,TweetRepository tweetRepository) {
		return new SimpleUserService(userRepository,tweetRepository) {
			
			@Override
			public User createNewUser(String name) {
				return new User(name);
			}
		};
	}
}
