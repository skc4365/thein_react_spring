# Spring Boot AOP 로깅 가이드

JDK 21 / Spring Boot 4.1.0 / Gradle / Lombok / JPA 환경에서
AOP(Aspect-Oriented Programming)를 이용한 로깅 적용 방법 정리.

---

## 1. 개요

컨트롤러 등 특정 계층의 메서드가 호출될 때마다 로그 코드를 반복 작성하지 않고,
AOP가 **호출 전 / 정상 반환 후 / 예외 발생 시 / 실행 시간 측정**을 자동으로 처리하도록 구성한다.

- 서버 콘솔(터미널, 로그 파일)에만 출력됨
- 브라우저 화면(React)에는 별도로 노출하지 않음 (필요 시 추후 확장 가능)

---

## 2. build.gradle 의존성

```gradle
dependencies {
    implementation 'org.springframework.boot:spring-boot-starter-web'
    implementation 'org.springframework.boot:spring-boot-starter-data-jpa'
	
    //implementation 'org.springframework.boot:spring-boot-starter-aop'   // AOP 3.x
	implementation 'org.springframework.boot:spring-boot-starter-aspectj' // AOP 4.x

    compileOnly 'org.projectlombok:lombok'
    annotationProcessor 'org.projectlombok:lombok'

}
```

`spring-boot-starter-aop`가 없으면 `@Aspect`가 동작하지 않음 (aspectjweaver 포함).

---

## 3. application.properties

```properties
server.port=8090

spring.application.name=demo-app

# JPA
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

# 로그 레벨 (패키지 단위로 세밀하게 조절 가능)
logging.level.com.example.demo.aop=DEBUG
logging.level.org.hibernate.SQL=DEBUG
```

---

## 4. 커스텀 어노테이션

특정 메서드에만 선택적으로 실행 시간 로깅을 붙이고 싶을 때 사용.

```java
package com.skc.aop;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface LogExecutionTime {
}
```

---

## 5. LoggingAspect (핵심 클래스)

```java
package com.skc.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Slf4j
@Aspect
@Component
public class LoggingAspect {

    // controller 패키지 하위 모든 메서드 대상
    @Pointcut("execution(* com.skc.controller..*(..))")
    public void controllerLayer() {}

    // @LogExecutionTime 붙은 메서드만 대상
    @Pointcut("@annotation(com.skc.aop.LogExecutionTime)")
    public void annotatedMethods() {}

    /** 메서드 실행 전: 파라미터 로깅 */
    @Before("controllerLayer()")
    public void logBefore(JoinPoint joinPoint) {
        log.info(">>> [{}] 호출, 파라미터: {}",
                joinPoint.getSignature().toShortString(),
                Arrays.toString(joinPoint.getArgs()));
    }

    /** 정상 반환 후: 결과값 로깅 */
    @AfterReturning(pointcut = "controllerLayer()", returning = "result")
    public void logAfterReturning(JoinPoint joinPoint, Object result) {
        log.info("<<< [{}] 반환, 결과: {}",
                joinPoint.getSignature().toShortString(), result);
    }

    /** 예외 발생 시: 에러 로깅 */
    @AfterThrowing(pointcut = "controllerLayer()", throwing = "ex")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable ex) {
        log.error("!!! [{}] 예외 발생: {}",
                joinPoint.getSignature().toShortString(), ex.getMessage());
    }

    /** 실행 시간 측정 (서버 콘솔 로그로만 출력) */
    @Around("annotatedMethods()")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();

        Object result = joinPoint.proceed(); // 실제 메서드 실행

        long executionTime = System.currentTimeMillis() - start;
        log.info("⏱ [{}] 실행 시간: {}ms",
                joinPoint.getSignature().toShortString(), executionTime);

        return result;
    }
}
```

---

## 6. 테스트용 Controller

```java
package com.skc.controller;

import com.example.demo.aop.LogExecutionTime;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/hello")
@RequiredArgsConstructor
public class HelloController {

    @GetMapping
    public String hello(@RequestParam(defaultValue = "world") String name) {
        return "Hello, " + name;
    }

    @LogExecutionTime
    @GetMapping("/slow")
    public String slowMethod() throws InterruptedException {
        Thread.sleep(500); // 느린 작업 시뮬레이션
        return "완료!";
    }

    @GetMapping("/error")
    public String errorTest() {
        throw new RuntimeException("일부러 발생시킨 예외");
    }
}
```

---

## 7. 실행 결과 예시 (서버 콘솔)

```
>>> [HelloController.hello(..)] 호출, 파라미터: [Claude]
<<< [HelloController.hello(..)] 반환, 결과: Hello, Claude

>>> [HelloController.slowMethod(..)] 호출, 파라미터: []
⏱ [HelloController.slowMethod(..)] 실행 시간: 503ms
<<< [HelloController.slowMethod(..)] 반환, 결과: 완료!

>>> [HelloController.errorTest(..)] 호출, 파라미터: []
!!! [HelloController.errorTest(..)] 예외 발생: 일부러 발생시킨 예외
```

> 실행 시간 로그는 서버 콘솔에만 출력되며, 프론트엔드(React) 화면에는 별도로 노출하지 않는다.

