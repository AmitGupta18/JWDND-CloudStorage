package com.udacity.jwdnd.course1.cloudstorage.services;

import java.util.ArrayList;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

import com.udacity.jwdnd.course1.cloudstorage.data.User;
import com.udacity.jwdnd.course1.cloudstorage.data.mappers.UserMapper;

@Service
public class AuthenticationService implements AuthenticationProvider {

	private UserMapper userMapper;
	private HashService hashService;

	public AuthenticationService(UserMapper userMapper, HashService hashService) {
		this.userMapper = userMapper;
		this.hashService = hashService;
	}

	public UserMapper getUserMapper() {
		return userMapper;
	}

	public void setUserMapper(UserMapper userMapper) {
		this.userMapper = userMapper;
	}

	public HashService getHashService() {
		return hashService;
	}

	public void setHashService(HashService hashService) {
		this.hashService = hashService;
	}

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String username = authentication.getName();
		String pwd = authentication.getCredentials().toString();

		User user = userMapper.getUser(username);
		if (user != null) {
			String endodedSalt = user.getSalt();
			String hashPassword = hashService.getHashedValue(pwd, endodedSalt);

			if (user.getPassword().equals(hashPassword)) {
				return new UsernamePasswordAuthenticationToken(username, pwd, new ArrayList<>());
			}
		}
		return null;
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}

}
