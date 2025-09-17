package com.academico.interceptor;

import jakarta.interceptor.AroundInvoke;
import jakarta.interceptor.Interceptor;
import jakarta.interceptor.InvocationContext;
import java.util.logging.Logger;

/**
 * Interceptor para logging de métodos
 */
@Interceptor
public class LoggingInterceptor {

    private static final Logger logger = Logger.getLogger(LoggingInterceptor.class.getName());

    @AroundInvoke
    public Object logMethod(InvocationContext context) throws Exception {
        String className = context.getTarget().getClass().getSimpleName();
        String methodName = context.getMethod().getName();
        
        logger.info("Ejecutando método: " + className + "." + methodName);
        
        long startTime = System.currentTimeMillis();
        
        try {
            Object result = context.proceed();
            long endTime = System.currentTimeMillis();
            logger.info("Método " + className + "." + methodName + " ejecutado en " + (endTime - startTime) + "ms");
            return result;
        } catch (Exception e) {
            logger.severe("Error en método " + className + "." + methodName + ": " + e.getMessage());
            throw e;
        }
    }
}

