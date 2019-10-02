package org.sk.security.demo.db.repositories;

import java.util.Optional;

import org.sk.security.demo.db.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

	Optional<User> findByLoginId(String loginId);
	Optional<User> findByLoginIdAndPassword(String loginId, String password);
}
