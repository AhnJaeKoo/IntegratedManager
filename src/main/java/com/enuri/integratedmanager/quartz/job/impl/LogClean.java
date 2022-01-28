package com.enuri.integratedmanager.quartz.job.impl;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.enuri.integratedmanager.core.templateengine.Velocity;
import com.enuri.integratedmanager.core.util.DateUtil;
import com.enuri.integratedmanager.core.util.SftpUtil;
import com.enuri.integratedmanager.jpa.entity.LogList;
import com.enuri.integratedmanager.jpa.entity.ServerInfo;
import com.enuri.integratedmanager.jpa.repository.LogListRepository;
import com.enuri.integratedmanager.jpa.repository.ServerInfoRepository;
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
public class LogClean implements ScheduleJob {
	@Autowired
	private LogListRepository logListRepository;

	@Autowired
	private ServerInfoRepository serverInfoRepository;

	@Value("${templates.logclean-path}")
	private String templatePath;

	@Override
	public String start() {
		StringBuilder resultBuffer = new StringBuilder();
		StringBuilder result = new StringBuilder();

		List<LogList> logLists = logListRepository.findAll();

		for (LogList logList : logLists) {
			HashMap<String, Object> map = new HashMap<>();

			map.put("backupPath", logList.getBackupPath());
	        map.put("delCycle", logList.getDelCycle());
	        map.put("fileExtension", logList.getFileExtension());
	        map.put("directoryDel", logList.getDirectoryDel());
	        map.put("backupFile", getBackupFileName(logList.getCutLogFile(), logList.getFileExtension())); // 백업 파일명 가져오기
			map.put("cutLogFile", logList.getCutLogPath() + "/" + logList.getCutLogFile());

			result.setLength(0);

			// 디렉토리 삭제
	        if ("Y".equals(logList.getDirectoryDel())) {
		        result.append(runCmd("directoryDelete.vm", map, logList.getServerId()));
	        }

	        // 로그 분리
			if ("Y".equals(logList.getDayCut())) {
				result.append("\r\n" + runCmd("cut.vm", map, logList.getServerId()));
			}

			result.append("\r\n" + runCmd("fileDelete.vm", map, logList.getServerId()));	// 로그삭제 명령어

			resultBuffer.append("LogClean LogId : " + logList.getId());
			resultBuffer.append(" - 결과 : " + result);
			resultBuffer.append("\r\n");
		}

		log.info(resultBuffer.toString());
		return resultBuffer.toString();
	}

	private String runCmd(String template, HashMap<String, Object> map, Long serverId) {
		StringBuilder command = new StringBuilder();

		command.append(Velocity.getTemplate(templatePath + template, map));
		return getFtpUtil(serverId).runCommand(command.toString());
	}

	private SftpUtil getFtpUtil(Long serverId) {
		ServerInfo serverInfo = serverInfoRepository.findById(serverId).get();	//접속정보 추출
		return new SftpUtil(serverInfo.getUser(), serverInfo.getPassword(), serverInfo.getIp(), serverInfo.getPort());	// 채널 생성
	}

	private String getBackupFileName(String fileName, String fileExtension) {
		return fileName.replace("." + fileExtension, "") + "_" + DateUtil.getDateString("yyyyMMdd") + "." + fileExtension;
	}
}
