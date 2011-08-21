package net.shadowjay1.bukkit.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.bukkit.Location;
import org.bukkit.plugin.java.JavaPlugin;

public class DataStorage
{
	JavaPlugin cplugin = null;
	private static HashMap<String,DataStorage> storages = new HashMap<String,DataStorage>();
	static Logger log = Logger.getLogger("Minecraft");
	public DataStorage(JavaPlugin plugin)
	{
		storages.put(plugin.getDescription().getName(), this);
	}
	public static DataStorage byPlugin(JavaPlugin plugin)
	{
		return storages.containsKey(plugin.getDescription().getName())?storages.get(plugin.getDatabase().getName()):null;
	}
	public File createDataFolder()
	{
		if(cplugin==null) return null;
		if(cplugin==null) System.out.println("I equal null.");
		File file = cplugin.getDataFolder();
		if(!file.exists())
		{
			file.mkdir();
		}
		return file;
	}
	
	public File createPlayerData(String attribute)
	{
		if(cplugin==null) return null;
		File file = new File(createDataFolder(), "players."+attribute);
		if(!file.exists())
		{
			try
			{
				file.createNewFile();
			}
			catch (IOException e)
			{
				log.log(Level.SEVERE, cplugin.getDescription().getName()+": unable to create data file!");
			}
		}
		return file;
	}
	public void writePlayerData(String attribute, String player, boolean add)
	{
		File file = createPlayerData(attribute);
		try
		{
			BufferedReader br = new BufferedReader(new FileReader(file));
			String s = "";
			String f = "";
			boolean exists = false;
			while((f = br.readLine())!=null)
			{
				if(f.toLowerCase().contains((player.toLowerCase())))
				{
					exists = true;
					if(add)
					{
						s+=f+"\n";
					}
				}
				else
				{
					s += f + "\n";
				}
			}
			br.close();
			if(!exists)
			{
				BufferedWriter bw = new BufferedWriter(new FileWriter(file));
				if(add)	bw.write(s+player+"\n");
				else bw.write(s);
				bw.close();
			}
			if(add)
				log.info(cplugin.getDescription().getName()+": "+player+" now has the attribute: "+attribute+".");
			else
				log.info(cplugin.getDescription().getName()+": "+player+" now has the attribute: "+attribute+".");
		}
		catch (IOException e)
		{
			log.log(Level.SEVERE, cplugin.getDescription().getName()+": unable to load data file!");
		}
	}
	
	public void writePlayerData(JavaPlugin plugin, String attribute, String player, boolean add)
	{
		JavaPlugin tplugin = cplugin;
		cplugin = plugin;
		File file = createPlayerData(attribute);
		cplugin = tplugin;
		try
		{
			BufferedReader br = new BufferedReader(new FileReader(file));
			String s = "";
			String f = "";
			boolean exists = false;
			while((f = br.readLine())!=null)
			{
				if(f.toLowerCase().contains((player.toLowerCase())))
				{
					exists = true;
					if(add)
					{
						s+=f+"\n";
					}
				}
				else
				{
					s += f + "\n";
				}
			}
			br.close();
			if(!exists)
			{
				BufferedWriter bw = new BufferedWriter(new FileWriter(file));
				if(add)	bw.write(s+player+"\n");
				else bw.write(s);
				bw.close();
			}
			if(add)
				log.info(cplugin.getDescription().getName()+": "+player+" now has the attribute: "+attribute+".");
			else
				log.info(cplugin.getDescription().getName()+": "+player+" now has the attribute: "+attribute+".");
		}
		catch (IOException e)
		{
			log.log(Level.SEVERE, cplugin.getDescription().getName()+": unable to load data file!");
		}
	}
	
	public Object readPlayerData(String attribute, String player)
	{
		File file = createPlayerData(attribute);
		
		try
		{
			BufferedReader br = new BufferedReader(new FileReader(file));
			String f = "";
			while((f = br.readLine())!=null)
			{
				if(f.equals(player))
					return true;
			}
			br.close();
		}
		catch (IOException e)
		{
			log.log(Level.SEVERE, cplugin.getDescription().getName()+": unable to load data file!");
		}
		return false;
	}
	
