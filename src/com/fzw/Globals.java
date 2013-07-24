package com.fzw;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.ClassPathResource;

import com.fzw.config.FZWFrameworkConfig;

public class Globals {

	private static final Log log = LogFactory.getLog(Globals.class);
	
	private static XmlBeanFactory factory = new XmlBeanFactory(
			new ClassPathResource("beans.xml"));
	
	/**
	 * 0.0.1 framework
	 * 0.0.2 support extension: includes seperate {commands, excutors, db connection, cache, extension init} 
	 */
	public static void printVersion() {
		log.info("***************************");
		log.info("FZW Framework");
		log.info("version "+FZWFrameworkConfig.getVersion());
		log.info("author: fzw ");
		log.info("***************************");
	}

	public static Object getBean(String name) {
		if (factory.containsBean(name)) {
			return factory.getBean(name);
		}
		return null;
	}
}