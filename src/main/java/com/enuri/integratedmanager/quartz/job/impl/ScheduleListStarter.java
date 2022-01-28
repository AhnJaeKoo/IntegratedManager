package com.enuri.integratedmanager.quartz.job.impl;

import java.util.List;
import java.util.Optional;

import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.TriggerBuilder;
import org.quartz.impl.JobDetailImpl;
import org.springframework.stereotype.Component;

import com.enuri.integratedmanager.core.job.SchedulerUtil;
import com.enuri.integratedmanager.core.util.StringUtil;
import com.enuri.integratedmanager.jpa.entity.ScheduleList;
import com.enuri.integratedmanager.jpa.entity.ServerInfo;
import com.enuri.integratedmanager.jpa.repository.ScheduleListRepository;
import com.enuri.integratedmanager.jpa.repository.ServerInfoRepository;
import com.enuri.integratedmanager.quartz.job.interfaces.ScheduleJob;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @description : ScheduleList 조회하여 scheduler에 등록한다.
  * @Since : 2020. 11. 2
  * @Author : AnJaeKoo
  * @History :
  */

@Slf4j
@Component
@RequiredArgsConstructor
public class ScheduleListStarter implements ScheduleJob {
	private final ScheduleListRepository scheduleListRepository;
	private final ServerInfoRepository serverInfoRepository;

	@Override
	public String start() {
		Scheduler scheduler = SchedulerUtil.getScheduler();	// 스케줄 가져오기

		//RunCmd 방식
		List<ScheduleList> scheduleLists = scheduleListRepository.findByGroupIdAndUseFlag((long) 1, "Y");
		Job job = new RunCmdJobExecutor();
		scheduleListStarter(scheduler, scheduleLists, job);

		//ApiCall 방식
		scheduleLists = scheduleListRepository.findByGroupIdAndUseFlag((long) 2, "Y");
		job = new ApiCallJobExecutor();

		return scheduleListStarter(scheduler, scheduleLists, job);
	}

	private String scheduleListStarter(Scheduler scheduler, List<ScheduleList> scheduleLists, Job job) {
		for (ScheduleList scheduleList : scheduleLists) {
			if ("Y".equals(scheduleList.getUseFlag())) { // flag 확인
				String jobKey = StringUtil.concatUnderbar(scheduleList.getGroupInfo().getGroupName(),
						scheduleList.getId().toString()); //jobkey 생성
				Optional<ServerInfo> opt = serverInfoRepository.findById(scheduleList.getServerId()); // ServerInfo 조회

				opt.ifPresent(serverInfo -> {
					JobDetailImpl jobDetail = createJobDetail(job, jobKey); // jobDetaul 생성
					jobDetail.setGroup(scheduleList.getId().toString()); // group set
					jobDetail.setDescription(scheduleList.getRemark()); // 비고
					jobDetail = setDataMap(jobDetail, scheduleList, serverInfo, jobKey); // param put

					CronTrigger cronTrigger = createCronTrigger(scheduleList); // CronTrigger 생성

					try {
						scheduler.scheduleJob(jobDetail, cronTrigger);
						scheduler.start(); // 실행
					} catch (SchedulerException e) {
						log.error("", e);
					}
				});
			}
		}

		return SchedulerUtil.printLog(scheduler, true);
	}

	private CronTrigger createCronTrigger(ScheduleList scheduleList) {
		return TriggerBuilder.newTrigger()
				.withIdentity(scheduleList.getGroupInfo().getGroupName(), scheduleList.getId().toString())
				.withSchedule(CronScheduleBuilder.cronSchedule(scheduleList.getCron().trim()))
				.build();
	}

	private JobDetailImpl createJobDetail(Job job, String jobKey) {
		return (JobDetailImpl) JobBuilder.newJob(job.getClass())
								.withIdentity(jobKey)
								.build();
	}

	// parameter 입력
	private JobDetailImpl setDataMap(JobDetailImpl jobDetail, ScheduleList scheduleList, ServerInfo serverInfo, String jobKey) {
		jobDetail.getJobDataMap().put("ip", serverInfo.getIp());
		jobDetail.getJobDataMap().put("port", serverInfo.getPort());
		jobDetail.getJobDataMap().put("user", serverInfo.getUser());
		jobDetail.getJobDataMap().put("password", serverInfo.getPassword());
		jobDetail.getJobDataMap().put("runCmd", scheduleList.getRunCmd());
		jobDetail.getJobDataMap().put("jobKey", jobKey);
		jobDetail.getJobDataMap().put("baseUrl", scheduleList.getBaseUrl());
		jobDetail.getJobDataMap().put("uri", scheduleList.getUri());
		jobDetail.getJobDataMap().put("parameter", scheduleList.getParameter());

		return jobDetail;
	}
}