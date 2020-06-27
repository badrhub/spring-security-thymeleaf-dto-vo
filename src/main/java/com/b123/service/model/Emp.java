package com.b123.service.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Emp {
@Id
@GeneratedValue
private Long id;
private String name;
private Double salary;
private String fonction;
public Emp(String name, Double salary, String fonction) {
 super();
 this.name = name;
 this.salary = salary;
 this.fonction = fonction;
 }
}