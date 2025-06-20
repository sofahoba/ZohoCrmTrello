package com.flakTechTask.zohoCrmTrello.AOP;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

    private static final Logger log = LoggerFactory.getLogger("GlobalServiceLogger");

    @Pointcut("within(com.flakTechTask.zohoCrmTrello.services..*)")
    public void serviceMethods() {}

    @Around("serviceMethods()")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        String methodName = signature.getDeclaringType().getSimpleName() + "." + signature.getName();

        log.info("### Entering method: {}", methodName);
        long start = System.currentTimeMillis();

        try {
            Object result = joinPoint.proceed();
            long duration = System.currentTimeMillis() - start;
            log.info("### Exiting method: {} | Execution time: {} ms", methodName, duration);
            return result;
        } catch (Throwable e) {
            log.error("!!!!! Exception in method: " + methodName, e);
            throw e;
        }
    }
}

