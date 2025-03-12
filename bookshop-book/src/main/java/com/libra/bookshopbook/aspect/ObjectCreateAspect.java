package com.libra.bookshopbook.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;


@Aspect
@Component
@Slf4j
public class ObjectCreateAspect {

    @AfterReturning(value = "@annotation(com.libra.bookshopbook.aspect.annotations.LogCreateObject)", returning = "result")
    public void logAfterMethodExecution(Object result) {
        String entityName = result.getClass().getName();
        String logMessage = String.format("Entity of %s has been created: %s", entityName, result);
        log.info(logMessage);
    }

}
