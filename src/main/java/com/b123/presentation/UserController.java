package com.b123.presentation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.b123.domaine.RoleVo;
import com.b123.domaine.UserVo;
import com.b123.service.IUserService;


@Controller
@RequestMapping("/superadm")
public class UserController {
	
	@Autowired
	private IUserService service;
	
  @RequestMapping("/users")	
  public String getAllUsers(Model m) {
	  List<UserVo> list = service.getAllUsersSA();
	  Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	  m.addAttribute("userName", "Welcome " + auth.getName());
	  m.addAttribute("list", list);
	  return "/user/users";
  }	
  
  @RequestMapping(value = "/update", method = RequestMethod.POST)
  public String update(@ModelAttribute("uv") UserVo emp) {
	  	service.update(emp);
   return "redirect:/superadm/users";
   }

  @RequestMapping("/edit/{id}")	
  public String edit(@PathVariable Long id, Model m) {
	  UserVo uv = service.getUserById(id);
	  List<RoleVo> roles = service.getAllRolesSA();
	  m.addAttribute("uv", uv);
	  m.addAttribute("rooles", roles);
	  return "/user/edit";
  }	
 
  @RequestMapping("/deleteRole/{id1}/{id2}")	
  public String deleteRole(@PathVariable("id1") Long id1,@PathVariable("id2") Integer id2, Model m) {
	  UserVo uv = service.deleteUserRole(id1,id2);
	  List<RoleVo> roles = service.getAllRolesSA();
	  m.addAttribute("uv", uv);
	  m.addAttribute("rooles", roles);
	  return "/user/edit";
  }	
  
  @RequestMapping(value = "/addRole/{id1}/{id2}")
  public String editsave(@PathVariable("id1") Long id1,@PathVariable("id2") Integer id2, Model m) {
	  UserVo uv = service.addUserRole(id1,id2);
	  List<RoleVo> roles = service.getAllRolesSA();
	  m.addAttribute("uv", uv);
	  m.addAttribute("rooles", roles);
	  return "/user/edit";
   }
  
  @RequestMapping(value = "/nouveau")
  public String nouveau(Model m) {
	  List<RoleVo> roles = service.getAllRolesSA();
	  UserVo uv = new UserVo();
	  m.addAttribute("uv", uv);
	  m.addAttribute("rooles", roles);
	  return "/user/form";
   }
  
  @RequestMapping(value = "/delete/{id}")
  public String delete(@PathVariable("id") Long id) {
	   	service.delete(id);
   return "redirect:/superadm/users";
   }
  
  @RequestMapping(value = "/save", method = RequestMethod.POST)
  public String save(@ModelAttribute("uv") UserVo u , Model m) {
	    if(service.isUniq(u.getUsername())) {
	    	service.save(u);
	        return "redirect:/superadm/users";	
	    }else {
	      List<RoleVo> roles = service.getAllRolesSA();
	  	  m.addAttribute("uv", u);
	  	  m.addAttribute("rooles", roles);
	  	  m.addAttribute("error", "username est deja existe ");
	  	  return "/user/form";
	    }
	 
   }
  
}
