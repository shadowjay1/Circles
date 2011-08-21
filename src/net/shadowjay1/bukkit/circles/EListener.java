package net.shadowjay1.bukkit.circles;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityListener;

public class EListener extends EntityListener
{
	public void onEntityDamageByEntity(EntityDamageByEntityEvent event)
	{
		if(event.getEntity() instanceof Player)
		{
			Player player = (Player) event.getEntity();
			
			if(event.getDamager() instanceof Player)
			{
				Player damager = (Player) event.getDamager();
				
				Circle c1 = Circle.byPlayer(player.getName());
				Circle c2 = Circle.byPlayer(damager.getName());
				
				if(c1!=null&&c2!=null)
				{
					if(c1.getName().equals(c2.getName()))
					{
						damager.sendMessage(ChatColor.DARK_GRAY+"You can not damage someone in your own circle!");
					}
				}
			}
		}
	}
}
