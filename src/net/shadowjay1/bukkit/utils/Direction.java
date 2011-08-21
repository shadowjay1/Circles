package net.shadowjay1.bukkit.utils;

import java.io.Serializable;
import java.util.Random;

import org.bukkit.block.BlockFace;
import org.bukkit.util.Vector;

public enum Direction implements Serializable
{
	NORTH,EAST,WEST,SOUTH,UP,DOWN;
	public static final Direction[] ALL = new Direction[]{NORTH,EAST,WEST,SOUTH,UP,DOWN};
	public static final Direction[] FLAT = new Direction[]{NORTH,EAST,WEST,SOUTH};
	public static Vector getDirection(Direction d)
	{
		if(d.equals(NORTH))
		{
			return new Vector(-1,0,0);
		}
		else if(d.equals(EAST))
		{
			return new Vector(0,0,-1);
		}
		else if(d.equals(WEST))
		{
			return new Vector(0,0,1);
		}
		else if(d.equals(SOUTH))
		{
			return new Vector(1,0,0);
		}
		else if(d.equals(UP))
		{
			return new Vector(0,1,0);
		}
		else if(d.equals(DOWN))
		{
			return new Vector(0,-1,0);
		}
		return new Vector(0,0,0);
	}
	public static Direction faceToDir(BlockFace face)
	{
		if(face.equals(BlockFace.NORTH))
		{
			return NORTH;
		}
		else if(face.equals(BlockFace.EAST))
		{
			return EAST;
		}
		else if(face.equals(BlockFace.WEST))
		{
			return WEST;
		}
		else if(face.equals(BlockFace.SOUTH))
		{
			return SOUTH;
		}
		else if(face.equals(BlockFace.UP))
		{
			return UP;
		}
		else if(face.equals(BlockFace.DOWN))
		{
			return DOWN;
		}
		
		return null;
	}
	public static Direction random(Direction[] dirs)
	{
		return dirs[Math.round(new Random(5).nextFloat())];
	}
}
