package com.acme.carddeckservice.aop;

import com.acme.carddeckservice.model.Card;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Aspect
@Component
public class LoggerAOP {
    private static final Logger LOGGER = LogManager.getLogger();
    public static final String REQUEST_ID = "requestId";
    private final ObjectMapper objectMapper;

    public LoggerAOP(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Before("execution(* com.acme.carddeckservice.controller.*.*(..))")
    public void logMethodsEntry(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        Map<String, Object> argumentMap = getAllArguments(joinPoint);

        String requestBodyJson = "{}";
        Object[] args = joinPoint.getArgs();
        for (Object arg : args) {
            if (arg instanceof Card) {
                try {
                    requestBodyJson = objectMapper.writeValueAsString(arg);
                } catch (JsonProcessingException e) {
                    LOGGER.error("Error while logging request body", e);
                }
                break;
            }
        }

        LOGGER.info("Received request to {} with, request Id: {} and arguments: {} and Request Body: {}",
                methodName, argumentMap.get(REQUEST_ID),
                argumentMap.entrySet()
                        .stream()
                        .filter(e -> !e.getKey().equals(REQUEST_ID))
                        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)),
                requestBodyJson);


    }

    @AfterReturning(pointcut = "execution(* com.acme.carddeckservice.controller.*.*(..))", returning = "returnValue")
    public void logMethodsExit(JoinPoint joinPoint, Object returnValue) throws JsonProcessingException {
        String methodName = joinPoint.getSignature().getName();
        Map<String, Object> argumentMap = getAllArguments(joinPoint);

        String responseBodyJson = "{}";
        HttpHeaders headers = null;
        if (returnValue instanceof ResponseEntity<?> responseEntity) {

            // Log headers
            headers = responseEntity.getHeaders();

            // Log response body
            Object body = responseEntity.getBody();
            if (body != null) {
                responseBodyJson = objectMapper.writeValueAsString(body);
            }
        }

        LOGGER.info("Sending response from {} with, request Id: {}, Response Body: {} and Headers: {}",
                methodName, argumentMap.get(REQUEST_ID), responseBodyJson, headers);

    }


    @AfterThrowing(pointcut = "execution(* com.acme.carddeckservice.*.*(..))", throwing = "ex")
    public void logException(JoinPoint joinPoint, Exception ex) {
        String methodName = joinPoint.getSignature().getName();
        String className = joinPoint.getTarget().getClass().getSimpleName();
        LOGGER.error("Exception in method {} of class {}: {}", methodName, className, ex.getMessage(), ex);
    }

    private Map<String, Object> getAllArguments(JoinPoint joinPoint) {
        Object[] arguments = joinPoint.getArgs();
        Map<String, Object> argumentMap = new HashMap<>();
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        String[] parameterNames = methodSignature.getParameterNames();
        for (int i = 0; i < parameterNames.length; i++) {
            argumentMap.put(parameterNames[i], arguments[i]);
        }
        return argumentMap;
    }
}
