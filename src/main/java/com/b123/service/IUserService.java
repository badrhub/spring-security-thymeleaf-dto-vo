package com.b123.service;

import java.util.List;
import org.springframework.security.core.userdetails.UserDetailsService;
import com.b123.domaine.RoleVo;
import com.b123.domaine.UserVo;

public interface IUserService extends UserDetailsService{
void save(UserVo user);
void save(RoleVo role);
 List<UserVo> getAllUsers();
 List<RoleVo> getAllRoles();
 RoleVo getRoleByName(String role);
void cleanDataBase();
List<UserVo> getAllUsersSA();
UserVo getUserById(Long id);
UserVo deleteUserRole(Long id1, Integer id2);
UserVo addUserRole(Long id1, Integer id2);
List<RoleVo> getAllRolesSA();
UserVo update(UserVo emp);
void delete(Long id1);
boolean isUniq(String s);
}
