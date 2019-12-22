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
package com.wishop.auth.services;

import org.springframework.scheduling.annotation.Scheduled;

import com.wishop.auth.security.encrypt.USignature;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

public class TokenService {

	private static final Cache restApiAuthTokenCache = CacheManager.getInstance().getCache("restApiAuthTokenCache");
	public static final int HALF_AN_HOUR_IN_MILLISECONDS = 30 * 60 * 1000;

	@Scheduled(fixedRate = HALF_AN_HOUR_IN_MILLISECONDS)
	public void evictExpiredTokens() {
		restApiAuthTokenCache.evictExpiredElements();
	}

	public String generateToken(String username) {
		return USignature.getInstance().sign(username);
	}

	public void store(String token, String credential) {
		restApiAuthTokenCache.put(new Element(token, credential));
	}

	public boolean contains(String token) {
		return restApiAuthTokenCache.get(token) != null;
	}

	public Object retrieve(String token) {
		Element element = restApiAuthTokenCache.get(token);
		if (element == null)
			return null;
		return element.getObjectValue();
	}

}
