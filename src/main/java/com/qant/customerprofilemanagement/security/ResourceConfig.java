package com.qant.customerprofilemanagement.security;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.web.util.matcher.RequestMatcher;

@Configuration
@EnableResourceServer
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ResourceConfig extends ResourceServerConfigurerAdapter {

	private static final String MOCK_CRMAPI_URL = "/crmapi**";
	private static final String CUSTOMER_URL = "/customer**";
	private static final String ROLE_USER = "hasAnyRole('USER')";

	@Value("${security.oauth2.resource.id}")
	private String resourceId;

	@Autowired
	private DefaultTokenServices tokenServices;

	@Autowired
	private TokenStore tokenStore;

	@Override
	public void configure(final ResourceServerSecurityConfigurer resources) {
		resources.resourceId(resourceId).tokenServices(tokenServices).tokenStore(tokenStore);
	}

	@Override
	public void configure(final HttpSecurity http) throws Exception {
		http.requestMatcher(new OAuthRequestedMatcher()).anonymous().disable().authorizeRequests()
				.antMatchers(HttpMethod.OPTIONS).permitAll()
				.antMatchers(MOCK_CRMAPI_URL).permitAll()
				.antMatchers(CUSTOMER_URL).access(ROLE_USER);
	}

	private static class OAuthRequestedMatcher implements RequestMatcher {
		public boolean matches(final HttpServletRequest request) {
			return true;
		}
	}

}
