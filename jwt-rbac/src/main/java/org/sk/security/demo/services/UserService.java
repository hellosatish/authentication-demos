package org.sk.security.demo.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.sk.security.demo.config.security.TokenService;
import org.sk.security.demo.db.entities.User;
import org.sk.security.demo.db.repositories.UserRepository;
import org.sk.security.demo.dto.LoginDTO;
import org.sk.security.demo.dto.UserDTO;
import org.sk.security.demo.dto.UserResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepo;
	@Autowired
	private TokenService tokenService;

	// ideally this shall be handled by DTO
	public Optional<String> getLoginToken(LoginDTO requestData) {
		return userRepo.findByLoginIdAndPassword(requestData.getLoginId(), requestData.getPassword()).map(usr -> {
			String token =tokenService.createToken(usr);
			return Optional.of(token);
		}).orElseGet(Optional :: empty);
	}

	public Optional<UserResponseDTO> createUser(UserDTO requestData) {
		User usr = userRepo.save(requestData.getEntity(0));
		return Optional.of(usr.getDto());
	}

	public Optional<UserResponseDTO> udpateUser(UserDTO requestData, int userId) {
		Optional<User> userInDb = userRepo.findById(userId);
		return userInDb.map(x -> {
			User usr = userRepo.save(requestData.getEntity(userId));
			return Optional.of(usr.getDto());
		}).orElseGet(Optional::empty);
	}

	public Optional<Integer> deleteUser(int userId) {
		Optional<User> userInDb = userRepo.findById(userId);
		return userInDb.map(x -> {
			userRepo.deleteById(userId);
			return Optional.of(userId);
		}).orElseGet(Optional::empty);
	}

	public Optional<UserResponseDTO> findUser(int userId) {
		Optional<User> userInDb = userRepo.findById(userId);
		return userInDb.map(x -> {
			return Optional.of(x.getDto());
		}).orElseGet(Optional::empty);
	}

	public List<UserResponseDTO> getAllUsers() {
		return userRepo.findAll().stream().map(User::getDto).collect(Collectors.toList());
	}
}
