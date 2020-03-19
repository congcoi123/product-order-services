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
package com.wishop.auth.security;

import java.io.IOException;
import java.util.Collections;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.wishop.auth.configurations.TokenConfig;
import com.wishop.auth.entities.Credential;
import com.wishop.auth.services.CredentialService;
import com.wishop.auth.services.TokenService;

public class UsernameAndPasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	// Can not autowire here
	private CredentialService credentialService;

	// We use auth manager to validate the user credentials
	private AuthenticationManager authManager;

	private TokenService tokenService;

	private TokenConfig tokenConfig;

	public UsernameAndPasswordAuthenticationFilter(CredentialService credentialService,
			AuthenticationManager authManager, TokenConfig tokenConfig, TokenService tokenService) {
		this.credentialService = credentialService;
		this.authManager = authManager;
		this.tokenConfig = tokenConfig;
		this.tokenService = tokenService;

		// By default, UsernamePasswordAuthenticationFilter listens to "/login" path.
		// In our case, we use "/auth". So, we need to override the defaults.
		this.setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher(tokenConfig.getUri(), "POST"));
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {

		try {

			// 1. Get credentials from request
			UserCredentials creds = new UserCredentials(request.getHeader("X-Auth-Username"),
					request.getHeader("X-Auth-Password"));

			// 1.1 Check the credential
			if (!credentialService.isExistCredential(creds.getUsername()))
				throw new IOException("User is not found");

			Credential credential = credentialService.getCredential(creds.getUsername());
			if (credential.getUserName().equals(creds.getUsername())
					&& credential.getPassword().equals(creds.getPassword())) {

			} else
				throw new IOException("User or Password is incorrect");

			// 2. Create auth object (contains credentials) which will be used by auth
			// manager
			UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(creds.getUsername(),
					creds.getPassword(), Collections.emptyList());

			// 3. Authentication manager authenticate the user, and use
			// UserDetialsServiceImpl::loadUserByUsername() method to load the user.
			return authManager.authenticate(authToken);

		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	// Upon successful authentication, generate a token.
	// The 'auth' passed to successfulAuthentication() is the current authenticated
	// user.
	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication auth) throws IOException, ServletException {

		String token = tokenService.generateToken(auth.getName());
		tokenService.store(token, auth.getName());

		// Add token to header
		response.addHeader(tokenConfig.getHeader(), tokenConfig.getPrefix() + token);
	}

	/*
	 * @see org.springframework.security.web.authentication.
	 * AbstractAuthenticationProcessingFilter#unsuccessfulAuthentication(javax.
	 * servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse,
	 * org.springframework.security.core.AuthenticationException)
	 */
	@Override
	protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException failed) throws IOException, ServletException {
		response.sendError(HttpServletResponse.SC_UNAUTHORIZED, failed.getMessage());
	}

	// A (temporary) class just to represent the user credentials MUST BE STATIC!
	private static class UserCredentials {
		private String username;
		private String password;

		public UserCredentials(String username, String password) {
			this.username = username;
			this.password = password;
		}

		public String getUsername() {
			return username;
		}

		public String getPassword() {
			return password;
		}
	}

}
