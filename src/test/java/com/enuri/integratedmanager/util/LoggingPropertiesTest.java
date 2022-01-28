package com.enuri.integratedmanager.util;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Component
@ConfigurationProperties("logging")
public class LoggingPropertiesTest {
	private String config;
}
