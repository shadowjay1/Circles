package net.shadowjay1.bukkit.utils;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.entity.Player;

public class PlayerPermissions
{
	protected static ArrayList<PlayerPermissions> permissions = new ArrayList<PlayerPermissions>();
	protected HashMap<String, Boolean> playerpermissions = new HashMap<String, Boolean>();
	private Player player;
	private PlayerPermissions(Player p)
	{
		player = p;
		permissions.add(this);
	}
	
	public Player getPlayer()
	{
		return player;
	}
	
	public boolean hasPermission(String name)
	{
		if(playerpermissions.containsKey(name)) return playerpermissions.get(name);
		else return false;
	}
	
	public void setPermission(String name, boolean value)
	{
		playerpermissions.put(name, value);
	}
	
	public static PlayerPermissions createPlayerPermissions(Player p)
	{
		PlayerPermissions data = get(p);
		return data!=null?data:new PlayerPermissions(p);
	}
	
	public static PlayerPermissions get(Player p)
	{
		for(int i = 0;i<permissions.size();i++)
		{
			if(permissions.get(i).getPlayer().equals(p))
				return permissions.get(i);
		}
		return null;
	}
}
