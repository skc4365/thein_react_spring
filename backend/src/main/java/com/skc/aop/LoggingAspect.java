package com.skc.aop;

import java.util.Arrays;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Aspect
@Component
public class LoggingAspect {
	 // com.example.demo.controller 패키지 하위 모든 메서드를 대상으로 지정
    @Pointcut("execution(* com.skc.controller..*(..))")
    public void controllerLayer() {}

    // @LogExecutionTime 어노테이션이 붙은 메서드를 대상으로 지정
    @Pointcut("@annotation(com.skc.aop.LogExecutionTime)")
    public void annotatedMethods() {}

    /**
     * 메서드 실행 전 로그 (파라미터 확인용)
     */
    @Before("controllerLayer()")
    public void logBefore(JoinPoint joinPoint) {
        log.info(">>> [{}] 호출, 파라미터: {}",
                joinPoint.getSignature().toShortString(),
                Arrays.toString(joinPoint.getArgs()));
    }

    /**
     * 메서드 정상 실행 후 반환값 로그
     */
    @AfterReturning(pointcut = "controllerLayer()", returning = "result")
    public void logAfterReturning(JoinPoint joinPoint, Object result) {
        log.info("<<< [{}] 반환, 결과: {}",
                joinPoint.getSignature().toShortString(), result);
    }

    /**
     * 메서드 실행 중 예외 발생 시 로그
     */
    @AfterThrowing(pointcut = "controllerLayer()", throwing = "ex")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable ex) {
        log.error("!!! [{}] 예외 발생: {}",
                joinPoint.getSignature().toShortString(), ex.getMessage());
    }

    /**
     * 실행 시간까지 재보고 싶을 때는 @Around 사용
     * (@LogExecutionTime 어노테이션 붙은 메서드에만 적용)
     */
    @Around("annotatedMethods()")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();

        Object result = joinPoint.proceed(); // 실제 메서드 실행

        long executionTime = System.currentTimeMillis() - start;
        log.info(" [{}] 실행 시간: {}ms",
                joinPoint.getSignature().toShortString(), executionTime);

        return result;
    }
}
