package com.enuri.integratedmanager;

import org.junit.runners.model.InitializationError;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.enuri.integratedmanager.core.RunSetup;

public class SpringRunnerTest extends SpringJUnit4ClassRunner {

	public SpringRunnerTest(Class<?> clazz) throws InitializationError {
		super(clazz);
		new RunSetup().setProperties(); //spring.config.location 주입
	}
}
