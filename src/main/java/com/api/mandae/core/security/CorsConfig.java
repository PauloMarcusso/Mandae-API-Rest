package com.api.mandae.core.security;// Fonte: https://spring.io/blog/2015/06/08/cors-support-in-spring-framework#filter-based-cors-support

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Collections;

@Configuration
public class CorsConfig {

	@Bean
	public FilterRegistrationBean<CorsFilter> corsFilterRegistrationBean() {
		CorsConfiguration config = new CorsConfiguration();
		config.setAllowCredentials(true);
		config.addAllowedOrigin("*");
		config.setAllowedMethods(Collections.singletonList("*"));
		config.setAllowedHeaders(Collections.singletonList("*"));
		
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", config);

		FilterRegistrationBean<CorsFilter> bean = new FilterRegistrationBean<>();
		bean.setFilter(new CorsFilter(source));
		bean.setOrder(Ordered.HIGHEST_PRECEDENCE);
		
		return bean;
	}

}