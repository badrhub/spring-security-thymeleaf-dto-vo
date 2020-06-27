package com.b123.dao;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.b123.service.model.Role;

public interface RoleRepository extends JpaRepository<Role, Integer> {
	 List<Role> findByRole(String role);
	 List<Role> findAll();
	 @Query("select r from Role r where r.role='SUPER ADMIN'")
	Role findByRoleSA(String string);
	}
