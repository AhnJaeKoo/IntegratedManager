package com.enuri.integratedmanager.jpa.repository;

import org.springframework.stereotype.Repository;

import com.enuri.integratedmanager.jpa.entity.ServerInfo;
import com.enuri.integratedmanager.jpa.repository.generic.GenericRepository;

@Repository
public interface ServerInfoRepository extends GenericRepository<ServerInfo> {}
