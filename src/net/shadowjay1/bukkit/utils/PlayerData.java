package net.shadowjay1.bukkit.utils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class PlayerData implements Serializable
{
	private static final long serialVersionUID = 5821161533574403725L;
	protected static ArrayList<PlayerData> datas = new ArrayList<PlayerData>();
	protected HashMap<String, Object> data = new HashMap<String, Object>();
	String player;
	
	private PlayerData(Player p)
	{
		player = p.getName();
		datas.add(this);
	}
	
	@SuppressWarnings("unchecked")
	public static void load(JavaPlugin plugin)
	{
		Object t = DataStorage.byPlugin(plugin).readObjectData("playerdata");
		
		if(t!=null)
		{
			datas = (ArrayList<PlayerData>) t;
		}
	}
	
	public static void save(JavaPlugin plugin)
	{
		DataStorage.byPlugin(plugin).writeObjectData("playerdata", datas);
	}
	
	public static PlayerData createPlayerData(Player p)
	{
		PlayerData data = get(p);
		return data!=null?data:new PlayerData(p);
	}
	
	public void setData(String name, Object info)
	{
		data.put(name, info);
	}
	
	public Object getData(String name)
	{
		if(data.containsKey(name))
			return data.get(name);
		else
			return null;
	}
	
	public static void setData(Player p, String name, Object value)
	{
		PlayerData data = createPlayerData(p);
		data.setData(name, value);
	}
	
	public static Object getData(Player p, String name)
	{
		PlayerData data = createPlayerData(p);
		return data!=null?data.getData(name):null;
	}
	
	public static boolean getBooleanData(Player p, String name)
	{
		Object odata = getData(p,name);
		boolean data;
		if(odata==null)
		{
			data = false;
		}
		else
		{
			if(odata instanceof Boolean)
			{
				data = (Boolean) odata;
			}
			else
			{
				data = false;
			}
		}
		return data;
	}
	
	public String getPlayer()
	{
		return player;
	}
	
	public static PlayerData get(Player p)
	{
		for(int i = 0;i<datas.size();i++)
		{
			if(datas.get(i).getPlayer().equals(p.getName()))
				return datas.get(i);
		}
		return null;
	}
}
