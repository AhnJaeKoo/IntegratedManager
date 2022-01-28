package com.enuri.integratedmanager.config.quartz;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

/**
  * @description : Quartz config
  * @Since : 2020. 11. 14
  * @Author : AnJaeKoo
  * @History :
  */

@Configuration
@EnableScheduling	//스케줄링 사용 알림
public class ScheduleConfig {

	@Value("${job.pool-size}")
	private int poolSize;

	@Bean
	public TaskScheduler scheduler() {
		ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
		scheduler.setPoolSize(poolSize);
		return scheduler;
	}
}