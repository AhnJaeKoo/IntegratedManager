package com.enuri.integratedmanager.quartz.job.impl;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.enuri.integratedmanager.core.util.SftpUtil;

import lombok.extern.slf4j.Slf4j;

/**
  * @description : 원격 명령 JobExecutor
  * @Since : 2020. 11. 14
  * @Author : AnJaeKoo
  * @History :
  */

@Slf4j
public class RunCmdJobExecutor implements Job {

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		// 설정정보 인식
        JobDataMap jobDataMap = context.getJobDetail().getJobDataMap();
        String remark = context.getJobDetail().getDescription();
		String jobKey = jobDataMap.getString("jobKey");
        String ip = jobDataMap.getString("ip").trim();
        String user = jobDataMap.getString("user").trim();
        String password = jobDataMap.getString("password");
        String runCmd = jobDataMap.getString("runCmd").trim();
        int port = jobDataMap.getInt("port");

        log.info("job : {}({}) - Running...", jobKey, remark);
        SftpUtil ftpUtil = new SftpUtil(user, password, ip, port);	// 채널 생성
    	// 명령어 실행
        String result = ftpUtil.runCommand(runCmd);
		log.info("RunCmd job : {} - 결과 : \n{}", jobKey, result);
	}
}
