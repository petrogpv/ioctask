package ua.rd.config;

import java.util.HashMap;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import ua.rd.domain.Tweet;
import ua.rd.domain.User;
import ua.rd.repository.InMemTweetRepository;
import ua.rd.repository.InMemUserRepository;
import ua.rd.repository.TweetRepository;
import ua.rd.repository.UserRepository;

@Configuration
public class RepoConfig {
	@Bean
	public TweetRepository tweetRepository() {
		return new InMemTweetRepository(new HashMap<>());
	}
	@Bean
	public UserRepository userRepository() {
		return new InMemUserRepository(new HashMap<>());
		
	}
	@Bean
	@Scope("prototype")
	public User user() {
		return new User();
	}
	@Bean
	@Scope("prototype")
	public Tweet tweet() {
		return new Tweet();
	}
} 
