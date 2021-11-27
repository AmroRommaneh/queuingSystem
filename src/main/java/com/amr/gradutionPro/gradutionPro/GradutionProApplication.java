package com.amr.gradutionPro.gradutionPro;

import com.amr.gradutionPro.gradutionPro.Model.ActiveTurns;
import com.amr.gradutionPro.gradutionPro.Model.Turn;
import com.amr.gradutionPro.gradutionPro.filters.AuthFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import javax.swing.*;
import java.util.ArrayList;

@SpringBootApplication
public class GradutionProApplication {

	public static int QueueTime;
	public static int numberofEmptyWindows=3;
	public static int numberOfTurnsInTheQueue=0;
	public static int window=1;
	//array list for who are in the queue
	public static ArrayList<ActiveTurns> turns = new ArrayList<ActiveTurns>();
	public static void main(String[] args) {
		SpringApplication.run(GradutionProApplication.class, args);
	}

//	@Bean
//	public FilterRegistrationBean<CorsFilter> corsFilter() {
//		FilterRegistrationBean<CorsFilter> registrationBean = new FilterRegistrationBean<>();
//		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//		CorsConfiguration config = new CorsConfiguration();
//		config.addAllowedOrigin("*");
//		config.addAllowedHeader("*");
//		source.registerCorsConfiguration("/**", config);
//		registrationBean.setFilter(new CorsFilter(source));
//		registrationBean.setOrder(0);
//		return registrationBean;
//	}
//
//	@Bean
//	public FilterRegistrationBean<AuthFilter> filterRegistrationBean() {
//		FilterRegistrationBean<AuthFilter> registrationBean = new FilterRegistrationBean<>();
//		AuthFilter authFilter = new AuthFilter();
//		registrationBean.setFilter(authFilter);
//		//registrationBean.addUrlPatterns("/api/User/*");
//		return registrationBean;
//	}

}
