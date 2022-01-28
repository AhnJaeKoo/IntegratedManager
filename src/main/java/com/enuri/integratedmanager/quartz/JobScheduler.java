package com.enuri.integratedmanager.quartz;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.enuri.integratedmanager.core.job.SchedulerUtil;
import com.enuri.integratedmanager.quartz.job.interfaces.ScheduleJob;

/**
  * @description : Quartz Scheduler main
  * @Since : 2020. 11. 14
  * @Author : AnJaeKoo
  * @History :
  */

@Component
public class JobScheduler {

	@Autowired
	private ScheduleJob schedulerInitialize;
	@Autowired
	private ScheduleJob scheduleListStarter;
	@Autowired
	private ScheduleJob logClean;
	@Autowired
	private ScheduleJob h2DbBackup;

	@PostConstruct
	private void init() {
		refreshScheduleList();	// 초기화로 스케줄 실행 시킨다.
	}

	//@Scheduled(fixedDelay = 1000)	//1초마다
	//@Scheduled(cron = "*/2 * * * * *")	//2초마다
	@Scheduled(cron = "${job.schedule-refresh.cron}")
	public String refreshScheduleList() {
		schedulerInitialize.start();  // 스케줄 초기화
		scheduleListStarter.start();  // scheduleList 조회하여 적용
		return SchedulerUtil.printLog(SchedulerUtil.getScheduler(), false);
	}

	@Scheduled(cron = "${job.dbbackup.cron}")
	public String h2DbBackup() {
		return h2DbBackup.start();
	}

	@Scheduled(cron = "${job.log-clean.cron}")
	public String logClean() {
		return logClean.start();
	}
}