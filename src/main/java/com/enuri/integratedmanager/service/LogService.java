package com.enuri.integratedmanager.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.enuri.integratedmanager.dao.LogMapper;
import com.enuri.integratedmanager.jpa.repository.LogListRepository;
import com.enuri.integratedmanager.model.LogListModel;

/**
  * @description : mybatis 전용 Service
  * @Since : 2020. 11. 14
  * @Author : AnJaeKoo
  * @History :
  */
@Service
public class LogService {

	@Autowired
	public LogMapper logMapper;

	public List<LogListModel> selectLogList() {
		return logMapper.selectLogList();
	}

}
