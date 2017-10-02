package ua.rd.config;

import java.util.HashMap;
import java.util.stream.Stream;

import org.springframework.context.annotation.*;

import ua.rd.domain.Tweet;
import ua.rd.domain.User;
import ua.rd.repository.InMemTweetRepository;
import ua.rd.repository.InMemUserRepository;
import ua.rd.repository.TweetRepository;
import ua.rd.repository.UserRepository;

@Configuration
//@ComponentScan("ua.rd")
//@ImportResource("classpath:/config.xml")
public class RepoConfig {
	private static  final long MAX_TEXT_LENGTH = 130L;

	@Bean
	public TweetRepository tweetRepository() {
		return new InMemTweetRepository();
	}
	@Bean
	public UserRepository userRepository() {
		return new InMemUserRepository();
	}

	@Bean
	@Scope("prototype")
	public User user() {
		return new User();
	}

	@Bean
	@Scope("prototype")
	public Tweet tweet() {
		return new Tweet(MAX_TEXT_LENGTH);
	}
} 
