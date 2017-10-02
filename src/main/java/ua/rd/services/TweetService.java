package ua.rd.services;

import ua.rd.domain.Tweet;
import ua.rd.domain.User;
import ua.rd.repository.TweetRepository;

public interface TweetService {
    Iterable<Tweet> allUsersTweets(User user);
    Iterable<Tweet> userTimeline(User user);
    Tweet createTweet(User user);
	boolean likeTweet(Long tweetId);
	void setTweetRepository(TweetRepository tweetRepository);
}
