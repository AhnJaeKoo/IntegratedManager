package com.enuri.integratedmanager.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.enuri.integratedmanager.model.LogListModel;

@Repository
@Mapper
public interface LogMapper {
	List<LogListModel> selectLogList();
}