---

## 8. 프론트엔드 호출 예시 (React + axios)

```javascript
// src/api/axios.js
import axios from 'axios';

const api = axios.create({
  baseURL: 'http://localhost:8090/api',
});

export default api;
```

```jsx
// src/App.jsx
import { useState } from 'react';
import api from './api/axios';

function App() {
  const [result, setResult] = useState('');

  const callHello = async () => {
    const res = await api.get('/hello', { params: { name: 'React' } });
    setResult(res.data);
  };

  const callSlow = async () => {
    const res = await api.get('/hello/slow');
    setResult(res.data);
  };

  return (
    <div>
      <button onClick={callHello}>Hello 호출</button>
      <button onClick={callSlow}>느린 메서드 호출</button>
      <p>결과: {result}</p>
    </div>
  );
}

export default App;
```

---

## 9. 핵심 정리

| 어노테이션 | 시점 | 용도 |
|---|---|---|
| `@Before` | 메서드 실행 전 | 파라미터 로깅, 인증 체크 등 |
| `@AfterReturning` | 정상 반환 후 | 결과값 로깅 |
| `@AfterThrowing` | 예외 발생 시 | 에러 로깅 |
| `@Around` | 실행 전체를 감쌈 | 실행 시간 측정, 트랜잭션 처리 등 (가장 강력) |

- **패키지 기준 Pointcut**: `controller` 패키지 하위 전체에 자동 적용 (`@Before`, `@AfterReturning`, `@AfterThrowing`)
- **어노테이션 기준 Pointcut**: `@LogExecutionTime`이 붙은 메서드에만 선택적으로 적용 (`@Around`)

---

## 10. Logback 설정 (로그를 파일로 저장)

콘솔 출력은 그대로 두고, 파일에도 로그를 남기도록 구성한다.

### 10-1. logback-spring.xml

`src/main/resources/logback-spring.xml` 경로에 생성. (`application.properties`와 같은 위치)

```xml
<?xml version="1.0" encoding="UTF-8"?>
<configuration>

    <!-- 로그 저장 경로 -->
    <property name="LOG_PATH" value="${user.dir}/logs"/>
    <property name="LOG_FILE_NAME" value="app"/>

    <!-- 콘솔 출력 -->
    <appender name="CONSOLE" class="ch.qos.logback.core.ConsoleAppender">
        <encoder>
            <pattern>%d{HH:mm:ss.SSS} [%thread] %-5level %logger{36} - %msg%n</pattern>
        </encoder>
    </appender>

    <!-- 파일 출력 (일별로 파일 분리, 최대 30일 보관) -->
    <appender name="FILE" class="ch.qos.logback.core.rolling.RollingFileAppender">
        <file>${LOG_PATH}/${LOG_FILE_NAME}.log</file>
        <rollingPolicy class="ch.qos.logback.core.rolling.TimeBasedRollingPolicy">
            <fileNamePattern>${LOG_PATH}/${LOG_FILE_NAME}.%d{yyyy-MM-dd}.log</fileNamePattern>
            <maxHistory>30</maxHistory>
        </rollingPolicy>
        <encoder>
            <pattern>%d{yyyy-MM-dd HH:mm:ss.SSS} [%thread] %-5level %logger{36} - %msg%n</pattern>
        </encoder>
    </appender>

    <!-- AOP 로그는 콘솔 + 파일 둘 다 -->
    <logger name="com.skc.aop" level="DEBUG" additivity="false">
        <appender-ref ref="CONSOLE"/>
        <appender-ref ref="FILE"/>
    </logger>

    <!-- 전체 기본 로그 레벨 -->
    <root level="INFO">
        <appender-ref ref="CONSOLE"/>
        <appender-ref ref="FILE"/>
    </root>

</configuration>
```

### 10-2. application.properties 정리

Logback 설정으로 로그 레벨을 관리하므로, 기존 `logging.level.*` 라인은 제거한다.

```properties
server.port=8090
spring.application.name=demo-app

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

### 10-3. 동작 확인

애플리케이션 실행 후 프로젝트 루트에 `logs/app.log` 파일이 생성되고, AOP 로그가 콘솔과 파일 양쪽에 동시에 찍힌다.

```
logs/
 ├── app.log                  ← 오늘 로그
 ├── app.2026-07-16.log       ← 어제 로그 (자동 보관)
 └── ...
```

### 10-4. 참고

- `.gitignore`에 `logs/` 추가해서 로그 파일이 git에 커밋되지 않도록 하는 걸 권장한다.
- `maxHistory`는 보관할 일수. 필요에 따라 조절 (예: 운영 서버는 90일 등).
- 로그 파일 용량이 걱정되면 `<maxFileSize>10MB</maxFileSize>`와 `<totalSizeCap>1GB</totalSizeCap>` 옵션을 `rollingPolicy` 안에 추가.

---

## 11. 참고 - 추후 확장 가능한 항목

- MDC(Mapped Diagnostic Context)를 이용한 요청별 추적 ID(traceId) 부여
- 실행 시간을 응답 헤더나 바디로 프론트엔드에 노출 (필요 시 별도 적용)
