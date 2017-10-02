package ua.rd.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import ua.rd.domain.User;

public class InMemUserRepository implements UserRepository {
	private static Long idCounter = Long.valueOf(0);
	private Map<Long,User> userRepo = new HashMap<>();
	
	public InMemUserRepository(){}
	@Override
	public User save(User user) {
		if(user.getId()==null) {
			user.setId(idCounter++);
		}
		put(user);
		return user;
	}
	
	@Override
	public User update(User user) {
		return save(user);
	}

	@Override
	public Optional<User> get(Long userId) {
		return Optional.ofNullable(userRepo.get(userId));
	}

	@Override
	public List<User> getUsersByName(String username) {
		return userRepo.values().stream().filter(user->user.getName().equals(username)).collect(Collectors.toList());
	}

	
	private User put(User user) {
		return userRepo.put(user.getId(),user);
	}
}
