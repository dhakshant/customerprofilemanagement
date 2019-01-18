package com.qant.customerprofilemanagement.repository;

import java.util.Optional;

import org.springframework.data.repository.Repository;

import com.qant.customerprofilemanagement.model.OauthAccount;

public interface OauthAccountRepo extends Repository<OauthAccount, Long> {
	Optional<OauthAccount> findByUsername(String username);

	Integer countByUsername(String username);

	OauthAccount save(OauthAccount account);
}
