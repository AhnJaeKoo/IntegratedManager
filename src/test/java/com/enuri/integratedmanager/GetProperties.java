package com.enuri.integratedmanager;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;

import com.enuri.integratedmanager.util.LoggingPropertiesTest;

@RunWith(SpringRunnerTest.class)
@SpringBootTest
public class GetProperties {

	@Autowired
	private Environment environment;

	@Autowired
	private LoggingPropertiesTest loggingPropertiesTest;

	@Value("${logging.config}")
	String logConfig;

	@Test
	public void contextLoads() {

		System.out.println(environment.getProperty("spring.profiles.active"));

		String config = loggingPropertiesTest.getConfig();

		assertThat(config, is("classpath:conf/log/logback-spring.xml"));
		assertThat(config, is(logConfig));

	}

	@After	//종료시 실행
    public void After() {
		System.out.println("After");
    }

	@Before	// 초기실행
    public void Before() {
		System.out.println("Before");
    }
}
