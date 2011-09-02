package net.shadowjay1.bukkit.circles;

import java.util.ArrayList;

public class CircleRank
{
	ArrayList<CirclePermission> permissions = new ArrayList<CirclePermission>();
	String name;
	
	public CircleRank(String name)
	{
		this.name = name;
	}
	
	public void addPermission(CirclePermission p)
	{
		if(!permissions.contains(p)) permissions.add(p);
	}
	
	public void removePermission(CirclePermission p)
	{
		if(permissions.contains(p)) permissions.remove(p);
	}
	
	public boolean hasPermission(CirclePermission p)
	{
		return permissions.contains(p);
	}
	
	public void setName(String name)
	{
		this.name = name;
	}
	
	public String getName()
	{
		return name;
	}
}
