package com.enuri.integratedmanager;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;

@RunWith(SpringRunnerTest.class)
@SpringBootTest
public class IntegratedManagerApplicationTests {

	@Autowired
	private Environment environment;

	@Test
	public void contextLoads() {

		System.out.println(environment.getProperty("spring.profiles.active"));
	}

	@After	//종료시 실행
    public void cleanup() {

    }

}
