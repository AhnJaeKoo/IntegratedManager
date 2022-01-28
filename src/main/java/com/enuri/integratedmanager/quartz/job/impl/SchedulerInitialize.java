package com.enuri.integratedmanager.quartz.job.impl;

import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.enuri.integratedmanager.core.job.SchedulerUtil;
import com.enuri.integratedmanager.core.util.MessageUtil;
import com.enuri.integratedmanager.quartz.job.interfaces.ScheduleJob;

import lombok.extern.slf4j.Slf4j;

/**
  * @description : 등록된 스케줄러 초기화
  * @Since : 2020. 11. 3
  * @Author : AnJaeKoo
  * @History :
  */

@Slf4j
@Component
public class SchedulerInitialize implements ScheduleJob {
	@Autowired
	private MessageUtil messageUtil;

	@Override
	public String start() {
		Scheduler scheduler = SchedulerUtil.getScheduler();	//get static scheduler

		try {
			scheduler.clear();
		} catch (SchedulerException e) {
			log.error(messageUtil.getMessage("job.schedule.error"), e);
		}

		return SchedulerUtil.printLog(scheduler, true);	// 삭제된 리스트 확인
	}
}
