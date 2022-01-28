package com.enuri.integratedmanager.quartz.job.impl;

import java.util.HashMap;

import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.enuri.integratedmanager.core.job.SchedulerUtil;
import com.enuri.integratedmanager.core.util.MapUtil;
import com.enuri.integratedmanager.core.util.MessageUtil;
import com.enuri.integratedmanager.core.util.StringUtil;
import com.enuri.integratedmanager.quartz.job.interfaces.ScheduleParameterJob;

import lombok.extern.slf4j.Slf4j;

/**
  * @description : 스케줄 즉시 실행
  * @Since : 2020. 12. 3
  * @Author : AnJaeKoo
  * @History :
  */

@Slf4j
@Component
public class ScheduleImmediateRun implements ScheduleParameterJob {

	@Autowired
	private MessageUtil messageUtil;

	@Override
	public String start(HashMap<?, ?> map)  {
		String result = "";

		try {
			if (!MapUtil.isEmpty(map)) {
				String jobName = (String) map.get("jobName");
				String id = (String) map.get("id");
				jobName = StringUtil.concatUnderbar(jobName, id);

				int jobStatus = SchedulerUtil.jobCheck(jobName, id);	// job 유효성과 상태 체크
				switch (jobStatus) {
					case -1: result = jobName + " : " + messageUtil.getMessage("job.schedule.none");
							 log.warn(result);
							 throw new SchedulerException(result);
					case  1: result = jobName + " : " + messageUtil.getMessage("job.schedule.running");
						     log.warn(result); break;
					case  0: SchedulerUtil.runJob(jobName, id);	// job 실행
							 result = jobName + " : " + messageUtil.getMessage("job.schedule.complete");
						     log.info(result); break;
					default: break;
				}
			} else {
				result = "Parameter map이 비어있습니다. 다시 확인하여 주시길 바랍니다.";
				log.warn(result);
			}
		} catch (SchedulerException e) {
			log.error("", e);
		}

		return result;
	}

}
