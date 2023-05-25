package com.banque.misr.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestClientResponseException;


@Aspect
@Component
public class LoggingAspect {
    private Logger log = LogManager.getLogger(LoggingAspect.class);

    /**
     * Pointcut that matches all repositories, services and Web REST endpoints.
     */
    @Pointcut("within(@org.springframework.stereotype.Repository *)"
            + " || within(@org.springframework.stereotype.Service *)"
            + " || within(@org.springframework.stereotype.Controller *)"
            + " || within(@org.springframework.stereotype.Component *)")
    public void springBeanPointcut() {
// Method is empty as this is just a Pointcut, the implementations are in the
// advices.
    }

    /**
     * Pointcut that matches all Spring beans in the application's main packages.
     */
    @Pointcut("within(com.banque.misr..*)")
    public void applicationPackagePointcut() {
// Method is empty as this is just a Pointcut, the implementations are in the
// advices.
    }

    @Around("applicationPackagePointcut() && springBeanPointcut()")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        Object[] arguments = joinPoint.getArgs();
        StringBuilder logString = new StringBuilder();
        logString.append(joinPoint.getSignature()).append(" started ");
        if (arguments.length != 0) {
            logString.append("with parameters: ");
            for (int i = 0; i < arguments.length; i++) {
                logString.append(arguments[i]);
            }
        }
        log.info(logString.toString());
        long start = System.currentTimeMillis();

        Object proceed = joinPoint.proceed();

        long executionTime = System.currentTimeMillis() - start;

        log.info(joinPoint.getSignature() + " finished execution in [" + executionTime + "]ms");
        return proceed;
    }

    @AfterThrowing(pointcut = "applicationPackagePointcut() && springBeanPointcut()", throwing = "ex")
    public void logException(JoinPoint joinPoint, Exception ex) {
        if (ex instanceof HttpStatusCodeException) {
            log.error("Error occured in " + joinPoint.getSignature() + ", Status code: "
                    + ((HttpStatusCodeException) ex).getStatusCode() + " Error Body: "
                    + ((RestClientResponseException) ex).getResponseBodyAsString());
        }
        log.error("error: " + ex.getMessage());
    }

}