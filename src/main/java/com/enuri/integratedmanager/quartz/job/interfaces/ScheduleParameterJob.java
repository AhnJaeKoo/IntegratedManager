package com.enuri.integratedmanager.quartz.job.interfaces;

import java.util.HashMap;

import com.enuri.integratedmanager.core.util.MapUtil;

/**
  * @description : 스케줄 시작시 사용(파라미터 추가 버전)
  * @Since : 2021. 01. 18
  * @Author : AnJaeKoo
  * @History :
  */
public interface ScheduleParameterJob {
	String start(HashMap<?, ?> map);
}
