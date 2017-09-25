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
		User user1 = userService.createNewUser("user1");
		tweetService.newTweet(user1);
		tweetService.newTweet(user1);
		System.out.println(tweetService.allUsersTweets(user1));
		User user2 = userService.createNewUser("user2");
		tweetService.newTweet(user2);
		tweetService.newTweet(user2);
		User user3 = userService.createNewUser("user3");
		tweetService.newTweet(user3);
		tweetService.newTweet(user3);
		
		user1.subscribe(user2);
		System.out.println(tweetService.userTimeline(user1));
		for(Tweet tweet :tweetService.userTimeline(user1)) {
			tweetService.likeTweet(tweet.getTweetId());
		}
		for(Tweet tweet :tweetService.userTimeline(user1)) {
			System.out.println(tweet.getLikeCounter());
		}
		
	}
}
