package com.enuri.integratedmanager.controller.rest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.enuri.integratedmanager.controller.rest.generic.JpaGenericController;
import com.enuri.integratedmanager.jpa.entity.ServerInfo;
import com.enuri.integratedmanager.jpa.repository.ServerInfoRepository;

/**
  * @description : ServerInfo 부가기능 구현
  * @Since : 2020. 11. 14
  * @Author : AnJaeKoo
  * @History :
  */

@RestController
@RequestMapping("/serverInfo")
public class ServerInfoController extends JpaGenericController<ServerInfoRepository, ServerInfo> {}
