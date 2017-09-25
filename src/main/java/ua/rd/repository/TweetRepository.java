package ua.rd.repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;
import ua.rd.domain.Tweet;
import ua.rd.domain.User;

public interface TweetRepository {

    Collection<Tweet> allTweets();

    Optional<Tweet> getTweet(Long l);

    Tweet save(Tweet tweet);

}
