package com.fzw.utils;

import java.util.HashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * futuretask util implement.
 * @author hwj
 *
 */
public class FutureTaskUtil {

	private static final HashMap<Object, FutureTask<Object>> taskMap = new HashMap<Object, FutureTask<Object>>();

	private static final ExecutorService es = Executors.newCachedThreadPool();
	
	public static void addTask(Object id, FutureTask<Object> task) {
		taskMap.put(id, task);
		es.execute(task);
	}
	
	public static Object getTaskValue(Object id) throws InterruptedException, ExecutionException {
		return taskMap.get(id).get();
	}
	
	public static Object getTaskValue(Object id, long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
		return taskMap.get(id).get(timeout,unit);
	}
	
//	public static FutureTask<Object> getTask(Object id){
//		return taskMap.get(id);
//	}
	
	public static boolean isDone(Object id){
		return taskMap.get(id).isDone();
	}
	
	public static boolean isCancelled(Object id){
		return taskMap.get(id).isCancelled();
	}

	public static boolean cancel(Object id){
		return taskMap.get(id).cancel(true);
	}
}
