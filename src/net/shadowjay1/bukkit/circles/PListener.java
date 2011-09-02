package net.shadowjay1.bukkit.circles;

import net.shadowjay1.bukkit.utils.PlayerData;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerListener;
import org.bukkit.event.player.PlayerMoveEvent;

public class PListener extends PlayerListener
{
	public void onPlayerChat(PlayerChatEvent event)
	{
		Circle circle = Circle.byPlayer(event.getPlayer().getName());

		if(circle!=null)
		{
			event.setFormat(ChatColor.GRAY+"["+circle.getCircleRank(circle.getMember(event.getPlayer().getName()).getRank()).getName()+"]"+"["+circle.getTag()+"]"+ChatColor.WHITE+" "+event.getPlayer().getDisplayName()+": "+event.getMessage());
			//event.setMessage(ChatColor.GRAY+"["+circle.getName()+"] "+ChatColor.WHITE+event.getMessage());

			if(PlayerData.getBooleanData(event.getPlayer(), "cchat"))
			{
				event.setCancelled(true);

				String[] players = circle.getMembers();

				for(String p : players)
				{
					Player player = Circles.circles.getServer().getPlayer(p);

					if(player!=null)
					{
						player.sendMessage(ChatColor.DARK_AQUA+event.getPlayer().getName()+ChatColor.WHITE+": "+event.getMessage());
					}
				}
			}
		}
	}

	public void onPlayerMove(PlayerMoveEvent event)
	{
		int x = event.getTo().getBlockX();
		int z = event.getTo().getBlockZ();

		Object o = PlayerData.getData(event.getPlayer(), "lastterritory");
		
		for(int i = 0;i<Circle.circles.size();i++)
		{
			Circle circle = Circle.circles.get(i);

			int rx = x - circle.getOriginX();
			int rz = z - circle.getOriginZ();

			if(rx*rx+rz*rz<=circle.getProtectionRadius())
			{
				if(o!=null)
				{
					if(!((String) o).equals(circle.getName()))
					{
						event.getPlayer().sendMessage(ChatColor.GRAY+"You have entered the territory of "+circle.getName()+".");
						PlayerData.setData(event.getPlayer(), "lastterritory", circle.getName());
					}
				}
				else
				{
					event.getPlayer().sendMessage(ChatColor.GRAY+"You have entered the territory of "+circle.getName()+".");
					PlayerData.setData(event.getPlayer(), "lastterritory", circle.getName());
				}
				
				return;
			}
		}
		
		if(o!=null)
		{
			if(!((String) o).equals(""))
			{
				event.getPlayer().sendMessage(ChatColor.GRAY+"You have entered the wilderness.");
				PlayerData.setData(event.getPlayer(), "lastterritory", "");
			}
		}
		else
		{
			event.getPlayer().sendMessage(ChatColor.GRAY+"You have entered the wilderness.");
			PlayerData.setData(event.getPlayer(), "lastterritory", "");
		}
	}
}
