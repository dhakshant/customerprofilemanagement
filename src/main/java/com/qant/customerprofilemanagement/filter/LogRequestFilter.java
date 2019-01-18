package com.qant.customerprofilemanagement.filter;

import static net.logstash.logback.marker.Markers.appendFields;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.MDC;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Order(1)
@Component
@ConditionalOnProperty(value = "enableLogRequestFilter", havingValue = "true")
public class LogRequestFilter extends OncePerRequestFilter {

	private static final String X_CORRELATION_ID = "X-Correlation-Id";
	private static final String X_REQUEST_ID = "X-Request-Id";
	private static final String CORRELATION_ID = "correlationId";
	private static final String ZERO = "0";
	protected static final String X_FORWARDED_FOR = "X-Forwarded-For";
	protected static final String USER_AGENT = "User-Agent";
	protected static final String REFERER = "Referer";
	protected static final String CONTENT_LENGTH = "Content-Length";
	protected static final String EXPIRES = "Expires";
	protected static final String CACHE_CONTROL = "Cache-Control";
	protected static final String CACHE_CONTROL_VALEUS
	= "no-store, no-cache, must-revalidate, proxy-revalidate";
	protected static final String PRAGMA = "Pragma";
	protected static final String NO_CACHE = "no-cache";

	@Override
	public final void doFilterInternal(final HttpServletRequest request,
			final HttpServletResponse response,
			final FilterChain filterChain) throws ServletException, IOException {

		setMdcValues(request);
		response.setHeader(EXPIRES, ZERO);
		response.setHeader(CACHE_CONTROL, CACHE_CONTROL_VALEUS);
		response.setHeader(PRAGMA, NO_CACHE);
		long start = System.currentTimeMillis();
		filterChain.doFilter(request, response);
		final AccessLog accessLog = createAccessLog(request, response, start);
		log.info(appendFields(accessLog), null);
	}

	protected AccessLog createAccessLog(final HttpServletRequest request,
			final HttpServletResponse response,
			final long start) {
		return AccessLog.builder().srcIp(request.getRemoteAddr()).xForwardedFor(
				request.getHeader(X_FORWARDED_FOR))
				.httpMethod(request.getMethod()).uri(request.getRequestURI())
				.queryString(request.getQueryString())
				.protocol(request.getProtocol()).status(response.getStatus())
				.responseTime(System.currentTimeMillis() - start).userAgent(
						request.getHeader(USER_AGENT))
				.contentType(request.getContentType()).referer(
						request.getHeader(REFERER)).build();
	}

	protected void setMdcValues(final HttpServletRequest request) {
		MDC.put(CORRELATION_ID, null);
		final Optional<String> correlationId = Optional.ofNullable(
				request.getHeader(X_CORRELATION_ID));
		MDC.put(CORRELATION_ID, correlationId
				.orElse(Optional.ofNullable(request.getHeader(X_REQUEST_ID))
						.orElse(UUID.randomUUID().toString())));
	}
}
