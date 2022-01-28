package com.enuri.integratedmanager.core.util;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

@Component
public class MessageUtil {

	@Autowired
    private MessageSource messageSource;
	private final Locale locage = Locale.KOREA;

	public String getMessage(String code) {
		return messageSource.getMessage(code, null, locage);
	}

}
