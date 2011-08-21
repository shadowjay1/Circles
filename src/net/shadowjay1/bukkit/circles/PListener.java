package net.shadowjay1.bukkit.circles;

import net.shadowjay1.bukkit.utils.PlayerData;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerListener;

public class PListener extends PlayerListener
{
	public void onPlayerChat(PlayerChatEvent event)
	{
		Circle circle = Circle.byPlayer(event.getPlayer().getName());
		
		if(circle!=null)
		{
			event.setFormat(ChatColor.GRAY+"["+circle.getTag()+"]"+ChatColor.WHITE+" "+event.getPlayer().getDisplayName()+": "+event.getMessage());
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
}
