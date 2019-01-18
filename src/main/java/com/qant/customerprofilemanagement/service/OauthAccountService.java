package com.qant.customerprofilemanagement.service;

import java.util.Optional;

import javax.security.auth.login.AccountException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.qant.customerprofilemanagement.model.OauthAccount;
import com.qant.customerprofilemanagement.repository.OauthAccountRepo;

@Service
public class OauthAccountService implements UserDetailsService {

	@Autowired
	private OauthAccountRepo accountRepo;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public UserDetails loadUserByUsername(final String s)
			throws UsernameNotFoundException {
		Optional<OauthAccount> account = accountRepo.findByUsername(s);
		if (account.isPresent()) {
			return account.get();
		} else {
			throw new UsernameNotFoundException(String.format("Username[%s] not found",
					s));
		}
	}

	public OauthAccount findAccountByUsername(final String username)
			throws UsernameNotFoundException {
		Optional<OauthAccount> account = accountRepo.findByUsername(username);
		if (account.isPresent()) {
			return account.get();
		} else {
			throw new UsernameNotFoundException(String.format("Username[%s] not found",
					username));
		}

	}

	public OauthAccount register(final OauthAccount account) throws AccountException {
		if (accountRepo.countByUsername(account.getUsername()) == 0) {
			account.setPassword(passwordEncoder.encode(account.getPassword()));
			return accountRepo.save(account);
		} else {
			throw new AccountException(String.format("Username[%s] already taken.",
					account.getUsername()));
		}
	}
}
