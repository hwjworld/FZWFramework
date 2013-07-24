package com.fzw.aop;

import java.lang.reflect.Method;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.aop.MethodBeforeAdvice;

import com.legaldaily.estension.ecard.model.condition.Cond;

public class LogAdvisior implements MethodBeforeAdvice {

	@Override
	public void before(Method m, Object[] args, Object target) throws Throwable {
		StringBuilder logStr = new StringBuilder();

//		logStr.append(DateFormatUtils.format(System.currentTimeMillis(),
//				"yyyy-MM-dd HH:mm:ss"));
		logStr.append("["+m.getDeclaringClass().getName()+":").append(m.getName()).append("]");
		if(args.length>0 && args[0] instanceof Cond){
			logStr.append("  message string: ").append(((Cond)args[0]).getMessage().getMessageStr());
		}
		Log log = LogFactory.getLog(m.getClass());
		log.info(logStr.toString());
	}
}
