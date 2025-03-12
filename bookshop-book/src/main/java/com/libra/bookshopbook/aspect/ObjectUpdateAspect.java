package com.libra.bookshopbook.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;


@Aspect
@Component
@Slf4j
public class ObjectUpdateAspect {

    @AfterReturning(value = "@annotation(com.libra.bookshopbook.aspect.annotations.LogUpdateObject)", returning = "result")
    public void logAfterMethodExecution(Object result) {
        String entityName = result.getClass().getName();
        String logMessage = String.format("Entity of %s has been updated: %s", entityName, result);
        log.info(logMessage);
    }

}
