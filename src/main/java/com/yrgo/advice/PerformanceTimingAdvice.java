package com.yrgo.advice;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class PerformanceTimingAdvice {

    @Around("execution(* com.yrgo.services..*.*(..)) || execution(* com.yrgo.dataaccess..*.*(..))")
    public Object measureExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.nanoTime();
        Object result = joinPoint.proceed();
        long end = System.nanoTime();

        String className = joinPoint.getTarget().getClass().getName();
        String methodName = joinPoint.getSignature().getName();
        double timeTaken = (end - start) / 1_000_000.0; // Convert nanoseconds to milliseconds

        System.out.printf("Time taken for the method %s from the class %s took %.4fms%n", methodName, className, timeTaken);

        return result;
    }
}
