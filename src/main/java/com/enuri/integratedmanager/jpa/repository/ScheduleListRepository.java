package com.enuri.integratedmanager.jpa.repository;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.enuri.integratedmanager.jpa.entity.ScheduleList;
import com.enuri.integratedmanager.jpa.repository.generic.GenericRepository;

@Repository
public interface ScheduleListRepository extends GenericRepository<ScheduleList> {

	@Transactional(readOnly = true)
	public List<ScheduleList> findByGroupIdAndUseFlag(Long groupId, String useFlag);
}
