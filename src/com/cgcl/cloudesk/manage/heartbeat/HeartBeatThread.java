package com.cgcl.cloudesk.manage.heartbeat;

import com.cgcl.cloudesk.manage.net.Transmittable;
import com.cgcl.cloudesk.manage.packet.PingPacket;

public class HeartBeatThread extends Thread{
	private Transmittable		transmitter = null;
	private PingPacket pingPacket = new PingPacket();
	
	public void run()
	{
		while(true)
		{
			transmitter.sendPacket(pingPacket);
			try {
				sleep(1000*30);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void setPingPacket(String userName)
	{
		this.pingPacket.setUserName(userName);
	}
	
	public void setTransmitter(Transmittable transmitter)
	{
		this.transmitter = transmitter;
	}
}
