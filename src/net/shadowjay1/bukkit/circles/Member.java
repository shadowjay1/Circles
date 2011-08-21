package net.shadowjay1.bukkit.circles;

import java.io.Serializable;

public class Member implements Serializable
{
	private static final long serialVersionUID = 1269496015858064563L;
	String title = "";
	int rank = 0;
	String name;
	
	public Member(String name)
	{
		this.name = name;
	}
	
	public String getTitle()
	{
		return title;
	}
	
	public void setTitle(String t)
	{
		title = t;
	}
	
	public int getRank()
	{
		return rank;
	}
	
	public void setRank(int r)
	{
		rank = r;
	}
	
	public String getName()
	{
		return name;
	}
}
