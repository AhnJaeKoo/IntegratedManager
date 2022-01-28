package com.enuri.integratedmanager.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.enuri.integratedmanager.model.TestModel;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class TestController {

	@RequestMapping("/home")
	public String home() {
		return "index";
	}

	@ResponseBody
	@RequestMapping("/testString")
	public String testString() {
		String value = "테스트1";
		return value;
	}

	@RequestMapping("/testMvc")
	public ModelAndView testMvc() throws Exception {
		ModelAndView mav = new ModelAndView("testMvc");	//jsp 파일명
		mav.addObject("name", "goddaehee");
		List<String> testList = new ArrayList<String>();
		testList.add("a");
		testList.add("b");
		testList.add("c");
		mav.addObject("list1", testList);
		return mav;
	}

	@RequestMapping("/thymeleafTest")
	public String thymeleafTest(Model model) {
		TestModel testModel = new TestModel();
		testModel.setId("user1");
		testModel.setName("홍길동");
		model.addAttribute("testModel", testModel);
		return "thymeleaf/thymeleafTest";
	}

	@RequestMapping("/thymeleafSample")
	public String thymeleafSample(Model model) {
		TestModel testModel = new TestModel();
		testModel.setId("user1");
		testModel.setName("홍길동");
		model.addAttribute("testModel", testModel);
		return "thymeleaf/sample";
	}

	@ResponseBody
	@RequestMapping(value = "/ip", method = RequestMethod.GET)
	public String getIp(HttpServletRequest request) {
		return request.getRemoteAddr();
	}
}

