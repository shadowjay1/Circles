package net.shadowjay1.bukkit.circles;

import java.util.logging.Logger;

import net.shadowjay1.bukkit.circles.Circle.Status;
import net.shadowjay1.bukkit.utils.PlayerData;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.plugin.java.JavaPlugin;

public class Circles extends JavaPlugin
{
	static Logger log = Logger.getLogger("Minecraft");
	public static Circles circles = null;

	public void onEnable()
	{
		circles = this;

		PListener pl = new PListener();
		BListener bl = new BListener();

		this.getServer().getPluginManager().registerEvent(Event.Type.PLAYER_CHAT, pl, Event.Priority.Low, this);
		this.getServer().getPluginManager().registerEvent(Event.Type.BLOCK_PLACE, bl, Event.Priority.Normal, this);
		this.getServer().getPluginManager().registerEvent(Event.Type.BLOCK_BREAK, bl, Event.Priority.Normal, this);

		log.info("[Circles] enabled.");
	}

	public void onDisable()
	{
		log.info("[Circles] disabled.");
	}

	public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
	{
		if(!(sender instanceof Player))
			return true;

		Player player = (Player) sender;

		if(command.getName().equals("circle"))
		{
			if(args.length<1)
			{
				Circle circle = Circle.byPlayer(player.getName());

				if(circle!=null)
				{
					player.sendMessage(ChatColor.YELLOW+"You are currently in "+circle.getName());
					player.sendMessage(ChatColor.YELLOW+"Balance: "+Integer.toString(circle.getBalance()));
					player.sendMessage(ChatColor.YELLOW+"Tag: "+circle.getTag());
					player.sendMessage(ChatColor.YELLOW+"Population: "+Integer.toString(circle.getMembers().length));
				}
				else
				{
					player.sendMessage(ChatColor.YELLOW+"You are not in a circle.");
				}
			}
			else
			{
				if(args[0].equals("create"))
				{
					if(args.length!=2)
					{
						player.sendMessage(ChatColor.YELLOW+"Usage: /f create [circle name]");
					}
					else
					{
						if(Circle.byName(args[1])==null)
						{
							Circle circle = new Circle(args[1]);
							circle.addMember(player.getName());
							circle.setRank(player.getName(), 1);

							player.sendMessage(ChatColor.GREEN+"Circle created.");
						}

						else
						{
							player.sendMessage(ChatColor.RED+"A circle with that name already exists!");
						}
					}
				}
				else if(args[0].equals("private")||args[0].equals("chat"))
				{
					if(Circle.byPlayer(player.getName())==null)
					{
						player.sendMessage(ChatColor.YELLOW+"You are not in a circle.");
						return true;
					}

					boolean fchat = !PlayerData.getBooleanData(player, "cchat");

					if(fchat)
					{
						player.sendMessage(ChatColor.GREEN+"Circle-only chat enabled.");
					}
					else
					{
						player.sendMessage(ChatColor.RED+"Circle-only chat disabled.");
					}

					PlayerData.setData(player, "fchat", fchat);
				}
				else if(args[0].equals("invite"))
				{
					if(args.length!=2)
					{
						player.sendMessage(ChatColor.YELLOW+"Usage: /f invite [player name]");
					}
					else
					{
						Circle circle = Circle.byPlayer(player.getName());

						Player target = this.getServer().getPlayer(args[1]);

						if(player!=null&&circle!=null)
						{
							circle.addInvited(args[1]);
							target.sendMessage(ChatColor.GREEN+"You have been invited to "+circle.getName());
							player.sendMessage(ChatColor.GREEN+args[1]+" has been invited to your circle.");
						}
					}
				}
				else if(args[0].equals("uninvite"))
				{
					if(args.length!=2)
					{
						player.sendMessage(ChatColor.YELLOW+"Usage: /f uninvite [player name]");
					}
					else
					{
						Circle circle = Circle.byPlayer(player.getName());

						Player target = this.getServer().getPlayer(args[1]);
						if(player!=null)
						{
							circle.removeInvited(args[1]);
							target.sendMessage(ChatColor.YELLOW+"You are no longer invited to "+circle.getName());
							player.sendMessage(ChatColor.YELLOW+args[1]+" is no longer invited to your circle.");
						}
					}
				}
				else if(args[0].equals("dismiss"))
				{
					if(args.length!=2)
					{
						player.sendMessage(ChatColor.YELLOW+"Usage: /f dismiss [player name]");
					}
					else
					{
						Circle circle = Circle.byPlayer(player.getName());

						Player target = this.getServer().getPlayer(args[1]);
						if(target!=null)
						{
							circle.removeMember(args[1]);
							target.sendMessage(ChatColor.YELLOW+"You have been dismissed from "+circle.getName());
							player.sendMessage(ChatColor.YELLOW+args[1]+" has been dismissed from your circle.");
						}
					}
				}
				else if(args[0].equals("rank"))
				{
					if(args.length!=4)
					{
						player.sendMessage(ChatColor.YELLOW+"Usage: /f rank [player name] [custom rank] [permissions id]");
					}
					else
					{
						Circle circle = Circle.byPlayer(player.getName());
						if(circle!=null)
						{
							if(circle.containsMember(args[1]))
							{
								Member m = circle.getMember(args[1]);
								m.setRank(Integer.parseInt(args[3]));
								m.setTitle(args[2]);
								player.sendMessage(args[1]+" has been reranked.");
							}
						}
					}
				}
				else if(args[0].equals("name"))
				{
					if(args.length!=2)
					{
						player.sendMessage(ChatColor.YELLOW+"Usage: /f name [circle name]");
					}
					else
					{
						Circle circle = Circle.byPlayer(player.getName());
						circle.setName(args[1]);
						player.sendMessage(ChatColor.YELLOW+"Circle name changed to "+args[1]);
					}
				}
				else if(args[0].equals("ally"))
				{
					if(args.length!=2)
					{
						player.sendMessage(ChatColor.YELLOW+"Usage: /f ally [circle name]");
					}
					else
					{
						Circle circle = Circle.byPlayer(player.getName());
						Circle ally = Circle.byName(args[1]);
						if(circle!=null&&ally!=null)
						{
							circle.setStatus(ally.getName(),Status.Ally);
							player.sendMessage(ChatColor.GREEN+"Your circle is now friendly towards "+args[1]);
						}
					}
				}
				else if(args[0].equals("enemy"))
				{
					if(args.length!=2)
					{
						player.sendMessage(ChatColor.YELLOW+"Usage: /f enemy [circle name]");
					}
					else
					{
						Circle circle = Circle.byPlayer(player.getName());
						Circle enemy = Circle.byName(args[1]);
						if(circle!=null&&enemy!=null)
						{
							circle.setStatus(enemy.getName(),Status.Enemy);
							player.sendMessage(ChatColor.RED+"Your circle is now hostile towards "+args[1]);
						}
					}
				}
				else if(args[0].equals("neutral"))
				{
					if(args.length!=2)
					{
						player.sendMessage(ChatColor.YELLOW+"Usage: /f neutral [circle name]");
					}
					else
					{
						Circle circle = Circle.byPlayer(player.getName());
						Circle neutral = Circle.byName(args[1]);
						if(circle!=null&&neutral!=null)
						{
							circle.setStatus(neutral.getName(),Status.Neutral);
							player.sendMessage(ChatColor.RED+"Your circle is now neutral towards "+args[1]);
						}
					}
				}
				else if(args[0].equals("disband"))
				{
					Circle circle = Circle.byPlayer(player.getName());
					if(circle!=null)
					{
						circle.remove();
						player.sendMessage(ChatColor.RED+"Circle disbanded.");
					}
					else
					{
						player.sendMessage(ChatColor.RED+"You are not in a circle.");
					}
				}
				else if(args[0].equals("bank"))
				{
					
				}
				else if(args[0].equals("center"))
				{
					Circle circle = Circle.byPlayer(player.getName());
					
					if(circle!=null)
					{
						circle.setOriginX(player.getLocation().getBlockX());
						circle.setOriginZ(player.getLocation().getBlockZ());
						
						player.sendMessage(ChatColor.GRAY+"Circle center changed.");
					}
					else
					{
						player.sendMessage(ChatColor.RED+"You are not in a circle.");
					}
				}
			}
		}

		return true;
	}
}
