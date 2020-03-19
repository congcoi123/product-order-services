/*
The MIT License

Copyright (c) 2019 kong <congcoi123@gmail.com>

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
package com.wishop.authrole.controllers;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.wishop.authrole.controllers.api.Api;
import com.wishop.authrole.controllers.api.Version1;
import com.wishop.authrole.entities.Credential;
import com.wishop.authrole.entities.request.AssignRoleRequest;

@RequestMapping(Api.CREDENTIALS)
@Validated
@PreAuthorize("denyAll")
public interface CredentialInterface {

	@GetMapping({ Version1.PATH })
	@PreAuthorize("hasRole('PERM_READ_CREDENTIAL')")
	ResponseEntity<Object> getAllCredentials();

	@GetMapping({ Version1.GETS_BY_PAGE_AND_LIMIT })
	@PreAuthorize("hasRole('PERM_READ_CREDENTIAL')")
	ResponseEntity<Object> getAllCredentials(@PathVariable("page") @NotNull @Min(1) int page,
			@PathVariable("limit") @NotNull @Min(1) @Max(250) int limit);

	@GetMapping({ Version1.GETS_ROLE_BY_USERNAME })
	@PreAuthorize("hasRole('PERM_READ_CREDENTIAL')")
	ResponseEntity<Object> getAllRoles(@PathVariable("username") String userName);

	@GetMapping({ Version1.GETS_PERMISSION_BY_USERNAME })
	@PreAuthorize("hasRole('PERM_READ_CREDENTIAL')")
	ResponseEntity<Object> getAllPermissions(@PathVariable("username") String userName);

	@GetMapping({ Version1.BY_USERNAME })
	@PreAuthorize("hasRole('PERM_READ_CREDENTIAL')")
	ResponseEntity<Object> getCredential(@PathVariable("username") String userName);

	@DeleteMapping({ Version1.BY_USERNAME })
	@PreAuthorize("hasRole('PERM_DELETE_CREDENTIAL')")
	ResponseEntity<Object> deleteCredential(@PathVariable("username") String userName);

	@PostMapping({ Version1.PATH })
	@PreAuthorize("hasRole('PERM_WRITE_CREDENTIAL')")
	ResponseEntity<Object> saveCredential(@RequestBody Credential credential);

	@PostMapping({ Version1.ASSIGN })
	@PreAuthorize("hasRole('PERM_WRITE_CREDENTIAL')")
	ResponseEntity<Object> assignRole(@Valid @RequestBody AssignRoleRequest assignRequest);

}
