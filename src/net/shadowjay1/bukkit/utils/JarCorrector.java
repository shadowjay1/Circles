package net.shadowjay1.bukkit.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.jar.JarInputStream;
import java.util.jar.JarOutputStream;
import java.util.zip.ZipEntry;

public class JarCorrector
{
	public static void main(String[] args)
	{
		try
		{
			deleteMetaInfo(new File(args[0]));
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	public static void deleteMetaInfo(File f) throws FileNotFoundException, IOException
	{
		if(f.getName().endsWith(".jar")&&f.exists())
		{
			final int buffer = 2048;
			JarInputStream jis = new JarInputStream(new FileInputStream(f));
			JarOutputStream jos = new JarOutputStream(new FileOutputStream(f.getPath()+".temp"));
			ZipEntry entry;
			while((entry=jis.getNextEntry())!=null)
			{
				int count;
				byte[] data = new byte[buffer];
				if(!entry.getName().startsWith("META-INF"))
				{
					jos.putNextEntry(entry);
					while ((count = jis.read(data, 0, buffer)) != -1)
					{
						jos.write(data, 0, count);
					}
				}
				else
				{
					while ((count = jis.read(data, 0, buffer)) != -1)
					{
					}
				}
			}
			jis.close();
			jos.flush();
			jos.close();
			
			f.delete();
			File file = new File(f.getPath()+".temp");
			file.renameTo(f);
		}
	}
}
