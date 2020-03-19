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
package com.wishop.auth.security.encrypt;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Signature;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

import com.google.common.io.Resources;

public final class USignature {

	private static final String DSA_PRIVATE_KEY = "keys/priKeyDSA.bin";
	private static final String DSA_PUBLIC_KEY = "keys/pubKeyDSA.bin";
	private static final String DSA = "DSA";
	private static final String UTF_8 = "UTF-8";

	private static volatile USignature instance;

	public static USignature getInstance() {
		if (instance == null) {
			synchronized (USignature.class) {
				if (instance == null)
					instance = new USignature();
			}
		}
		return instance;
	}

	private Signature signer;
	private Signature checker;

	private USignature() {
		// Load private key for signing
		try (InputStream fis = Resources.getResource(DSA_PRIVATE_KEY).openStream()) {
			byte[] b = new byte[fis.available()];
			fis.read(b);
			fis.close();

			// Create private key
			PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(b);
			KeyFactory factory = KeyFactory.getInstance(DSA);
			PrivateKey priKey = factory.generatePrivate(spec);

			// Sign
			signer = Signature.getInstance(DSA);
			signer.initSign(priKey, new SecureRandom());

		} catch (IOException | NoSuchAlgorithmException | InvalidKeySpecException | InvalidKeyException e) {
			e.printStackTrace();
		}

		// Load public key for checking
		try (InputStream fis = Resources.getResource(DSA_PUBLIC_KEY).openStream()) {
			byte[] b = new byte[fis.available()];
			fis.read(b);
			fis.close();

			// Create public key
			X509EncodedKeySpec spec = new X509EncodedKeySpec(b);
			KeyFactory factory = KeyFactory.getInstance(DSA);
			PublicKey pubKey = factory.generatePublic(spec);

			// Checker
			checker = Signature.getInstance(DSA);
			checker.initVerify(pubKey);

		} catch (IOException | NoSuchAlgorithmException | InvalidKeySpecException | InvalidKeyException e) {
			e.printStackTrace();
		}
	}

	public String sign(String data) {
		if (signer != null) {
			synchronized (signer) {
				try {
					byte byteFile[] = data.getBytes(UTF_8);
					// Insert message into signer
					signer.update(byteFile);
					byte[] bsign = signer.sign();
					// Save signature
					String sign = Base64.getEncoder().encodeToString(bsign);
					return sign;
				} catch (Exception e) {
					e.printStackTrace();
					return null;
				}
			}
		}
		return null;
	}

	public boolean check(String data, String signature) {
		if (checker != null) {
			synchronized (checker) {
				try {
					byte[] byteFile = data.getBytes(UTF_8);
					// Load message to Checker
					checker.update(byteFile);
					byte[] bsign = Base64.getDecoder().decode(signature);
					// Result
					return checker.verify(bsign);
				} catch (UnsupportedEncodingException | IllegalArgumentException | SignatureException e) {
					e.printStackTrace();
					return false;
				}
			}
		}
		return false;
	}

}
