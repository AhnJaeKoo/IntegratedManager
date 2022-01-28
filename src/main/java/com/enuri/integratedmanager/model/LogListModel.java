package com.enuri.integratedmanager.model;

import lombok.Data;

/**
  * @description : 현재 등록되어 있는 로그 리스트
  * @Since : 2020. 9. 9
  * @Author : AnJaeKoo
  * @History :
  */

@Data
public class LogListModel {
	/*
	 * 프로그램ID
	 * 로그경로
	 * 로그파일명
	 * 일별분리(Y/N)
	 * 삭제주기(초 분 시 일 월 요일)
	 * 백업주기(초 분 시 일 월 요일)
	 * 백업경로
	 */
	private long id;
	private String programId;
	private String serverId;
	private String logPath;
	private String logFilename;
	private String dayCut;
	private int delCycle;
	private int backupCycle;
	private String backupPath;

}
