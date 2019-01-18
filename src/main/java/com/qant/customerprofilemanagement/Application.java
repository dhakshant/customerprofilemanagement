package com.qant.customerprofilemanagement;

import java.util.Arrays;

import javax.security.auth.login.AccountException;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.client.RestTemplate;

import com.qant.customerprofilemanagement.model.OauthAccount;
import com.qant.customerprofilemanagement.service.OauthAccountService;
import com.qant.customerprofilemanagement.util.Constants;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@EnableCircuitBreaker
@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	public RestTemplate restTemplate(final RestTemplateBuilder builder) {
		return builder.build();
	}

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	@Qualifier("mainDataSource")
	public DataSource dataSource() {
		EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
		return builder.setType(EmbeddedDatabaseType.H2).build();
	}

	@Bean
	CommandLineRunner init(final OauthAccountService accountService) {
		return evt	 -> Arrays.asList(Constants.USERNAMES.split(",")).forEach(username -> {
			OauthAccount acct = new OauthAccount();
			acct.setUsername(username);
			if (username.equals(Constants.TESTUSER1)) {
				acct.setPassword(Constants.PASS1);
			}
			if (username.equals(Constants.TESTUSER2)) {
				acct.setPassword(Constants.PASS2);
			}
			acct.setFirstName(username);
			acct.setLastName(Constants.LASTNAME);
			acct.grantAuthority(Constants.ROLE_USER);
			if (username.equals(Constants.ADMIN)) {
				acct.grantAuthority(Constants.ROLE_ADMIN);
			}
			try {
				accountService.register(acct);
			} catch (AccountException e) {
				log.error("AccountException: "+e.getMessage());
			}
		});
	}
}
