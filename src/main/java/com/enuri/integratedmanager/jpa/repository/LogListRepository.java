package com.enuri.integratedmanager.jpa.repository;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.enuri.integratedmanager.jpa.entity.LogList;
import com.enuri.integratedmanager.jpa.repository.generic.GenericRepository;

@Repository
public interface LogListRepository extends GenericRepository<LogList> {

	@Transactional(readOnly = true)
	public List<LogList> findBySchduleId(int schduleId);

	@Transactional
	public Long removeBySchduleId(String schduleId);
}