	public Object readPlayerData(JavaPlugin plugin, String attribute, String player)
	{
		JavaPlugin tplugin = cplugin;
		cplugin = plugin;
		
		File file = createPlayerData(attribute);
		
		cplugin = tplugin;
		try
		{
			BufferedReader br = new BufferedReader(new FileReader(file));
			String f = "";
			while((f = br.readLine())!=null)
			{
				if(f.equals(player))
					return true;
			}
			br.close();
		}
		catch (IOException e)
		{
			log.log(Level.SEVERE, cplugin.getDescription().getName()+": unable to load data file!");
		}
		return false;
	}
	
	public File createLandData(String attribute)
	{
		File file = new File(createDataFolder(), "land."+attribute);
		if(!file.exists())
		{
			try
			{
				file.createNewFile();
			}
			catch (IOException e)
			{
				log.log(Level.SEVERE, cplugin.getDescription().getName()+": unable to create data file!");
			}
		}
		return file;
	}
	public void writeLandData(String attribute, String[] owner, Location from, Location to, int layer, boolean keep)
	{
		if(from.getBlockX()>to.getBlockX())
		{
			double tempx = from.getX();
			from.setX(to.getX());
			to.setX(tempx);
		}
		if(from.getBlockY()>to.getBlockY())
		{
			double tempy = from.getY();
			from.setY(to.getY());
			to.setY(tempy);
		}
		if(from.getBlockZ()>to.getBlockZ())
		{
			double tempz = from.getZ();
			from.setZ(to.getZ());
			to.setZ(tempz);
		}
		
		String players = "";
		for(int i = 0;i<owner.length;i++)
		{
			players+=owner[i]+" ";
		}
		
		String l = Integer.toString(layer)+" "+players+Integer.toString(from.getBlockX())+" "+Integer.toString(from.getBlockY())+" "+Integer.toString(from.getBlockZ())+" "+Integer.toString(to.getBlockX())+" "+Integer.toString(to.getBlockY())+" "+Integer.toString(to.getBlockZ());
		File file = createLandData(attribute);
		
		try
		{
			BufferedReader br = new BufferedReader(new FileReader(file));
			String f = "";
			String s = "";
			while((f = br.readLine())!=null)
			{
				String[] words = f.split(" ");
				if(!(equals(from,new int[]{Integer.parseInt(words[words.length-6]),Integer.parseInt(words[words.length-5]),Integer.parseInt(words[words.length-4])})&&equals(to,new int[]{Integer.parseInt(words[words.length-3]),Integer.parseInt(words[words.length-2]),Integer.parseInt(words[words.length-1])})))
					s+=f+"\n";
				if(f.equals(l))
				{
					br.close();
					return;
				}
			}
			br.close();
			BufferedWriter bw = new BufferedWriter(new FileWriter(file));
			if(keep) bw.write(s+l+"\n");
			else bw.write(s);
			bw.close();
		}
		catch (IOException e)
		{
			log.log(Level.SEVERE, cplugin.getDescription().getName()+": unable to load data file!");
		}
	}
	public void writeLandData(JavaPlugin plugin, String attribute, String[] owner, Location from, Location to, int layer, boolean keep)
	{
		JavaPlugin tplugin = cplugin;
		cplugin = plugin;
		
		if(from.getBlockX()>to.getBlockX())
		{
			double tempx = from.getX();
			from.setX(to.getX());
			to.setX(tempx);
		}
		if(from.getBlockY()>to.getBlockY())
		{
			double tempy = from.getY();
			from.setY(to.getY());
			to.setY(tempy);
		}
		if(from.getBlockZ()>to.getBlockZ())
		{
			double tempz = from.getZ();
			from.setZ(to.getZ());
			to.setZ(tempz);
		}
		
		String players = "";
		for(int i = 0;i<owner.length;i++)
		{
			players+=owner[i]+" ";
		}
		
		String l = Integer.toString(layer)+" "+players+Integer.toString(from.getBlockX())+" "+Integer.toString(from.getBlockY())+" "+Integer.toString(from.getBlockZ())+" "+Integer.toString(to.getBlockX())+" "+Integer.toString(to.getBlockY())+" "+Integer.toString(to.getBlockZ());
		File file = createLandData(attribute);
		
		cplugin = tplugin;
		
		try
		{
			BufferedReader br = new BufferedReader(new FileReader(file));
			String f = "";
			String s = "";
			while((f = br.readLine())!=null)
			{
				String[] words = f.split(" ");
				if(!(equals(from,new int[]{Integer.parseInt(words[words.length-6]),Integer.parseInt(words[words.length-5]),Integer.parseInt(words[words.length-4])})&&equals(to,new int[]{Integer.parseInt(words[words.length-3]),Integer.parseInt(words[words.length-2]),Integer.parseInt(words[words.length-1])})))
					s+=f+"\n";
				if(f.equals(l))
				{
					br.close();
					return;
				}
			}
			br.close();
			BufferedWriter bw = new BufferedWriter(new FileWriter(file));
			if(keep) bw.write(s+l+"\n");
			else bw.write(s);
			bw.close();
		}
		catch (IOException e)
		{
			log.log(Level.SEVERE, cplugin.getDescription().getName()+": unable to load data file!");
		}
	}
	public int[] readLandExtraData(String attribute, Location loc)
	{
		File file = createLandData(attribute);

		try
		{
			BufferedReader br = new BufferedReader(new FileReader(file));
			String f = "";
			while((f = br.readLine())!=null)
			{
				String[] words = f.split(" ");
				int[] from = {Integer.parseInt(words[words.length-6]),Integer.parseInt(words[words.length-5]),Integer.parseInt(words[words.length-4])};
				int[] to = {Integer.parseInt(words[words.length-3]),Integer.parseInt(words[words.length-2]),Integer.parseInt(words[words.length-1])};
				
				if(within(loc, from, to))
				{
					return new int[]{from[0],from[1],from[2],to[0],to[1],to[2],Integer.parseInt(words[0])};
				}
			}
			br.close();
		}
		catch (IOException e)
		{
			log.log(Level.SEVERE, cplugin.getDescription().getName()+": unable to load data file!");
		}
		return null;
	}
	public int[] readLandExtraData(JavaPlugin plugin, String attribute, Location loc)
	{
		JavaPlugin tplugin = cplugin;
		cplugin = plugin;
		
		File file = createLandData(attribute);
		
		cplugin = tplugin;

		try
		{
			BufferedReader br = new BufferedReader(new FileReader(file));
			String f = "";
			while((f = br.readLine())!=null)
			{
				String[] words = f.split(" ");
				int[] from = {Integer.parseInt(words[words.length-6]),Integer.parseInt(words[words.length-5]),Integer.parseInt(words[words.length-4])};
				int[] to = {Integer.parseInt(words[words.length-3]),Integer.parseInt(words[words.length-2]),Integer.parseInt(words[words.length-1])};
				
				if(within(loc, from, to))
				{
					return new int[]{from[0],from[1],from[2],to[0],to[1],to[2],Integer.parseInt(words[0])};
				}
			}
			br.close();
		}
		catch (IOException e)
		{
			log.log(Level.SEVERE, cplugin.getDescription().getName()+": unable to load data file!");
		}
		return null;
	}
	public String[] readLandData(String attribute, Location loc)
	{
		File file = createLandData(attribute);

		try
		{
			BufferedReader br = new BufferedReader(new FileReader(file));
			String f = "";
			String[] cowners = null;
			int clayer = 0;
			while((f = br.readLine())!=null)
			{
				String[] words = f.split(" ");
				String ownerstring = "";
				for(int i = 1;i<words.length-6;i++)
				{
					ownerstring+=words[i]+" ";
				}
				ownerstring = ownerstring.trim();
				String[] owners = ownerstring.split(" ");
				int layer = Integer.parseInt(words[0]);
				int[] from = {Integer.parseInt(words[words.length-6]),Integer.parseInt(words[words.length-5]),Integer.parseInt(words[words.length-4])};
				int[] to = {Integer.parseInt(words[words.length-3]),Integer.parseInt(words[words.length-2]),Integer.parseInt(words[words.length-1])};
				
				if(within(loc, from, to)&&layer>clayer)
				{
					cowners = owners;
					clayer = layer;
				}
			}
			br.close();
			if(clayer>0)
			{
				return cowners;
			}
		}
		catch (IOException e)
		{
			log.log(Level.SEVERE, cplugin.getDescription().getName()+": unable to load data file!");
		}
		return null;
	}
	
