package com.tts.TechTalentTwitter.repository;

import org.springframework.data.repository.CrudRepository;

import com.tts.TechTalentTwitter.model.User;

public interface UserRepository extends CrudRepository<User, Long>{
	 User findByUsername(String username);
}
