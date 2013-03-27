package com.cgcl.cloudesk.manage.net;

import com.cgcl.cloudesk.manage.packet.PacketBase;

public interface Transmittable {
	/**
	 * Sends packet to IO interface
	 * @param packet
	 */
	void						sendPacket(PacketBase packet);
	
	/**
	 * Starts
	 */
	void						start();
	
	/**
	 * Stops
	 */
	void						terminate();
}
