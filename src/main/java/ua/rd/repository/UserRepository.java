package ua.rd.repository;

import java.util.List;
import java.util.Optional;

import ua.rd.domain.User;

public interface UserRepository {
	
	User save(User user);
	User update(User user);
	Optional<User> get(Long userId);
	List<User> getUsersByName(String username);

}
