package com.enuri.integratedmanager.quartz.job.impl;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.enuri.integratedmanager.core.util.DateUtil;
import com.enuri.integratedmanager.core.util.FileUtil;
import com.enuri.integratedmanager.core.util.MessageUtil;
import com.enuri.integratedmanager.quartz.job.interfaces.ScheduleJob;

import lombok.extern.slf4j.Slf4j;

/**
  * @description : H2Db 파일  백업
  * @Since : 2020. 11. 2
  * @Author : AnJaeKoo
  * @History :
  */

@Slf4j
@Component
public class H2DbBackup implements ScheduleJob {

	@Value("${spring.profiles.active}")
	private String activeProfile;



	@Value("${spring.datasource.hikari.jdbc-url}")
	private String h2url;

	@Value("${spring.h2.backup-path}")
	private String backupPath;

	@Value("${spring.h2.cut-day}")
	private int cutDay;

	@Value("${spring.h2.db-file-path}")
	private String filePath;

	@Value("${spring.h2.db-file-name}")
	private List<String> fileNameList;

	@Autowired
	private MessageUtil messageUtil;

	@Override
	public String start() {	// 접속 url : jdbc:h2:file:/data/groupMatching/IntegratedManager/h2_db/log
		// Profile h2일때 db 백업구동
		if (isProfile("h2")) {
			backup();
			FileUtil.oldFileDelete(backupPath, cutDay);
		}

		return messageUtil.getMessage("backup.complete");
	}

	public void backup() {
		FileUtil.mkdir(backupPath);	// 백업 폴더 생성

		// 파일 하위 폴더로 백업
		fileNameList.forEach(name -> {
			File file = new File(filePath + name);

			try {
				if (file.exists()) {
					FileUtil.copyFileBufferedReader(file.getPath(), backupPath + getBackupFileName(file.getName()));	//파일 복사
				} else {
					log.error("{} : {}", messageUtil.getMessage("backup.none"), file.getPath());
				}
			} catch (IOException e) {
				// windows에서는 동시 파일 읽기가 안되서 에러난다.
				if (!isProfile("local")) {
					log.error("", e);
				}
			}

		});
	}

	private String getBackupFileName(String fileName) {
		return fileName.replace(".db", "_" + DateUtil.getDateString("yyyyMMdd") + ".db");
	}

	// profile 체크
	private boolean isProfile(String profile) {
		return Arrays.asList(activeProfile.split(",")).contains(profile);
	}
}
