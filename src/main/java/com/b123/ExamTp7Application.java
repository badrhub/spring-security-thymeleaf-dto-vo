package com.b123;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class ExamTp7Application {
	
	public static void main(String[] args)  {
		SpringApplication.run(ExamTp7Application.class, args);
	}

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
	 BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
	 return bCryptPasswordEncoder;
	 }

	   
	
	
	
////    @Autowired
//	private IUserService userService;
//	@Override  implements CommandLineRunner	
//	public void run(String... args) throws Exception {
//		userService.cleanDataBase();
//		 userService.save(new RoleVo("ADMIN"));
//		 userService.save(new RoleVo("CLIENT"));
//		 RoleVo roleAdmin = userService.getRoleByName("SUPER ADMIN");
//		 RoleVo roleClient = userService.getRoleByName("CLIENT");
//		 UserVo admin1 = new UserVo("sadmin", "sadmin", Arrays.asList(roleAdmin));
//		 UserVo client1 = new UserVo("client1", "client1", Arrays.asList(roleClient));
//		 userService.save(admin1);
//		 userService.save(client1);
//		 // *************
//		 empService.save(new EmpVo("emp1", 10000d, "Fonction1"));
//		 empService.save(new EmpVo("emp2", 20000d, "Fonction3"));
//		 empService.save(new EmpVo("emp3", 30000d, "Fonction4"));
//		 empService.save(new EmpVo("emp4", 40000d, "Fonction5"));
//		 empService.save(new EmpVo("emp5", 50000d, "Fonction6")); 
//		
//	}

}
