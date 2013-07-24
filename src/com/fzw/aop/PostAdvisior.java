package com.fzw.aop;

import java.lang.reflect.Method;

import org.springframework.aop.AfterReturningAdvice;

import com.legaldaily.estension.ecard.view.adapt.php.PHPAdapter;

public class PostAdvisior implements AfterReturningAdvice {

	private PHPAdapter adapter;
	
	@Override
	public void afterReturning(Object returnValue, Method method,
			Object[] args, Object target) throws Throwable {

//		returnValue = adapter.adapt(method.getName(), returnValue);
	}
	
	public PHPAdapter getAdapter() {
		return adapter;
	}
	public void setAdapter(PHPAdapter adapter) {
		this.adapter = adapter;
	}

}
