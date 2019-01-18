package com.qant.customerprofilemanagement.security;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import com.qant.customerprofilemanagement.service.OauthAccountService;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

	private static final String TOKEN_KEY_ACCESS = "isAnonymous() || hasAuthority('ROLE_TRUSTED_CLIENT')";
	private static final String CHECK_TOKEN_ACCESS = "hasAuthority('ROLE_TRUSTED_CLIENT')";
	private static final String NORMAL_APP = "normal-app";
	private static final String IMPLICIT = "implicit";
	private static final String READ = "read";
	private static final String WRITE = "write";
	private static final String TRUSTED_APP = "trusted-app";
	private static final String CLIENT_CREDENTIALS = "client_credentials";
	private static final String PASSWORD = "password";
	private static final String REFRESH_TOKEN = "refresh_token";
	private static final String ROLE_TRUSTED_CLIENT = "ROLE_TRUSTED_CLIENT";
	private static final String SECRET = "secret";
	private static final String TRUST = "trust";
	private static final String REGISTER_APP = "register-app";
	private static final String ROLE_REGISTER = "ROLE_REGISTER";
	private static final String CLIENT_REGISTERED = "my-client-with-registered-redirect";
	private static final String AUTHORIZATION_CODE = "authorization_code";
	private static final String ROLE_CLIENT = "ROLE_CLIENT";
	private static final String RESOURCEIDS = "oauth2-resource";
	private static final String REDIRECT_URIS = "http://anywhere?key=value";

	@Value("${security.oauth2.resource.id}")
	private String resourceId;

	@Value("${access_token.validity_period}")
	private int accessTokenValiditySeconds;

	@Value("${refresh_token.validity_period}")
	private int refreshTokenValiditySeconds;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Bean
	@Primary
	public UserDetailsService userDetailsService() {
		return new OauthAccountService();
	}

	@Override
	public void configure(final AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		endpoints.authenticationManager(this.authenticationManager).tokenServices(tokenServices())
				.tokenStore(tokenStore()).accessTokenConverter(accessTokenConverter());
	}

	@Override
	public void configure(final AuthorizationServerSecurityConfigurer oauthServer) throws Exception {
		oauthServer.tokenKeyAccess(TOKEN_KEY_ACCESS)
				.checkTokenAccess(CHECK_TOKEN_ACCESS);
	}

	@Override
	public void configure(final ClientDetailsServiceConfigurer clients) throws Exception {
		clients.inMemory().withClient(NORMAL_APP)
				.authorizedGrantTypes(AUTHORIZATION_CODE, IMPLICIT)
				.authorities(ROLE_CLIENT).scopes(READ, WRITE).resourceIds(resourceId)
				.accessTokenValiditySeconds(accessTokenValiditySeconds)
				.refreshTokenValiditySeconds(refreshTokenValiditySeconds)
				.and().withClient(TRUSTED_APP)
				.authorizedGrantTypes(CLIENT_CREDENTIALS, PASSWORD, REFRESH_TOKEN)
				.authorities(ROLE_TRUSTED_CLIENT).scopes(READ, WRITE)
				.resourceIds(resourceId)
				.accessTokenValiditySeconds(accessTokenValiditySeconds)
				.refreshTokenValiditySeconds(refreshTokenValiditySeconds)
				.secret(SECRET).and()
				.withClient(REGISTER_APP).authorizedGrantTypes(CLIENT_CREDENTIALS)
				.authorities(ROLE_REGISTER)
				.scopes(READ).resourceIds(resourceId).secret(SECRET).and()
				.withClient(CLIENT_REGISTERED)
				.authorizedGrantTypes(AUTHORIZATION_CODE)
				.authorities(ROLE_CLIENT).scopes(READ, TRUST)
				.resourceIds(RESOURCEIDS)
				.redirectUris(REDIRECT_URIS);
	}

	@Bean
	public TokenStore tokenStore() {
		return new JwtTokenStore(accessTokenConverter());
	}

	@Autowired
	private SecretKeyProvider keyProvider;

	@Bean
	public JwtAccessTokenConverter accessTokenConverter() {
		JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
		try {
			converter.setSigningKey(keyProvider.getKey());
		} catch (URISyntaxException | KeyStoreException | NoSuchAlgorithmException | IOException
				| UnrecoverableKeyException | CertificateException e) {
			e.printStackTrace();
		}

		return converter;
	}

	@Bean
	@Primary
	public DefaultTokenServices tokenServices() {
		DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
		defaultTokenServices.setTokenStore(tokenStore());
		defaultTokenServices.setSupportRefreshToken(true);
		defaultTokenServices.setTokenEnhancer(accessTokenConverter());
		return defaultTokenServices;
	}

}
