package net.shadowjay1.bukkit.utils;
import java.util.ArrayList;

import org.bukkit.Location;
import org.bukkit.entity.Player;

public class LocationHolder
{
	private static ArrayList<LocationHolder> locations = new ArrayList<LocationHolder>();
	private Player player;
	private Location location;
	public LocationHolder(Player p, Location l)
	{
		player = p;
		location = l;
		locations.add(this);
	}
	public void remove()
	{
		locations.remove(this);
	}
	public Player getPlayer()
	{
		return player;
	}
	public Location getLocation()
	{
		return location;
	}
	public static LocationHolder getLocationHolder(Player p)
	{
		for(int i = 0;i < locations.size();i++)
		{
			LocationHolder lh = locations.get(i);
			if(lh.getPlayer().equals(p))
			{
				return lh;
			}
		}
		return null;
	}
}
