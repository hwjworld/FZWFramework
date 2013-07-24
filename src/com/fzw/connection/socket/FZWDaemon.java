/**
 * 
 */
package com.fzw.connection.socket;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.charset.Charset;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.mina.core.filterchain.DefaultIoFilterChainBuilder;
import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;

import com.fzw.SystemInit;
import com.fzw.config.FZWFrameworkConfig;
import com.fzw.utils.LogUtils;

/**
 * @author hwj
 * 
 */
public class FZWDaemon {

	private static IoAcceptor acceptor = null;

	/**
	 * @param args
	 * @throws IOException
	 * @throws ConfigurationException 
	 */
	public static void main(String[] args) throws IOException, ConfigurationException {
		SystemInit.init();
		// Create the acceptor
		acceptor = new NioSocketAcceptor();
		

		// Add two filters : a logger and a codec
//		 acceptor.getFilterChain().addLast( "logger", new LoggingFilter() );
		TextLineCodecFactory textLineCodecFactory = new TextLineCodecFactory(Charset
				.forName("UTF-8"));
		textLineCodecFactory.setDecoderMaxLineLength(Integer.MAX_VALUE);
		textLineCodecFactory.setEncoderMaxLineLength(Integer.MAX_VALUE);
		acceptor.getFilterChain().addLast(
				"codec",
				new ProtocolCodecFilter(textLineCodecFactory));

		acceptor.setHandler(new FZWServerHandler());

		acceptor.getSessionConfig().setReadBufferSize(2048);
		acceptor.getSessionConfig().setIdleTime(IdleStatus.BOTH_IDLE, 10);
		acceptor.getSessionConfig().setWriteTimeout(120);
		
		addLogger(acceptor.getFilterChain());
		
		acceptor.bind(new InetSocketAddress(FZWFrameworkConfig.getPort()));
		LogUtils.info("［FZWFramework系统启动完成］");
	}
	

	private static void addLogger(DefaultIoFilterChainBuilder chain) {
		chain.addLast("logger", new LoggingFilter());
		Log log = LogFactory.getLog(FZWDaemon.class);
		log.info("Logging ON");
	}

	public static void shutdown() {
		Log log = LogFactory.getLog(FZWDaemon.class);
		log.warn("Shutting down ESDaemon, wait...");
		acceptor.setCloseOnDeactivation(true);
		acceptor.unbind();
		acceptor.dispose();
		log.warn("Shutted down ESDaemon");
	}
}
