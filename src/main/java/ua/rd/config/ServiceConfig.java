package ua.rd.config;

import java.util.function.BiPredicate;

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
	public TweetService tweetService(TweetRepository tweetRepository,BiPredicate<Tweet,User> timelineConfig) {
		return new SimpleTweetService(tweetRepository,timelineConfig) {
			
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
	
	@Bean
	@Autowired
	public BiPredicate<Tweet,User> timelineConfig(TimelineConfigurer timelineConfigurer){
		return timelineConfigurer.showOwnRetweet()
				.or(timelineConfigurer.showOwnTweet())
				.or(timelineConfigurer.showSubscriptionsRetweet())
				.or(timelineConfigurer.showSubscriptionsTweet());
	}
	@Bean TimelineConfigurer timelineConfigurer() {
		return new TimelineConfigurer();
	}
}
