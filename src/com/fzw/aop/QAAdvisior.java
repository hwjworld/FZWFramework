package com.fzw.aop;

import java.lang.reflect.Method;

import org.springframework.aop.AfterReturningAdvice;

import com.legaldaily.estension.ecard.view.adapt.php.PHPAdapter;

public class QAAdvisior implements AfterReturningAdvice {

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

//	@Override
//	public Object invoke(MethodInvocation invocation) throws Throwable {
//		Object ret = invocation.proceed();
//		if (ret == null)
//			return null;
//		System.out.println("[end]"+invocation.getMethod().getName());
////		Object result = null;
////		if(invocation.getMethod().getName().eq)
////		if(ret instanceof List<?>){
////			List<?> list = (List<?>)ret; 
////			for (Object i : list) {
////				//listQARecommend
////				if(i instanceof Recommendation){
////					
////				}
////				//listWaitQARecommend
////				else if(i instanceof Question) {
////					
////				}
////			}
////		}
////		//return boolean
////		else{
////			
////		}
////		String password = (String) ret;
////		StringBuffer encrypt = new StringBuffer(password.length());
////		for (int i = 0; i < password.length(); i++)
////			encrypt.append('*');
//		return "abc";
//	}
}
