package ua.rd.config;

		import java.util.function.BiPredicate;

		import ua.rd.domain.Tweet;
		import ua.rd.domain.User;

public class TimelineConfigurer {

	private  BiPredicate<Tweet,User> ownTweet = (tweet,user)->tweet.getUser().equals(user);
	private  BiPredicate<Tweet,User> subscriptionsTweet = (tweet,user) -> user.getSubscriptions().contains(tweet.getUser());

	private  BiPredicate<Tweet,User> ownRetweet = (tweet,user)->user.getRetweets().contains(tweet);

	private  BiPredicate<Tweet,User> subscriptionsRetweet = (tweet,user) -> user.getSubscriptions().stream()
			.flatMap(targetUser -> targetUser.getRetweets().stream())
			.anyMatch(item-> item.equals(tweet));


	public  BiPredicate<Tweet,User> getDefaultConfig() {
		return ownTweet.or(ownRetweet).or(subscriptionsTweet).or(subscriptionsRetweet);
	}
}
