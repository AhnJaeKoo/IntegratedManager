package com.enuri.integratedmanager.controller.mybatis;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.enuri.integratedmanager.model.LogListModel;
import com.enuri.integratedmanager.service.LogService;

@Controller
public class MybatisController {

	@Autowired
	private LogService logService;

	// mybatis 방식
	@RequestMapping("/logListView")
	public ModelAndView logListView() throws Exception {
		List<LogListModel> testList = logService.selectLogList();
		ModelAndView mva = new ModelAndView();
		mva.addObject("list", testList);

		return mva;
	}
}

