package com.enuri.integratedmanager.core.job;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.Trigger.TriggerState;
import org.quartz.impl.JobDetailImpl;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.matchers.GroupMatcher;

import lombok.extern.slf4j.Slf4j;

/**
  * @description :	Quartz Job Scheduler 에서 사용할 기능모음
  * @Since : 2020. 12. 3
  * @Author : AnJaeKoo
  * @History :
  */

@Slf4j
public class SchedulerUtil {

	@SuppressWarnings("unchecked")
	public static String printLog(Scheduler scheduler, boolean isLogPrint) {

		StringBuffer buffer = new StringBuffer();

		try {
			for (String groupName : scheduler.getJobGroupNames()) {
				for (JobKey jobKey : scheduler.getJobKeys(GroupMatcher.jobGroupEquals(groupName))) {
					// get job's trigger
					List<Trigger> triggers = (List<Trigger>) scheduler.getTriggersOfJob(jobKey);
					JobDetailImpl jobDetail = (JobDetailImpl) scheduler.getJobDetail(jobKey);
					Date nextFireTime = triggers.get(0).getNextFireTime();
					buffer.append("""
							[jobName] : %s  / [groupName] : %s  / [실행 계획시간] : %s  / ( %s )
							""".formatted(jobKey.getName(),
											jobKey.getGroup(),
											dateToString(nextFireTime),
											jobDetail.getDescription()));
				}
			}

			if (buffer.length() == 0) {
				buffer.append("job Empty!!!");
			}

			if (isLogPrint) {
				log.info(buffer.toString());
			}
		} catch (SchedulerException e) {
			log.error("", e);
		}

		return buffer.toString();
	}


	/**
	  * @description : job 상태 체크
	  * @param :
	  * @return : -1:없는 스케줄, 0:정상, 1:구동중(비정상)
	  */
	public static int jobCheck(String jobName, String id) throws SchedulerException {
		Scheduler scheduler = getScheduler();
		JobDetail jobDetail = scheduler.getJobDetail(new JobKey(jobName, id));

		if (jobDetail == null) {
			return -1;
		}

		List<? extends Trigger> triggers = scheduler.getTriggersOfJob(jobDetail.getKey());
		for (Trigger trigger : triggers) {
			TriggerState triggerState = scheduler.getTriggerState(trigger.getKey());
			if (TriggerState.NORMAL.equals(triggerState)) {
				return 0;
			}
		}
		return 1;
	}

	public static void runJob(String jobName, String id) throws SchedulerException {
		Scheduler scheduler = getScheduler();
		scheduler.triggerJob(new JobKey(jobName, id));
	}

	// 정적 스케줄러 반환
	public static Scheduler getScheduler() {
		try {
			return StdSchedulerFactory.getDefaultScheduler();
		} catch (SchedulerException e) {
			log.error("", e);
			return null;
		}
	}

	public static String dateToString(Date nextFireTime) {
		return new SimpleDateFormat("yyyy-MM-dd(E) HH:mm:ss").format(nextFireTime);
	}
}
