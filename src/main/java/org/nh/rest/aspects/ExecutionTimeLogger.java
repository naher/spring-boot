package org.nh.rest.aspects;

import org.apache.log4j.Logger;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Aspect
@Component
public class ExecutionTimeLogger {

    protected final Logger logger = Logger.getLogger(ExecutionTimeLogger.class);

    @Pointcut("@annotation(org.springframework.web.bind.annotation.RequestMapping)")
    public void requestMapping() {
    }

    @Pointcut("execution(* org.nh.rest.controllers.*Controller.*(..))")
    public void methodPointcut() {
    }

    @Before("requestMapping() && methodPointcut()")
    public void before() {
        logger.info("BEFORE");
    }

    @After("requestMapping() && methodPointcut()")
    public void after() {
        logger.info("AFTER");
    }

    @Around("requestMapping() && methodPointcut()")
    public Object profile(ProceedingJoinPoint pjp) throws Throwable {
        StopWatch sw = new StopWatch();
        String name = pjp.getSignature().getName();
        try {
            logger.info("STARTWATCH: - " + name);
            sw.start();
            return pjp.proceed();
        } finally {
            sw.stop();
            logger.info("STOPWATCH: " + sw.shortSummary() + " - " + name);
        }
    }
}
