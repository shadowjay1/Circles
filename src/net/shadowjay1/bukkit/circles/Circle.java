package net.shadowjay1.bukkit.circles;

import java.util.ArrayList;
import java.util.HashMap;

public class Circle
{
	public static ArrayList<Circle> circles = new ArrayList<Circle>();
	
	String name;
	String tag;
	int radius;
	int ox;
	int oz;
	HashMap<String,Member> members;
	HashMap<String, Status> relations;
	ArrayList<String> invites;
	int bank;
	
	public Circle(String name)
	{
		tag = "";
		radius = 10;
		members = new HashMap<String,Member>();
		relations = new HashMap<String, Status>();
		this.name = name;
		bank = 0;
		
		circles.add(this);
	}
	
	public int getProtectionRadius()
	{
		return radius;
	}
	
	public void setProtectionRadius(int radius)
	{
		this.radius = radius;
	}
	
	public int getOriginX()
	{
		return ox;
	}
	
	public void setOriginX(int x)
	{
		ox = x;
	}
	
	public int getOriginZ()
	{
		return oz;
	}
	
	public void setOriginZ(int z)
	{
		oz = z;
	}
	
	public void setBalance(int bal)
	{
		bank = bal;
	}
	
	public void addBalance(int bal)
	{
		bank+=bal;
	}
	
	public int getBalance()
	{
		return bank;
	}
	
	public void setStatus(String circle, Status s)
	{
		relations.put(circle, s);
	}
	
	public Status getStatus(String circle)
	{
		return relations.containsKey(circle)?relations.get(circle):Status.Neutral;
	}
	
	public void addInvited(String invited)
	{
		if(!invites.contains(invited)) invites.remove(invited);
	}
	
	public void removeInvited(String invited)
	{
		invites.remove(invited);
	}
	
	public boolean isInvited(String invited)
	{
		return invites.contains(invited);
	}
	
	public void remove()
	{
		circles.remove(this);
	}
	
	public void setName(String name)
	{
		this.name = name;
	}
	
	public String getName()
	{
		return name;
	}
	
	public void setTag(String tag)
	{
		this.tag = tag;
	}
	
	public String getTag()
	{
		return tag;
	}
	
	public String[] getMembers()
	{
		return members.keySet().toArray(new String[members.keySet().size()]);
	}
	
	public void addMember(String p)
	{
		members.put(p, new Member(p));
	}
	
	public void removeMember(String p)
	{
		members.remove(p);
	}
	
	public boolean containsMember(String p)
	{
		return members.containsKey(p);
	}
	
	public Member getMember(String p)
	{
		return members.get(p);
	}
	
	public void setRank(String p, int rank)
	{
		if(members.containsKey(p))
		{
			members.get(p).setRank(rank);
		}
	}
	
	public int getRank(String p)
	{
		if(members.containsKey(p))
		{
			return members.get(p).getRank();
		}
		else
		{
			return -1;
		}
	}
	
	public static Circle byName(String name)
	{
		for(Circle circle : circles)
		{
			if(circle.getName().equals(name)) return circle;
		}
		
		return null;
	}
	
	public static Circle byTag(String tag)
	{
		for(Circle circle : circles)
		{
			if(circle.getTag().equals(tag)) return circle;
		}
		
		return null;
	}
	
	public static Circle byPlayer(String p)
	{
		for(Circle circle : circles)
		{
			if(circle.containsMember(p)) return circle;
		}
		
		return null;
	}
	
	public enum Status
	{
		Ally,Enemy,Neutral
	}
}
