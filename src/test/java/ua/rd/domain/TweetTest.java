package ua.rd.domain;

import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ua.rd.config.RepoConfig;
import ua.rd.config.ServiceConfig;
import ua.rd.services.TweetService;

@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(classes = {ServiceConfig.class, RepoConfig.class})
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@ContextHierarchy({
        @ContextConfiguration(locations = { "classpath:config.xml" }),
        @ContextConfiguration(classes = ServiceConfig.class)
})
@ContextConfiguration(locations = { "classpath:config.xml" })
@ActiveProfiles("dev")
public class TweetTest {

    @Autowired
    TweetService tweetService;

    @Test
    public void setTweetTextTest() {
        String message = "texttext";
        Tweet tweet = tweetService.createTweet(null);
        tweet.setTxt(message);
        assertEquals(message, tweet.getTxt());
    }

    @Test
    public void setTweetTextLongerThenMaxTextLengthTest() {
        String message = "texttext";
        Tweet tweet = tweetService.createTweet(null);
        tweet.setMaxTextLength(message.length() - 1L);
        tweet.setTxt(message);
        assertNull(tweet.getTxt());
    }


}