	public String[] readLandData(JavaPlugin plugin, String attribute, Location loc)
	{
		JavaPlugin tplugin = cplugin;
		cplugin = plugin;
		
		File file = createLandData(attribute);
		
		cplugin = tplugin;

		try
		{
			BufferedReader br = new BufferedReader(new FileReader(file));
			String f = "";
			String[] cowners = null;
			int clayer = 0;
			while((f = br.readLine())!=null)
			{
				String[] words = f.split(" ");
				String ownerstring = "";
				for(int i = 1;i<words.length-6;i++)
				{
					ownerstring+=words[i]+" ";
				}
				ownerstring = ownerstring.trim();
				String[] owners = ownerstring.split(" ");
				int layer = Integer.parseInt(words[0]);
				int[] from = {Integer.parseInt(words[words.length-6]),Integer.parseInt(words[words.length-5]),Integer.parseInt(words[words.length-4])};
				int[] to = {Integer.parseInt(words[words.length-3]),Integer.parseInt(words[words.length-2]),Integer.parseInt(words[words.length-1])};
				
				if(within(loc, from, to)&&layer>clayer)
				{
					cowners = owners;
					clayer = layer;
				}
			}
			br.close();
			if(clayer>0)
			{
				return cowners;
			}
		}
		catch (IOException e)
		{
			log.log(Level.SEVERE, cplugin.getDescription().getName()+": unable to load data file!");
		}
		return null;
	}
	
