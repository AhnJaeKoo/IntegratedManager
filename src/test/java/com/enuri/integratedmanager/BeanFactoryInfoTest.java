package com.enuri.integratedmanager;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.boot.test.context.SpringBootTest;

@RunWith(SpringRunnerTest.class)
@SpringBootTest
public class BeanFactoryInfoTest {

	@Autowired
	private DefaultListableBeanFactory beanFactory;

	@Test
	public void beans() {

		for (String name : beanFactory.getBeanDefinitionNames()) {
			System.out.println("BeanName : " + name);
			System.out.println("name : " + beanFactory.getBean(name).getClass().getSimpleName());
		}
	}
}
