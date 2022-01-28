package com.enuri.integratedmanager.core.templateengine;

import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;

import lombok.experimental.UtilityClass;

@UtilityClass
public class Velocity {

	public String getTemplate (String file, Map<?, ?> map) {
		VelocityContext context = new VelocityContext();
		StringWriter writer = new StringWriter();
		Template template = getVelocityEngine().getTemplate(file);

        context.put("map", map);
        template.merge(context, writer);

        return writer.toString();
	}

	private VelocityEngine getVelocityEngine() {
		VelocityEngine velocityEngine = new VelocityEngine();
		velocityEngine.init(getProperties());	// resources 경로 설정
		return velocityEngine;
	}

	private Properties getProperties() {
		Properties properties = new Properties();
		properties.setProperty("resource.loader", "class");
		properties.setProperty("class.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
		return properties;
	}
}
