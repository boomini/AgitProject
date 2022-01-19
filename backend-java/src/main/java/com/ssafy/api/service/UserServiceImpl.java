package com.ssafy.api.service;

import com.ssafy.api.dto.UserDto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ssafy.db.entity.User;
import com.ssafy.db.repository.UserRepository;
import com.ssafy.db.repository.UserRepositorySupport;

/**
 *	유저 관련 비즈니스 로직 처리를 위한 서비스 구현 정의.
 */
@Service("userService")
public class UserServiceImpl implements UserService {
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	UserRepositorySupport userRepositorySupport;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Override
	public boolean createUser(UserDto userDto){
		// 보안을 위해서 유저 패스워드 암호화 하여 디비에 저장.
		userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
		userRepository.save(userDto.toEntity()).getId();
		return true;
	}

	@Override
	public User getUserByUserId(String userId) {
		// 디비에 유저 정보 조회 (userId 를 통한 조회).
		User user = null;
		UserDto userDto = null;
		if(userRepositorySupport.findUserByUserId(userId).isPresent()) {
			user = userRepositorySupport.findUserByUserId(userId).get();
		}

		return user;
	}

	public boolean  updateUserByUserId(String userId, UserDto userDto){
		User user = getUserByUserId(userId);
		if(user==null){
			//해당유저가 존재하지 않을때
			return false;
		}
		user.setNickName(userDto.getNickName());
		userRepository.save(user);
		return true;
	}

	public boolean deleteUserByUserId(String userId){
		// 디비에 유저 정보 조회 (userId 를 통한 조회).
		User user = getUserByUserId(userId);
		if(user==null){
			//해당유저가 존재하지 않을때.
			return false;
		}

		userRepository.delete(user);
		return true;
	}


}
