package com.enuri.integratedmanager.core.util;

import static com.enuri.integratedmanager.core.context.ApplicationContextProvider.getApplicationContext;
import org.springframework.context.ApplicationContext;
import lombok.experimental.UtilityClass;

@UtilityClass
public class BeanUtil {

	public Object getBean(Class<?> classType) {
        ApplicationContext applicationContext = getApplicationContext(); // Container 받아오기
        return applicationContext.getBean(classType); // Container에서 param의 class에 해당하는 bean 가져오고 return
    }

	public Object getBean(String beanName) {
        ApplicationContext applicationContext = getApplicationContext();
        return applicationContext.getBean(beanName);
    }

	public void printBean() {
		ApplicationContext applicationContext = getApplicationContext();

		for (String name : applicationContext.getBeanDefinitionNames()) {
			System.out.println("BeanName : " + name);
			System.out.println("name : " + applicationContext.getBean(name).getClass().getSimpleName());
		}
	}
}