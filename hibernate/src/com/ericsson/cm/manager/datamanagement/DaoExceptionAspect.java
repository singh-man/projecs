package com.ericsson.cm.manager.datamanagement;

import com.ericsson.cm.common.exception.MMException;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DataAccessException;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class shall intercept the methods in DAO classes and converts the
 * {@link DataAccessException} thrown by Spring to {@link MMException} before
 * throwing to the caller.
 * 
 * @author emmhssh
 * 
 */
@Aspect
@Order(1)
public class DaoExceptionAspect {

	final Logger log = Logger.getLogger(DaoExceptionAspect.class.getName());

	/**
	 * Point cut definition
	 */
	@Pointcut("@within(org.springframework.transaction.annotation.Transactional)")
	//@Pointcut("execution(* com.ericsson.cm.manager.datamanagement.dao.*DAO.*(..))")
	public void dbException() {
	}

	/**
	 * Point cut interceptor to convert {@link DataAccessException} to
	 * {@link MMException}
	 * 
	 * @param e
	 * @throws MMException
	 */
	@AfterThrowing(pointcut = "dbException()", throwing = "e")
	public void logDbException(DataAccessException e) throws Exception {
		log.severe(e.getMessage());
		if (log.isLoggable(Level.FINE))
			e.printStackTrace();
		throw new Exception();
	}
}
