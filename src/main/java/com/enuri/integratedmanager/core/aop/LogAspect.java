package com.enuri.integratedmanager.core.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Aspect
@Component
public class LogAspect {

	@Around("execution(* com.enuri.integratedmanager.quartz.job.interfaces.Schedul*Job.start(..))")
    public Object logging(ProceedingJoinPoint pjp) throws Throwable {
        Object result = pjp.proceed();
        log.info("스케줄 실행 클래스(메소드) : {}({}) - 실행결과 : {} ", pjp.getTarget().getClass().getSimpleName(), pjp.getSignature().getName(), result); // 호출 메소드명 실행 완료 로그
        return result;
    }

}
