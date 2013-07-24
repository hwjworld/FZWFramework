package com.fzw.utils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class LogUtils {
	
	private static final Log log = LogFactory.getLog(LogUtils.class);

	
	public static void debug(Object arg0) {
		log.debug(arg0);
	}
	
	public static void error(Object arg0) {
		log.error(arg0);
	}

	
	public static void fatal(Object arg0) {
		log.fatal(arg0);
	}

	public static void info(Object arg0) {
		log.info(arg0);
	}
	
	
	public static void trace(Object arg0) {
		log.trace(arg0);
	}
	
	public static void warn(Object arg0) {
		log.warn(arg0);
	}

}
