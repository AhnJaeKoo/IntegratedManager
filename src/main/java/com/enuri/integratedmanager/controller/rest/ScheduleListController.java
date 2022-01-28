package com.enuri.integratedmanager.controller.rest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.enuri.integratedmanager.controller.rest.generic.JpaGenericController;
import com.enuri.integratedmanager.jpa.entity.ScheduleList;
import com.enuri.integratedmanager.jpa.repository.ScheduleListRepository;

/**
  * @description : ScheduleList 부가기능 구현
  * @Since : 2020. 11. 14
  * @Author : AnJaeKoo
  * @History :
  */

@RestController
@RequestMapping("/scheduleList")
public class ScheduleListController extends JpaGenericController<ScheduleListRepository, ScheduleList> {
}
