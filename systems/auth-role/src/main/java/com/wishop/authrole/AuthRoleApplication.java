/*
The MIT License

Copyright (c) 2019-2020 kong <congcoi123@gmail.com>

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in
all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
THE SOFTWARE.
*/
package com.wishop.authrole;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

import com.wishop.authrole.entities.Credential;
import com.wishop.authrole.entities.Permission;
import com.wishop.authrole.entities.Role;
import com.wishop.authrole.repository.CredentialRepository;
import com.wishop.authrole.repository.PermissionRepository;
import com.wishop.authrole.repository.RoleRepository;

@SpringBootApplication
public class AuthRoleApplication {

	public static void main(String[] args) {
		SpringApplication.run(AuthRoleApplication.class, args);
	}

	@Profile("development")
	@Bean
	CommandLineRunner initDatabase(PermissionRepository permissionRepository, RoleRepository roleRepository,
			CredentialRepository credentialRepository) {
		return args -> {
			Permission per = new Permission("PERM_READ_NEWS"); // 1
			per.setDeleted(true);
			permissionRepository.save(per);
			permissionRepository.save(new Permission("PERM_WRITE_NEWS")); // 2
			permissionRepository.save(new Permission("PERM_DELETE_NEWS1")); // 3
			permissionRepository.save(new Permission("PERM_DELETE_NEWS2")); // 4
			permissionRepository.save(new Permission("PERM_DELETE_NEWS3")); // 5
			permissionRepository.save(new Permission("PERM_DELETE_NEWS4")); // 6
			permissionRepository.save(new Permission("PERM_DELETE_NEWS5")); // 7
			permissionRepository.save(new Permission("PERM_DELETE_NEWS6")); // 8

			Role role = new Role("Admin");
			role.addPermission(new Permission("PERM_DELETE_NEWS7"));
			role.addPermission(new Permission("PERM_DELETE_NEWS8"));
			roleRepository.save(role);

			Role role2 = new Role("Moder");
			role2.addPermission(new Permission("PERM_DELETE_NEWS9"));
			role2.addPermission(new Permission("PERM_DELETE_NEWS10"));
			role2.addPermission(new Permission("PERM_DELETE_NEWS11"));
			roleRepository.save(role2);

			Credential cre = new Credential("cong", "12345");
            // cre.addRole(role);
            // cre.addRole(role2);
			credentialRepository.save(cre);

		};
	}

}
