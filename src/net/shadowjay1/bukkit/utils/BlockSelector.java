package net.shadowjay1.bukkit.utils;

import java.lang.reflect.Method;
import java.util.ArrayList;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;

public class BlockSelector
{
	private static ArrayList<BlockSelector> selectors = new ArrayList<BlockSelector>();
	Player player;
	int[] ids;
	Method method;
	Block block = null;
	public BlockSelector(Player p, int[] i)
	{
		if(get(p) != null)
			get(p).remove();
		player = p;
		ids = i;
		selectors.add(this);
	}
	public void setBlock(Block b)
	{
		for(int i = 0;i<ids.length;i++)
		{
			if(b.getTypeId()==ids[i]||ids[i]==-1)
			{
				block = b;
				player.sendMessage("Door selected.");
			}
		}
	}
	public Player getPlayer()
	{
		return player;
	}
	public static BlockSelector get(Player p)
	{
		for(int i = 0;i<selectors.size();i++)
		{
			if(selectors.get(i).getPlayer().equals(p))
			{
				return selectors.get(i);
			}
		}
		return null;
	}
	public boolean waiting()
	{
		return block==null;
	}
	public Block getBlock()
	{
		return block;
	}
	public void remove()
	{
		selectors.remove(this);
	}
}
