package com.cgcl.cloudesk.manage.net;

import java.io.IOException;
import java.io.InputStream;

import com.cgcl.cloudesk.manage.app.AppData;
import com.cgcl.cloudesk.manage.com.Handler;
import com.cgcl.cloudesk.manage.config.NetConfig;
import com.cgcl.cloudesk.manage.config.PacketConfig;
import com.cgcl.cloudesk.manage.log.LogService;
import com.cgcl.cloudesk.manage.packet.PacketBase;
import com.cgcl.cloudesk.manage.util.NetUtil;
import com.cgcl.cloudesk.manage.util.Serializer;
import com.cgcl.cloudesk.screens.act.BaseActivity;
import com.cgcl.cloudesk.screens.act.MainActivity;
import com.cgcl.cloudesk.screens.act.UI;

public class InputThread extends Thread {
	private boolean				bTerminate = false;
	private Handler				handler = null;
	private InputStream			is = null;
	private byte[]				inputBuf = new byte[NetConfig.inputBufLen];
	private int					inputBufPos = 0;
	private UI					ui = null;
	public InputThread(Handler handler, InputStream is)
	{
		this.handler = handler;
		this.is = is;
	}
	
	public void run()
	{
		// InputThread can only run once
		if(bTerminate)
		{
			return;
		}
		
		// main loop
		while(!bTerminate)
		{
			// read inputstream
			int inputLen = -1;
			try {
				inputLen = is.read(inputBuf, inputBufPos, inputBuf.length - inputBufPos);
			} catch (IOException e) {					
				LogService.getInstance().WriteLog("ReadError : " + e.getMessage()+ " inputBufPos = " + inputBufPos);
			//	if (MainActivity.isInitSuceessed == false)
				((BaseActivity) BaseActivity.CurrentActivity).showError();
			}
		
			System.out.println("input Len: " + inputLen);
			// meet the end of the inputstream
			if(-1 == inputLen)
			{
				bTerminate = true;
				continue;
			}
			
			// change the current pos
			inputBufPos += inputLen;
			
			// analyze the byte array
			int tempInputBufPos = 0;
			PacketBase packet = null;
			do
			{
				System.out.println("tempInputBufPos = " + tempInputBufPos +"   inputBufPos - tempInputBufPos = " + (inputBufPos - tempInputBufPos));
				packet = NetUtil.produce(inputBuf, tempInputBufPos, inputBufPos - tempInputBufPos);
				System.out.println("produce");
				// move the left data to front
				if(null == packet)
				{
					System.out.println("packet == null ; break");
					System.arraycopy(inputBuf, tempInputBufPos, inputBuf, 0, inputBufPos - tempInputBufPos);
					inputBufPos -= tempInputBufPos;
					break;
				}
				
				//if the packet is too long
				if( packet.getType() == PacketConfig.kWarningPacketType)
				{
					int packetLen = Serializer.deserializeInt(inputBuf, tempInputBufPos + PacketConfig.kLenPos);
					byte[] tmpInputBuf = new byte[packetLen];
					System.arraycopy(inputBuf, tempInputBufPos, tmpInputBuf, 0, inputBufPos - tempInputBufPos);
					
					try {
						while( 0 != (tmpInputBuf.length - (inputBufPos - tempInputBufPos)) )
						{
							inputLen = is.read(tmpInputBuf, (inputBufPos - tempInputBufPos), tmpInputBuf.length - (inputBufPos - tempInputBufPos));
							inputBufPos += inputLen;
						}
					} catch (IOException e) {
						LogService.getInstance().WriteLog("WarningPacketError : " + " " + e.getMessage());
						e.printStackTrace();
					}
					packet = NetUtil.produce(tmpInputBuf, 0, tmpInputBuf.length);
					handler.handle(packet);
					inputBufPos = 0;
					break;
				}
				
				// move tempInputBufPos forward
				System.out.println("packet type = " + (int)packet.getType());
				System.out.println("packet length = " + packet.length());
				tempInputBufPos += packet.length();
				// handle the entire packet
				System.out.println("handler.handle(packet);");
				handler.handle(packet);
			}
			while(true);
		}
		
		inputBufPos = 0;
		bTerminate = true;
	}
	
	public void terminate()
	{
		try {
			is.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			LogService.getInstance().WriteLog("InputTerminateError = " +" " + e.getMessage());
			e.printStackTrace();
		}
	}
}
