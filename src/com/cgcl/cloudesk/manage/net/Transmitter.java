/**
 * Transmitter that is supported by TCP protocol
 */

package com.cgcl.cloudesk.manage.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;

import com.cgcl.cloudesk.manage.com.Handler;
import com.cgcl.cloudesk.manage.log.LogService;
import com.cgcl.cloudesk.manage.packet.PacketBase;
import com.cgcl.cloudesk.screens.act.BaseActivity;

public class Transmitter implements Transmittable, Handler {
	private Handler				handler = null;
	private Socket 				sock = null;
	private InputThread			inputThread = null;
	private OutputThread		outputThread = null;
	/*static 	boolean				connectionOK = true;
	static  int					connectionResult = 0;
	
	*/
	public Transmitter(Handler handler, String remoteHost, String port) throws Exception
	{
		// config
		this.handler = handler;

		sock = new Socket();
		SocketAddress 	socketAddress = new InetSocketAddress(remoteHost, Integer.parseInt(port));
		sock.connect(socketAddress, 4000);
		//sock.setSoTimeout(5000);
		inputThread = new InputThread( this, sock.getInputStream() );
		outputThread = new OutputThread( sock.getOutputStream() );
		
		LogService.getInstance().WriteLog("sock = " + sock + " inputThread = " + inputThread + "  outputThread = " + outputThread);
	}
	
	public void start()
	{
		System.out.println("6");		
		inputThread.start();			
		System.out.println("7");
		outputThread.start();
		System.out.println("8");
	}
	
	public void terminate()
	{
		inputThread.terminate();
		outputThread.terminate();
		
		try {
			sock.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			inputThread.join();
			outputThread.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void sendPacket(PacketBase packet) {
		packet.setLen( packet.length() );
		outputThread.output(packet);
	}

	public void handle(PacketBase packet) {
		handler.handle(packet);
	}
}
