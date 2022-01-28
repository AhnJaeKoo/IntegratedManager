package com.enuri.integratedmanager.controller.error;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CustomErrorController implements ErrorController {

	@Value("${spring.thymeleaf.error-path}")
	private String errorPath;

	@RequestMapping(value = "/error")
	public String handleError(HttpServletRequest request) {
		Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
		if (status != null) {
			int statusCode = Integer.valueOf(status.toString());
			if (statusCode == HttpStatus.NOT_FOUND.value()) {
				return errorPath + "404";
			}
			if (statusCode == HttpStatus.FORBIDDEN.value()) {
				return errorPath + "403";
			}
		}
		return "error";
	}
}
