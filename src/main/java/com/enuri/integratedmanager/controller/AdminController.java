package com.enuri.integratedmanager.controller;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.enuri.integratedmanager.core.job.SchedulerUtil;
import com.enuri.integratedmanager.quartz.JobScheduler;
import com.enuri.integratedmanager.quartz.job.interfaces.ScheduleJob;
import com.enuri.integratedmanager.quartz.job.interfaces.ScheduleParameterJob;

/**
 * @description : 관리차원에서 필요한 사항들 모음
 * @Since : 2020. 11. 9
 * @Author : AnJaeKoo
 * @History :
 */

@Controller
@RequestMapping("admin")
public class AdminController {

	@Autowired
	private JobScheduler jobScheduler;
	@Autowired
	private ScheduleJob schedulerInitialize;
	@Autowired
	private ScheduleJob h2DbBackup;
	@Autowired
	private ScheduleParameterJob scheduleImmediateRun;

	@ResponseBody
	@RequestMapping(value="/schedule/refresh", produces=MediaType.TEXT_PLAIN_VALUE) // 개행 처리 됨
	public String refreshScheduleList() {
		return jobScheduler.refreshScheduleList();
	}

	@ResponseBody
	@RequestMapping("/schedule/init")
	public String scheduleListInit() {
		return schedulerInitialize.start(); // 스케줄 초기화
	}

	@ResponseBody
	@RequestMapping(value="/schedule/status", produces=MediaType.TEXT_PLAIN_VALUE)
	public String scheduleStatus() {
		return SchedulerUtil.printLog(SchedulerUtil.getScheduler(), false);
	}

	@ResponseBody
	@RequestMapping(value="/schedule/run/{jobName},{id}", produces=MediaType.TEXT_PLAIN_VALUE)
	public String run(@PathVariable("jobName") String jobName, @PathVariable("id") String id) {
		HashMap<String, String> hashMap = new HashMap<>();
		hashMap.put("jobName", jobName);
		hashMap.put("id", id);
		return scheduleImmediateRun.start(hashMap);
	}

	@ResponseBody
	@RequestMapping("/h2dbbackup")
	public String h2DbBackup() {
		return h2DbBackup.start();
	}
}
