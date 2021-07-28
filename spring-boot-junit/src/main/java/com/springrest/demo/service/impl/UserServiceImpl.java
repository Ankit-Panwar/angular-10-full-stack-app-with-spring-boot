package com.springrest.demo.service.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springrest.demo.dto.UserDto;
import com.springrest.demo.dto.UserRequest;
import com.springrest.demo.exception.ResourceNotFoundException;
import com.springrest.demo.model.User;
import com.springrest.demo.repository.UserRepository;
import com.springrest.demo.service.UserService;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	ModelMapper modelMapper;

	@Override
	public List<User> getAllUser() {
		List<User> userList = this.userRepository.findAll();
//		List<UserDto> list  = new ArrayList<UserDto>();
//		userList.forEach(user -> {
//			list.add(new UserDto(user.getId(), user.getFirstName(), user.getLastName(), user.getEmail()));
//		});
		System.out.println("Getting data from DB : " + userList);
		return userList;
	}

	@Override
	public Map<String, Object> createUpdate(UserRequest userDto) {
		Map<String, Object> response = new HashMap<String, Object>();
		User user = modelMapper.map(userDto, User.class);
		user.setDob(LocalDate.now());
		user.setJoiningDate(LocalDate.now());
		user = this.userRepository.save(user);
		response.put("data", user);
		response.put("message", userDto.getId() == 0 ? "User Addes Sucessfully":"User Updated Sucessfully");
		return response;
	}

	@Override
	public User getUserById(Integer userId) {
		Optional<User> user = this.userRepository.findById(userId);
		if(!user.isPresent())
			throw new ResourceNotFoundException("User Not Exist With Id : " + userId);
		return user.get();
	}

	@Override
	public Map<String, Boolean> deleteUserByIdSoftDelete(Integer userId) {
		Map<String, Boolean> response = new HashMap<String, Boolean>();
		Optional<User> user = this.userRepository.findById(userId);
		if(!user.isPresent())
			throw new ResourceNotFoundException("User Not Exist With Id : " + userId);
		this.userRepository.deleteByUserId(userId, false);
		response.put("deleted", Boolean.TRUE);
		return response;
	}

	@Override
	public User deleteUserByIdHardDelete(Integer userId) {
		User user = this.userRepository.findById(userId).get();
		this.userRepository.deleteById(userId);
		return user;
	}

	@Override
	public Map<String, Object> searchByFirstName(String firstName) {
		Map<String, Object> response = new HashMap<String, Object>();
		List<User> userList = userRepository.findByFirstName(firstName);
		if(userList.size() == 0)
			response.put("message", "No Record Found");
		else 
			response.put("data", userList);
		return response;
	}

	@Override
	public Map<String, Object> searchByLastName(String lastName) {
		Map<String, Object> response = new HashMap<String, Object>();
		List<User> userList = userRepository.findByLastName(lastName);
		if(userList.size() == 0)
			response.put("message", "No Record Found");
		else 
			response.put("data", userList);
		return response;
	}

	@Override
	public Map<String, Object> searchByPincode(int pincode) {
		Map<String, Object> response = new HashMap<String, Object>();
		List<User> userList = userRepository.findByPincode(pincode);
		if(userList.size() == 0)
			response.put("message", "No Record Found");
		else 
			response.put("data", userList);
		return response;
	}

	@Override
	public Map<String, Object> sortByDOB() {
		Map<String, Object> response = new HashMap<String, Object>();
		List<User> userList = this.userRepository.sortByDOB();
		response.put("data",userList);
		return response;
	}

	@Override
	public Map<String, Object> sortByJoiningDate() {
		Map<String, Object> response = new HashMap<String, Object>();
		List<User> userList = this.userRepository.sortByJoingDate();
		response.put("data", userList);
		return response;
	}

	
	
}
