package com.fzw.connection.socket;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;

public class ShutdownFZWDaemonHandler extends IoHandlerAdapter {
	@Override
	public void messageReceived(IoSession session, Object message)
			throws Exception {
		if(message.equals("successfully")){
			System.err.println("Shutdown successfully");
			session.close(false);
		}
	}
	
	@Override
	public void exceptionCaught(IoSession session, Throwable cause)
			throws Exception {
	}
}
