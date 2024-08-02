package com.knowit.commentService.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.knowit.commentService.entity.User;


@FeignClient(name = "AuthService")
public interface UserClient {

	@GetMapping("/api/auth/users/{id}")
	public ResponseEntity<User> getUserById(@PathVariable Long id);
}
