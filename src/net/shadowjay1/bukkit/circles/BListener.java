package net.shadowjay1.bukkit.circles;

import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockListener;
import org.bukkit.event.block.BlockPlaceEvent;

public class BListener extends BlockListener
{
	public void onBlockPlace(BlockPlaceEvent event)
	{
		Block b = event.getBlockPlaced();
		
		int x = b.getX();
		int z = b.getZ();
		
		for(int i = 0;i<Circle.circles.size();i++)
		{
			Circle circle = Circle.circles.get(i);
			
			int rx = x - circle.getOriginX();
			int rz = z - circle.getOriginZ();
			
			if(rx*rx+rz*rz<=circle.getProtectionRadius())
			{
				if(!circle.containsMember(event.getPlayer().getName()))
				{
					event.getPlayer().sendMessage(ChatColor.RED+"You cannot build in the territory of "+circle.getName());
					event.setCancelled(true);
				}
				return;
			}
		}
	}
	
	public void onBlockBreak(BlockBreakEvent event)
	{
		Block b = event.getBlock();
		
		int x = b.getX();
		int z = b.getZ();
		
		for(int i = 0;i<Circle.circles.size();i++)
		{
			Circle circle = Circle.circles.get(i);
			
			int rx = x - circle.getOriginX();
			int rz = z - circle.getOriginZ();
			
			if(rx*rx+rz*rz<=circle.getProtectionRadius())
			{
				if(!circle.containsMember(event.getPlayer().getName()))
				{
					event.getPlayer().sendMessage(ChatColor.RED+"You cannot destroy in the territory of "+circle.getName());
					event.setCancelled(true);
				}
				return;
			}
		}
	}
}
