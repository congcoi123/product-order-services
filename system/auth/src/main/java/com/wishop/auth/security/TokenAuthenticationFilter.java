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
package com.wishop.auth.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.wishop.auth.configurations.TokenConfig;
import com.wishop.auth.security.encrypt.USignature;
import com.wishop.auth.services.TokenService;

public class TokenAuthenticationFilter extends OncePerRequestFilter {

	private TokenConfig tokenConfig;

	private TokenService tokenService;

	public TokenAuthenticationFilter(TokenConfig tokenConfig, TokenService tokenService) {
		this.tokenConfig = tokenConfig;
		this.tokenService = tokenService;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws ServletException, IOException {

		// 1. get the authentication header. Tokens are supposed to be passed in the
		// authentication header
		String header = request.getHeader(tokenConfig.getHeader());

		// 2. validate the header and check the prefix
		if (header == null || !header.startsWith(tokenConfig.getPrefix())) {
			chain.doFilter(request, response); // If not valid, go to the next filter.
			return;
		}

		// If there is no token provided and hence the user won't be authenticated.
		// It's Ok. Maybe the user accessing a public path or asking for a token.

		// All secured paths that needs a token are already defined and secured in
		// config class.
		// And If user tried to access without access token, then he won't be
		// authenticated and an exception will be thrown.

		// 3. Get the token
		String token = header.replace(tokenConfig.getPrefix(), "");

		// 4. Validate the token
		Object ousername = tokenService.retrieve(token);

		if (ousername != null) {
			String username = (String) ousername;

			if (USignature.getInstance().check(username, token)) {
				List<String> authorities = new ArrayList<String>();

				// 5. Create auth object
				// UsernamePasswordAuthenticationToken: A built-in object, used by spring to
				// represent the current authenticated / being authenticated user.
				// It needs a list of authorities, which has type of GrantedAuthority interface,
				// where SimpleGrantedAuthority is an implementation of that interface
				UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(username, null,
						authorities.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList()));

				// 6. Authenticate the user
				// Now, user is authenticated
				SecurityContextHolder.getContext().setAuthentication(auth);
			}
		}

		// go to the next filter in the filter chain
		chain.doFilter(request, response);
	}

}
