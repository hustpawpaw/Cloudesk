package com.cgcl.cloudesk.manage.com;

/**
 * Queue Entry
 */
class QueueEntry {
	private Object				data = null;
	public QueueEntry			prev = null;
	public QueueEntry			next = null;

	public QueueEntry(Object data)
	{
		this.data = data;
	}
	
	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}
}

/**
 * Queue implemented with the style of FIFO
 * Thread-safe
 */
public class Queue {
	QueueEntry					first = null;
	QueueEntry					last = null;
	
	/**
	 * Is the queue empty
	 * @return true if empty, otherwise not
	 */
	public synchronized boolean isEmpty()
	{
		if(null == first)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	/**
	 * Returns the size of the queue
	 * @returns size of the queue
	 */
	public synchronized int size()
	{
		if(null == first)
		{
			return 0;
		}
		
		int size = 1;
		QueueEntry qe = first;
		while(qe != last)
		{
			qe = qe.next;
			++size;
		}
		return size;
	}
	
	/**
	 * Pushes an item to the queue
	 * @params data Object to push into the queue
	 */
	public synchronized void push(Object data)
	{
		QueueEntry qe = new QueueEntry(data);
		if(null == first)
		{
			first = last = qe;
		}
		else
		{
			qe.next = first;
			first.prev = qe;
			first = qe;
		}
	}
	
	/**
	 * Pops an item from the queue
	 * @returns Object to data referenced by the last item
	 */
	public synchronized Object pop()
	{
		if(null == first)
		{
			return null;
		}
		
		QueueEntry qe = last;
		if(first == last)
		{
			first = last = null;
			return qe.getData();
		}
		
		last = last.prev;
		last.next = null;
		qe.prev = null;
		return qe.getData();
	}
	
	/**
	 * Clears the queue
	 */
	public synchronized void clear()
	{
		QueueEntry pre = first;
		QueueEntry post = null;
		while(null != pre)
		{
			post = pre.next;
			pre.setData(null);
			pre.prev = pre.next = null;
			pre = post;
		}
		first = last = null;
	}
}
