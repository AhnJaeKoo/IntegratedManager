package com.enuri.integratedmanager;

import org.springframework.boot.Banner.Mode;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableAsync;

import com.enuri.integratedmanager.core.RunSetup;
import com.enuri.integratedmanager.core.banner.BannerPrinter;

//@EnableBatchProcessing // Spring Batch 실행 설정
//@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class})
@EnableAspectJAutoProxy	// AOP
@SpringBootApplication
public class IntegratedManagerApplication {

	public static void main(String[] args) {
		//SpringApplication.run(IntegratedManagerApplication.class, args);
		new RunSetup().setProperties(); //spring.config.location 주입
		SpringApplication app = new SpringApplication(IntegratedManagerApplication.class);
		app.setBannerMode(Mode.OFF);
		ConfigurableApplicationContext context = app.run(args);
	    app.setBannerMode(Mode.CONSOLE);
	    new BannerPrinter(context).print(IntegratedManagerApplication.class);
	}
}