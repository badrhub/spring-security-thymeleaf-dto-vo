package com.b123.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import com.b123.service.model.User;


public interface UserRepository extends JpaRepository<User, Long> {
 User findByUsername(String userName);
}
