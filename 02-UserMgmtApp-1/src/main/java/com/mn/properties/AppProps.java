package com.mn.properties;

import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

 @Data
@Configuration
@ConfigurationProperties(prefix = "app")
@EnableConfigurationProperties	
public class AppProps {
	private Map<String, String> message = new HashMap<>();

}
