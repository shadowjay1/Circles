package net.shadowjay1.bukkit.utils;

public class Wireframe
{
	public static boolean isOn(int x, int y, int z, int width, int height, int length)
	{
		boolean dx = x==1||x==width;
		boolean dy = y==1||y==height;
		boolean dz = z==1||z==length;
		
		return (dx&&dy)||(dy&&dz)||(dx&&dz);
	}
}
