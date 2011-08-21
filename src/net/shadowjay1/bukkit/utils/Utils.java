package net.shadowjay1.bukkit.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Utils
{
	public static ItemStack[] getInventoryItems(Player p)
	{
		ItemStack[] itemstacks = p.getInventory().getContents();
		ArrayList<ItemStack> itemslist = new ArrayList<ItemStack>();
		for(int i = 0;i<itemstacks.length;i++)
		{
			if(itemstacks[i]!=null)
			{
				itemslist.add(itemstacks[i]);
			}
		}
		ItemStack[] items = new ItemStack[itemslist.size()];
		for(int i = 0;i<items.length;i++)
		{
			items[i] = itemslist.get(i);
		}
		return items;
	}
	public static LivingEntity getClosestLiving(Block target, int radius, int playerid)
	{
		List<LivingEntity> living = target.getWorld().getLivingEntities();
		for(int i = 0;i<living.size();i++)
		{
			LivingEntity entity = living.get(i);
			int xdis = getDistance(entity.getLocation().getBlockX(), target.getX());
			int ydis = getDistance(entity.getLocation().getBlockY(), target.getY());
			int zdis = getDistance(entity.getLocation().getBlockZ(), target.getZ());
			if(xdis<radius&&ydis<radius&&zdis<radius&&entity.getEntityId()!=playerid)
			{
				return entity;
			}
		}
		return null;
	}
	public static int getDistance(int first, int second)
	{
		return Math.abs(Math.abs(first)-Math.abs(second));
	}
	public static double getDistance(double first, double second)
	{
		return Math.abs(Math.abs(first)-Math.abs(second));
	}
	public static Entity getEntityById(World w, int id)
	{
		List<Entity> entities = w.getEntities();
		for(int i = 0;i<entities.size();i++)
		{
			if(entities.get(i).getEntityId()==id)
				return entities.get(i);
		}
		return null;
	}
	public static Entity[] arrangeByDistance(Entity[] entities, Location loc)
	{
		Entity[] newentities = new Entity[entities.length];
		ArrayList<Entity> list = new ArrayList<Entity>();
		boolean placed;
		for(int i = 0;i<entities.length;i++)
		{
			placed = false;
			for(int t = 0;t<list.size()&&!placed;t++)
			{
				if(getDistance(entities[i].getLocation(),loc)<getDistance(list.get(t).getLocation(),loc))
				{
					list.add(t,entities[i]);
					placed = true;
				}
			}
			if(list.size()==0)
			{
				list.add(0,entities[i]);
				placed = true;
			}
		}
		newentities = list.toArray(newentities);
		return newentities;
	}
	public static double getDistance(Location from, Location to)
	{
		double xdis = getDistance(from.getX(),to.getX());
		double ydis = getDistance(from.getY(),to.getY());
		double zdis = getDistance(from.getZ(),to.getZ());
		
		double xzdis = Math.sqrt(Math.pow(xdis,2)+Math.pow(zdis,2));
		double dis = Math.sqrt(Math.pow(xzdis,2)+Math.pow(ydis,2));
		return dis;
	}
	public static Location locOnCircle(Location center, int radius)
	{
		World w = center.getWorld();
		double x = new Random().nextInt(radius);
		double z = Math.sqrt(Math.pow(radius,2)-Math.pow(x,2));
		Location l = new Location(w,x,center.getY(),z);
		l.setY(w.getHighestBlockYAt(l)+1);
		return l;
	}
}
