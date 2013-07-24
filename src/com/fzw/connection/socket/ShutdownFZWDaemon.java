package com.fzw.connection.socket;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;
import java.util.Scanner;

import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

public class ShutdownFZWDaemon {
	public static void main(String[] args) {

		System.out.println("Please input the password to shutdown ECDaemon:");

		String pwd = new Scanner(System.in).next();
		if(!"founder".equals(pwd)){
			System.out.println("ERROR PASSWORD");
			return;
			//TODO log the information of this behavior  
		}else{
			System.out.println("OK...");
			System.out.println("START TO SHUT DOWN. WAIT...");
		}

		NioSocketConnector connector = new NioSocketConnector();

		// Configure the service.
		connector.setConnectTimeoutMillis(2000);
		connector.getFilterChain().addLast(
				"codec",
				new ProtocolCodecFilter(new TextLineCodecFactory(Charset
						.forName("UTF-8"))));

//		connector.getFilterChain().addLast("logger", new LoggingFilter());

		connector.setHandler(new ShutdownFZWDaemonHandler());

		ConnectFuture future = connector.connect(new InetSocketAddress(
				"127.0.0.1", 8410));
		future.awaitUninterruptibly();
		IoSession session = future.getSession();
		session.write("shutdown?auth=fzw");

		// wait until the summation is done
		session.getCloseFuture().awaitUninterruptibly();

		connector.dispose();
		System.out.println("SHUTTED DOWN ECDaemon");
	}
}
