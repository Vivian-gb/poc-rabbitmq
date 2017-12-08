package com.teste.rabbitmqpoc.aspect;

import java.util.Arrays;
import java.util.Collection;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Aspect
public class LoggingAspect {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoggingAspect.class);

    /**
     * Metodo de execução de aspecto antes de executar os serviços
     *
     * @param joinPoint
     */
    @Before("execution(* com.teste.rabbitmqpoc.controller..*(..)) || execution(* com.teste.rabbitmqpoc.dao..*(..))")
    public void logBefore(JoinPoint joinPoint) {
        Object[] argumentos = joinPoint.getArgs();
        for (int i = 0; i < argumentos.length; i++) {
            if (argumentos[i] instanceof Collection) {
                String s = "Lista com tamanho: " + ((Collection<?>) argumentos[i]).size();
                argumentos[i] = s;
            }
            if (argumentos[i] instanceof Object[]) {
                String s = "Array com tamanho: " + ((Object[]) argumentos[i]).length;
                argumentos[i] = s;
            }
        }

        LOGGER.debug("Executando metodo {} parametros {}", getName(joinPoint), Arrays.toString(joinPoint.getArgs()));
    }

    /**
     * Metodo de execução de aspecto depois de executar os serviços
     *
     * @param joinPoint
     */
    @After("execution(* com.teste.rabbitmqpoc.controller..*(..)) || execution(* com.teste.rabbitmqpoc.dao..*(..))")
    public void logAfter(JoinPoint joinPoint) {
        LOGGER.debug("Saindo do metodo {}", getName(joinPoint));
    }

    /**
     * Metodo de execução de aspecto ao lançar uma exceção nos serviços
     *
     * @param joinPoint
     */
    @AfterThrowing(pointcut = "execution(* com.teste.rabbitmqpoc.controller..*(..)) || execution(* com.teste.rabbitmqpoc.dao..*(..))", throwing = "error")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable error) {
        LOGGER.error("Exceção no método: {}", getName(joinPoint));
        LOGGER.error("Exceção: {}", error.toString());
        if (error.getMessage() != null) {
            LOGGER.error("Detalhes: {}", error.getMessage(), error);
        }
    }

    private String getName(JoinPoint joinPoint) {
        String clazz = null;
        Object target = joinPoint.getTarget();
        if (target != null) {
            Class<?> targetClass = target.getClass();
            if (targetClass != null) {
                clazz = targetClass.getSimpleName();
            }
        }
        Signature signature = joinPoint.getSignature();
        if (clazz == null || clazz.isEmpty()) {
            Class declaringType = signature.getDeclaringType();
            if (declaringType != null) {
                clazz = declaringType.getSimpleName();
            }
            if (clazz == null || clazz.isEmpty()) {
                clazz = signature.getDeclaringTypeName();
            }
        }
        return new StringBuilder().append(clazz).append('#').append(signature.getName()).toString();
    }

}
