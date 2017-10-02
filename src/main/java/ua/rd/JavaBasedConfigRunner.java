package ua.rd;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import ua.rd.config.RepoConfig;
import ua.rd.config.ServiceConfig;
import ua.rd.domain.Tweet;
import ua.rd.domain.User;
import ua.rd.services.TweetService;
import ua.rd.services.UserService;

public class JavaBasedConfigRunner {
	public static void main(String[] args) {

		AnnotationConfigApplicationContext repoContext = new AnnotationConfigApplicationContext(RepoConfig.class);

		AnnotationConfigApplicationContext serviceContext = new AnnotationConfigApplicationContext();
		serviceContext.setParent(repoContext);
		serviceContext.register(ServiceConfig.class);
		serviceContext.refresh();
		
		UserService userService = serviceContext.getBean(UserService.class);
		TweetService tweetService = serviceContext.getBean(TweetService.class);
		User currentUser = userService.saveUser(userService.newUser("user1"));
		
		
		tweetService.createTweet(currentUser);
		tweetService.createTweet(currentUser);
		System.out.println(tweetService.allUsersTweets(currentUser));
		
		User user2 = userService.saveUser(userService.newUser("user2"));
		tweetService.createTweet(user2);
		tweetService.createTweet(user2);
		User user3 = userService.saveUser(userService.newUser("user3"));
		tweetService.createTweet(user3);
		Tweet targetTweet = tweetService.createTweet(user3);
		
		userService.createSubscription(currentUser, user2.getId());
		userService.retweet(user2, targetTweet.getTweetId());
		
		System.out.println(tweetService.userTimeline(currentUser));
		for(Tweet tweet :tweetService.userTimeline(currentUser)) {
			tweetService.likeTweet(tweet.getTweetId());
		}
		
		System.out.println(tweetService.userTimeline(currentUser));
	}
}
