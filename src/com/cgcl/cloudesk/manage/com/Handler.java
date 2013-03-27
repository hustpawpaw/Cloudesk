/**
 * Packet Handler
 */

package com.cgcl.cloudesk.manage.com;

import com.cgcl.cloudesk.manage.packet.PacketBase;

public interface Handler {
	/**
	 * Handles the packet
	 * @param packet received packet from the IO interface
	 */
	void						handle(PacketBase packet);
}
