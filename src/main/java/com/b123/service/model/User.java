package com.b123.service.model;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "user")
@NoArgsConstructor
@AllArgsConstructor
public class User {
	
	
@Id
@GeneratedValue
private Long id; 
@Length(min = 5, message = "*Your username must have at least 5 characters")
@NotEmpty(message = "*Please provide your username")
private String username;
@Length(min = 5, message = "*Your password must have at least 5 characters")
@NotEmpty(message = "*Please provide your password")
private String password;
private String email;
@ManyToMany(cascade = CascadeType.ALL)
@JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"),
inverseJoinColumns = @JoinColumn(name = "role_id"))
private List<Role> roles = new ArrayList<Role>();
}
