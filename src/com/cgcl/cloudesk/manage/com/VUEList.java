package com.cgcl.cloudesk.manage.com;

class  ListNode{
	private String value = null;
	public ListNode next = null;
	
	public ListNode(String value)
	{
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
public class VUEList {
	
	private ListNode head = null;
	private ListNode tail = null;
	
	public void insert(String value)
	{
		ListNode listNode = new ListNode(value);
		if( head == null )
		{
			head = listNode;
			tail = listNode;
			tail.next = null;
		}
		else
		{
			tail.next = listNode;
			tail = listNode;
			tail.next = null;
		}
	}
	
	public boolean delete(String value)
	{
		if( head == null)
			return false;
		
		if( head.getValue().equals(value))
		{
			ListNode tmp;
			tmp = head;
			tmp.setValue(null);
			head = tmp.next;
			tmp.next = null;
			
			if(tmp == tail)
			{
				tail = null;
			}
			return true;
		}
		
		ListNode tmpPre = head;
		ListNode tmp = head.next;
		for( ; tmp != null ; tmpPre = tmp, tmp = tmp.next)
		{
			if(tmp.getValue().equals(value))
			{
				tmp.setValue(null);
				tmpPre.next = tmp.next;
				tmp.next = null;
				return true;
			}
		}
		return false;
	}
	
	public int size()
	{
		if( head == null )
			return 0;
		else
		{
			int num = 0;
			for(ListNode tmp = head ; tmp != null ; tmp = tmp.next)
				num++;
			return num;
		}
	}
	
	public void clear()
	{
		if(head == null)
			return ;
		
		ListNode current = head;
		ListNode next = head.next;
		while( current.next != null )
		{
			current.setValue(null);
			current.next = null;
			current = next;
			next = next.next;
		}
		
		current.setValue(null);
		current.next = null;
		head = tail = null;
	}
	
	public String get(int i)
	{
		if( head == null )
			return null;
		int num = 0;
		for(ListNode tmp = head ; tmp != null ; tmp = tmp.next)
		{
			if( num == i )
				return tmp.getValue();
			num++;
		}
		return null;
	}
	
	public String toString()
	{
		String result = null;
		for(ListNode tmp = head ; tmp != null ; tmp = tmp.next)
		{
			result += tmp.getValue();
			result += "\n";
		}
		return result;
	}
	
}
