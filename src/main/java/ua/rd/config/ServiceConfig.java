package ua.rd.config;

import java.util.function.BiPredicate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import org.springframework.context.annotation.ImportResource;
import ua.rd.domain.*;
import ua.rd.repository.TweetRepository;
import ua.rd.repository.UserRepository;
import ua.rd.services.SimpleTweetService;
import ua.rd.services.SimpleUserService;
import ua.rd.services.TweetService;
import ua.rd.services.UserService;

@Configuration
//@ImportResource("classpath:/config.xml")
public class ServiceConfig {

	@Bean
	@Autowired
	public TweetService tweetService(TweetRepository tweetRepository, TimelineConfigurer timelineConfigurer) {
		return new SimpleTweetService(tweetRepository, timelineConfigurer) ;
	}

	@Bean
	@Autowired
	public UserService userService(UserRepository userRepository, TweetRepository tweetRepository) {
		return new SimpleUserService(userRepository, tweetRepository);
	}


	@Bean TimelineConfigurer timelineConfigurer() {
		return new TimelineConfigurer();
	}
}
