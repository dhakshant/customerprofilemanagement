package com.qant.customerprofilemanagement.filter;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.PropertyNamingStrategy.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(SnakeCaseStrategy.class)
@JsonInclude(value = Include.NON_NULL)
@ToString
public class AccessLog {
	private String srcIp;
	private String xForwardedFor;
	private String httpMethod;
	private String uri;
	private String queryString;
	private String protocol;
	private Integer status;
	private Long responseTime;
	private String userAgent;
	private String contentType;
	private String referer;
}