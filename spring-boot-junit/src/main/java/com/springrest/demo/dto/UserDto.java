package com.springrest.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter @Getter
@AllArgsConstructor
public class UserDto {

	int id;
	String firstName;
	String lastName;
	String email;
}
