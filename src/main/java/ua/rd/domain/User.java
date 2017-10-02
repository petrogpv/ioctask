package ua.rd.domain;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

//@Component("user")
//@Scope("prototype")
public class User {

	private Long id;
	
	
	private Set<User> subscriptions = new HashSet<>();
	
	private Set<Tweet> retweets = new HashSet<>();

	private String name;

	public User() {

	}

	public User(String name) {
		this.name = name;
	}

	public boolean subscribe(User target) {
		return subscriptions.add(target);

	}


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<User> getSubscriptions() {
		return subscriptions;
	}

	public void setSubscriptions(Set<User> subscriptions) {
		this.subscriptions = subscriptions;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		User other = (User) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "User{ name='" + name + '}';
	}

	public Set<Tweet> getRetweets() {
		return retweets;
	}
	
	public boolean setRetweet(Tweet tweet) {
		boolean changed =  retweets.add(tweet);
		if(changed) {
			tweet.incrementRetweetCounter();
		}
		return changed;
	}

	public void setRetweets(Set<Tweet> retweets) {
		this.retweets = retweets;
	}

}