	private boolean within(Location l, int[] from, int[] to)
	{
		if(l.getBlockX()<from[0]) return false;
		if(l.getBlockY()<from[1]) return false;
		if(l.getBlockZ()<from[2]) return false;
		if(l.getBlockX()>to[0]) return false;
		if(l.getBlockY()>to[1]) return false;
		if(l.getBlockZ()>to[2]) return false;
		return true;
	}
	private boolean equals(Location l, int[] otherloc)
	{
		if(l.getBlockX()!=otherloc[0]) return false;
		if(l.getBlockY()!=otherloc[1]) return false;
		if(l.getBlockZ()!=otherloc[2]) return false;
		return true;
	}
	public File createObjectData(String attribute)
	{
		File file = new File(createDataFolder(), "object."+attribute);
		if(!file.exists())
		{
			try
			{
				file.createNewFile();
			}
			catch (IOException e)
			{
				log.log(Level.SEVERE, cplugin.getDescription().getName()+": unable to create data file!");
			}
		}
		return file;
	}
	public void writeObjectData(String attribute, Object object)
	{
		File file = createObjectData(attribute);
		try
		{
			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file));
			oos.writeObject(object);
			oos.flush();
			oos.close();
		}
		catch(IOException e)
		{
			log.log(Level.SEVERE, cplugin.getDescription().getName()+": unable to load data file!");
			e.printStackTrace();
		}
	}
	public void writeObjectData(JavaPlugin plugin, String attribute, Object object)
	{
		JavaPlugin tplugin = cplugin;
		cplugin = plugin;
		File file = createObjectData(attribute);
		cplugin = tplugin;
		
		try
		{
			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file));
			oos.writeObject(object);
			oos.flush();
			oos.close();
		}
		catch(IOException e)
		{
			log.log(Level.SEVERE, cplugin.getDescription().getName()+": unable to load data file!");
			e.printStackTrace();
		}
	}
	public Object readObjectData(String attribute)
	{
		File file = createObjectData(attribute);
		try
		{
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
			Object object = ois.readObject();
			ois.close();
			return object;
		}
		catch(Exception e)
		{
			log.log(Level.SEVERE, cplugin.getDescription().getName()+": unable to load data file!");
			e.printStackTrace();
		}
		return null;
	}
	public Object readObjectData(JavaPlugin plugin, String attribute)
	{
		JavaPlugin tplugin = cplugin;
		cplugin = plugin;
		File file = createObjectData(attribute);
		cplugin = tplugin;
		
		try
		{
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
			Object object = ois.readObject();
			ois.close();
			return object;
		}
		catch(Exception e)
		{
			log.log(Level.SEVERE, cplugin.getDescription().getName()+": unable to load data file!");
			e.printStackTrace();
		}
		return null;
	}
}
