package com.ssafy.api.service;

import com.ssafy.api.request.UserPatchPostReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ssafy.api.request.UserRegisterPostReq;
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
	public User createUser(UserRegisterPostReq userRegisterInfo) {
		User user = new User();
		user.setUserId(userRegisterInfo.getUserId());
		// 보안을 위해서 유저 패스워드 암호화 하여 디비에 저장.
		user.setPassword(passwordEncoder.encode(userRegisterInfo.getPassword()));
		user.setName(userRegisterInfo.getName());
		user.setYear(userRegisterInfo.getYear());
		user.setMonth(userRegisterInfo.getMonth());
		user.setDay(userRegisterInfo.getDay());
		user.setNickName(userRegisterInfo.getNickName());
		return userRepository.save(user);
	}

	@Override
	public User getUserByUserId(String userId) {
		// 디비에 유저 정보 조회 (userId 를 통한 조회).
		User user = null;
		if(userRepositorySupport.findUserByUserId(userId).isPresent()) {
			user = userRepositorySupport.findUserByUserId(userId).get();
		}
		return user;
	}

	public User updateUserByUserId(String userId, UserPatchPostReq userPatchPostReq){
		User user = userRepositorySupport.findUserByUserId(userId).get();
		user.setName(userPatchPostReq.getName());
		user.setYear(userPatchPostReq.getYear());
		user.setMonth(userPatchPostReq.getMonth());
		user.setDay(userPatchPostReq.getDay());
		user.setNickName(userPatchPostReq.getNickName());
		return userRepository.save(user);
	}

	public User deleteUserByUserId(String userId){
		User user = userRepositorySupport.findUserByUserId(userId).get();
		userRepository.delete(user);
		return user;
	}


}
