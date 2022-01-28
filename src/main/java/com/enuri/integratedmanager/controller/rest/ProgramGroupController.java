package com.enuri.integratedmanager.controller.rest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.enuri.integratedmanager.controller.rest.generic.JpaGenericController;
import com.enuri.integratedmanager.jpa.entity.ProgramGroup;
import com.enuri.integratedmanager.jpa.repository.ProgramGroupRepository;

import lombok.extern.slf4j.Slf4j;

/**
  * @description : ProgramGroup 부가기능 구현
  * @Since : 2020. 11. 14
  * @Author : AnJaeKoo
  * @History :
  */

@RestController
@RequestMapping("/programGroup")
public class ProgramGroupController extends JpaGenericController<ProgramGroupRepository, ProgramGroup> {}
