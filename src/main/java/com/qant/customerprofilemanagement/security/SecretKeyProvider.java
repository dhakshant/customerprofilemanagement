package com.qant.customerprofilemanagement.security;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;

import org.springframework.stereotype.Component;

import com.qant.customerprofilemanagement.util.Constants;

/**
 * Based on
 * http://www.java2s.com/Code/Java/Security/RetrievingaKeyPairfromaKeyStore.htm
 */
@Component
public class SecretKeyProvider {

	public String getKey() throws URISyntaxException, KeyStoreException,
	IOException, NoSuchAlgorithmException, CertificateException, UnrecoverableKeyException {
		return new String(getKeyPair().getPublic().getEncoded(), Constants.UTF_8);
	}

	private KeyPair getKeyPair() throws KeyStoreException,
	IOException, NoSuchAlgorithmException, CertificateException, UnrecoverableKeyException {
		FileInputStream is = new FileInputStream(Constants.KEY);

		KeyStore keystore = KeyStore.getInstance(KeyStore.getDefaultType());
		keystore.load(is, Constants.MYPASS.toCharArray());

		String alias = Constants.MYKEYS;

		Key key = keystore.getKey(alias, Constants.MYPASS.toCharArray());
		if (key instanceof PrivateKey) {
			// Get certificate of public key
			Certificate cert = keystore.getCertificate(alias);

			// Get public key
			PublicKey publicKey = cert.getPublicKey();

			// Return a key pair
			return new KeyPair(publicKey, (PrivateKey) key);
		} else {
			throw new UnrecoverableKeyException();
		}
	}
}
