package com.yrgo.advice;

import org.aspectj.lang.ProceedingJoinPoint;

public class PerformanceTimingAdvice {

    public Object measureExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.nanoTime();
        Object result = joinPoint.proceed();
        long end = System.nanoTime();

        String className = joinPoint.getTarget().getClass().getName();
        String methodName = joinPoint.getSignature().getName();
        double timeTaken = (end - start) / 1_000_000.0;

        System.out.printf("Time taken for the method %s from the class %s took %.4fms%n", methodName, className, timeTaken);

        return result;
    }
}
