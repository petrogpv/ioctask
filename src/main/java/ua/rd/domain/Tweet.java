package ua.rd.domain;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

//@Component("tweet")
//@Scope("prototype")
public class Tweet {

	private Long tweetId;
	private String txt;
	private User user;
	private long likeCounter;
	private long retweetCounter;
	private long maxTextLength;

	public Tweet(Long maxTextLength) {
		this.maxTextLength = maxTextLength;

	}

	public Long getTweetId() {
		return tweetId==null? null: new Long(tweetId);
	}

	public void setTweetId(Long tweetId) {
		this.tweetId = new Long(tweetId);
	}

	public String getTxt() {
		return txt;
	}

	public void setTxt(String txt) {
		if (txt.length() <= maxTextLength) {
			this.txt = txt;
		}
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Long getLikeCounter() {
		return likeCounter;
	}

	public void like() {
		this.likeCounter++;
	}

	public void dislike() {
		this.likeCounter--;
	}

	public long getMaxTextLength() {
		return maxTextLength;
	}

	public void setMaxTextLength(long maxTextLength) {
		this.maxTextLength = maxTextLength;
	}

	public long getRetweetCounter() {
		return retweetCounter;
	}

	void incrementRetweetCounter() {
		retweetCounter++;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((tweetId == null) ? 0 : tweetId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Tweet other = (Tweet) obj;
		if (tweetId == null) {
			if (other.tweetId != null)
				return false;
		} else if (!tweetId.equals(other.tweetId))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Tweet{" + "tweetId=" + tweetId + 
				", txt='" + txt + '\'' + 
				", author=" + user.getName() + 
				", likes=" + likeCounter + 
				'}';
	}

}
