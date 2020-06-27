package com.b123.domaine;

import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
public class RoleVo {
private int id;
private String role;
public RoleVo(String role) { 
 this.role = role;
 }
}