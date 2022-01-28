package com.enuri.integratedmanager.core;

import java.io.IOException;
import java.util.Properties;

import org.springframework.core.io.ClassPathResource;

import lombok.extern.slf4j.Slf4j;

/**
  * @description : application.properties 파일 안에 spring.config.location를 읽어서 yml 파일을 여러개 사용가능하도록 설정
  * @Since : 2020. 9. 25
  * @Author : AnJaeKoo
  * @History : 호출위치가 프레임워크 전이기에 spring 방식의 호출이 안된다.
  * classpath 의 파일을 찾아 설정값을 강제로 set 하여 여러 용도에 맞는 yml을 분리하여 사용가능하게 되었다.
  * 폴더안의 파일 /* 을 사용하고 싶었으나 지원안됨.
  */

@Slf4j
public class RunSetup {

	public void setProperties() {
		try {
			Properties properties = new Properties();
			properties.load(new ClassPathResource(Const.APPLICATION_PROPERTIES).getInputStream());
			System.setProperty(Const.SPRING_CONFIG_LOCATION, properties.getProperty(Const.SPRING_CONFIG_LOCATION));
		} catch (IOException e) {
			log.error("", e);
		}
	}
}
