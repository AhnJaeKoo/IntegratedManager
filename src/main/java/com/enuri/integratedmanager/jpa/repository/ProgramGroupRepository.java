package com.enuri.integratedmanager.jpa.repository;

import org.springframework.stereotype.Repository;

import com.enuri.integratedmanager.jpa.entity.ProgramGroup;
import com.enuri.integratedmanager.jpa.repository.generic.GenericRepository;

@Repository
public interface ProgramGroupRepository extends GenericRepository<ProgramGroup> {}
