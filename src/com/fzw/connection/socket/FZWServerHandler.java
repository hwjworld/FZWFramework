package com.fzw.connection.socket;

import java.util.Calendar;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.transport.socket.SocketSessionConfig;

import com.fzw.model.ConnectionMessage;
import com.fzw.service.ServiceInvokerProxy;

public class FZWServerHandler extends IoHandlerAdapter {
	private static final Log log = LogFactory
			.getLog(FZWServerHandler.class);

	private static long count = 0;
	// private static ThreadPoolExecutor tp = new ThreadPoolExecutor(2, 50,
	// 1000 * 60, TimeUnit.SECONDS,
	// new ArrayBlockingQueue<Runnable>(1000),
	// new ThreadPoolExecutor.CallerRunsPolicy());
	/**
	 * Trap exceptions.
	 */
    @Override
    public void exceptionCaught(IoSession session, Throwable cause) {
        log.warn("Unexpected exception.", cause);
        // Close connection when unexpected exception is caught.
        session.close(true);
    }


	@Override
	public void messageReceived(IoSession session, Object message)
			throws Exception {
//		log.info(session.getRemoteAddress() + " received");
		if(StringUtils.isBlank(message.toString())){
			return ;
		}
		session.setAttribute("time",Calendar.getInstance());
		String str = message.toString().trim();
		if (str.equalsIgnoreCase("shutdown?auth=fzw")) {
			log.info("received message to shutdown ECDaemon");
			session.setAttribute("shutdown");
			return;
		}
		if (str.startsWith("quit")) {
			session.close(false);
			return;
		}
//		log.info("Message: " + str);
		ConnectionMessage connectionMessage = ConnectionMessage
				.buildMessage(str);
//		log.info("[Received Message " + count++ + "]: "+connectionMessage.getMessageName());
		log.info("[Received Message " + count++ + "]: "+connectionMessage.getMessageStr());
		session.setAttribute("messagename", connectionMessage.getMessageStr());
		if (connectionMessage != null) {
			ServiceInvokerProxy.request(connectionMessage);
			session.write(connectionMessage.getResultValue());
		}
//		session.close(false);
	}

	@Override
	public void sessionCreated(IoSession session) throws Exception {
		SocketSessionConfig cfg = (SocketSessionConfig) session.getConfig();
		cfg.setReceiveBufferSize(2 * 1024 * 1024);
		cfg.setReadBufferSize(2 * 1024 * 1024);
		cfg.setKeepAlive(true);
		cfg.setSoLinger(0); // 这个是根本解决问题的设置

		cfg.setReuseAddress(true);
		super.sessionCreated(session);
	}

	/**
	 * On idle, we just write a message on the console
	 */
	@Override
	public void sessionIdle(IoSession session, IdleStatus status)
			throws Exception {
		System.out.println("IDLE " + session.getIdleCount(status));
		session.close(false);
	}

	@Override
	public void sessionClosed(IoSession session) throws Exception {
		super.sessionClosed(session);
		if (session.getAttribute("shutdown") != null) {
			FZWDaemon.shutdown();
		}
	}

	@Override
	public void sessionOpened(IoSession session) throws Exception {
		super.sessionOpened(session);
	}

	@Override
	public void messageSent(IoSession session, Object message) throws Exception {
		Calendar calendar = (Calendar) session.getAttribute("time");
		Calendar now = Calendar.getInstance();
		long cost = now.getTimeInMillis()-calendar.getTimeInMillis();
		
		log.info("message sent, cost: ["+cost+"] "+((cost>300)?" [Warnning: Spent more than 300ms]:"+session.getAttribute("messagename"):""));
		// if(session.i)
		// WriteFuture writeFuture = session.write(message);
		// writeFuture.await();
		// session.close(false);
		// session.read();
		// super.messageSent(session, message);
	}

}
