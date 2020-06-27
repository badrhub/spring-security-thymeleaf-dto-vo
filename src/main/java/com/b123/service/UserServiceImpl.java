package com.b123.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.b123.dao.EmpRepository;
import com.b123.dao.RoleRepository;
import com.b123.dao.UserRepository;
import com.b123.domaine.RoleConverter;
import com.b123.domaine.RoleVo;
import com.b123.domaine.UserConverter;
import com.b123.domaine.UserVo;
import com.b123.service.model.Role;
import com.b123.service.model.User;

@Service("userService")
@Transactional
public class UserServiceImpl implements IUserService {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	@Autowired
	private EmpRepository empRepository;
	@Autowired
    private JavaMailSender javaMailSender;

	
	public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
 		this.userRepository = userRepository;
 		this.roleRepository = roleRepository;
 		this.bCryptPasswordEncoder = bCryptPasswordEncoder; 

 }
	
@Override
public UserDetails loadUserByUsername(String username) throws
UsernameNotFoundException {
 User user = userRepository.findByUsername(username);
 boolean enabled = true;
 boolean accountNonExpired = true;
 boolean credentialsNonExpired = true;
 boolean accountNonLocked = true;
 return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),enabled, accountNonExpired, credentialsNonExpired, accountNonLocked,
getAuthorities(user.getRoles()));
 }


private Collection<? extends GrantedAuthority> getAuthorities(List<Role> roles) {
 List<GrantedAuthority> springSecurityAuthorities = new ArrayList<>();
 for (Role r : roles) {
 springSecurityAuthorities.add(new SimpleGrantedAuthority(r.getRole()));
 }
 return springSecurityAuthorities;
 }


@Override
@Transactional
public void save(UserVo userVo) {
 User user = UserConverter.toBo(userVo);
 user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
 List<Role> rolesPersist = new ArrayList<>();
 for (Role role : user.getRoles()) {
 Role userRole = roleRepository.findByRole(role.getRole()).get(0);
 rolesPersist.add(userRole);
 }
 user.setRoles(rolesPersist);
 userRepository.save(user);
 }

@Override
@Transactional
public void save(RoleVo roleVo) {
 roleRepository.save(RoleConverter.toBo(roleVo));
 }

@Override
public List<UserVo> getAllUsers() {
 return UserConverter.toVoList(userRepository.findAll());
 }

public List<UserVo> getAllUsersSA() {
	Role r = roleRepository.findByRoleSA("SUPER ADMIN");
 return UserConverter.toVoList(userRepository.findAll().stream()
		  .filter(u -> !u.getRoles().contains(r))
		  .collect(Collectors.toList()));
 }

@Override
public List<RoleVo> getAllRoles() {
 return RoleConverter.toVoList(roleRepository.findAll());
 }

@Override
public RoleVo getRoleByName(String role) {
 return RoleConverter.toVo(roleRepository.findByRole(role).get(0));
 } 

@Override
public void cleanDataBase() {
 userRepository.deleteAll();
 roleRepository.deleteAll();
 empRepository.deleteAll();
 }

@Override
public UserVo getUserById(Long id) {
		return UserConverter.toVo(userRepository.getOne(id));
}

@Override
@Transactional
public UserVo deleteUserRole(Long id1, Integer id2) {
	User u = userRepository.getOne(id1);
	Role r = roleRepository.getOne(id2);
	u.getRoles().remove(r);
	return UserConverter.toVo(userRepository.save(u));
}

@Override
@Transactional
public UserVo addUserRole(Long id1, Integer id2) {
	User u = userRepository.getOne(id1);
	Role r = roleRepository.getOne(id2);
	if(!u.getRoles().contains(r)) {
		u.getRoles().add(r);
		u = userRepository.save(u);
	}
	return UserConverter.toVo(u);
}

@Override
public List<RoleVo> getAllRolesSA() {
	 return RoleConverter.toVoList(roleRepository.findAll().stream()
			  .filter(u -> !u.getRole().equals("SUPER ADMIN"))
			  .collect(Collectors.toList()));
}

@Override
@Transactional
public UserVo update(UserVo userVo) {
	User user = userRepository.getOne(userVo.getId());
	 user.setUsername(userVo.getUsername());
	 user.setEmail(userVo.getEmail());
	 if(!userVo.getPassword().isEmpty() && userVo.getPassword() != null)
	 user.setPassword(bCryptPasswordEncoder.encode(userVo.getPassword()));
	 user = userRepository.save(user);
	 if(user.getEmail() != null  && !user.getEmail().isEmpty())
	 sendEmail(user);
	return UserConverter.toVo(user);
	
}

void sendEmail(User user) {
    SimpleMailMessage msg = new SimpleMailMessage();
    msg.setTo(user.getEmail());
    msg.setSubject("modifications effectuées sur votre profil ");
    msg.setText("votre nouveau profil \n username : " + user.getUsername() + "\n email : " + user.getEmail() + "\n password et roles : contacter l'administrateur");
    javaMailSender.send(msg);
}

@Override
public boolean isUniq(String s) {
	User user = userRepository.findByUsername(s);
	if(user != null) {
		return false;
	}
	return true;
}

@Override
@Transactional
public void delete(Long id1) {
	User u = userRepository.getOne(id1);
	if(u != null) {
		u.getRoles().clear();
		userRepository.save(u);
		userRepository.delete(u);
	}
	
}

}
