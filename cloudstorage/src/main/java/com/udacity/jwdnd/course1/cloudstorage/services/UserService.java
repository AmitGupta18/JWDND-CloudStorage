package com.udacity.jwdnd.course1.cloudstorage.services;

import java.security.SecureRandom;
import java.util.Base64;

import org.springframework.stereotype.Service;

import com.udacity.jwdnd.course1.cloudstorage.data.User;
import com.udacity.jwdnd.course1.cloudstorage.data.mappers.UserMapper;

@Service
public class UserService {
	private final UserMapper userMapper;
	private final HashService hashService;

	public UserService(UserMapper userMapper, HashService hashService) {
		super();
		this.userMapper = userMapper;
		this.hashService = hashService;
	}

	public UserMapper getUserMapper() {
		return userMapper;
	}

	public HashService getHashService() {
		return hashService;
	}

	/**
	 * @param username
	 * @return
	 */
	public boolean isUserNameAvailable(String username) {
		return (userMapper.getUser(username) == null);
	}

	/**
	 * @param user
	 * @return
	 */
	public int createUser(User user) {
		SecureRandom random = new SecureRandom();
		byte[] salt = new byte[16];
		// generating a random number and assign in salt
		random.nextBytes(salt);
		String encodedSalt = Base64.getEncoder().encodeToString(salt);
		String hashedPassword = hashService.getHashedValue(user.getPassword(), encodedSalt);
		return userMapper.insert(new User(null, user.getUsername(), encodedSalt, hashedPassword, user.getFirstName(),
				user.getLastName()));
	}

}
