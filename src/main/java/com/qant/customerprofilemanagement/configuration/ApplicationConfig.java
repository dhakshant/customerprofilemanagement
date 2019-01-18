package com.qant.customerprofilemanagement.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "config")
public class ApplicationConfig {
	private String crmApiGatewayBaseUrl;
	private String customerFindByIdUrl;
	private String customerCreateUrl;
	private String customerUpdateUrl;
	private String customerDeleteUrl;
}
