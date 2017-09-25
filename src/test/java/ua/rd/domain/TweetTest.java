package ua.rd.domain;

import static org.junit.Assert.*;

import org.junit.Test;

public class TweetTest {

	@Test
	public void setTweetTextTest() {
		Tweet tweet = new Tweet();
		String message ="texttext";
		tweet.setMaxTextLength(message.length());
		tweet.setTxt(message);
		assertEquals(message, tweet.getTxt());
	}
	@Test
	public void setTweetTextLongerThenMaxTextLengthTest() {
		Tweet tweet = new Tweet();
		String message ="texttext";
		tweet.setMaxTextLength(message.length()-1);
		tweet.setTxt(message);
		assertNull(tweet.getTxt());
	}


}
