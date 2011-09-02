package net.shadowjay1.bukkit.circles;

public enum CirclePermission
{
	INVITE,UNINVITE,DISMISS,SET_NAME,SET_TAG,ALLY,ENEMY,NEUTRAL,DISBAND,SET_CENTER;
	
	public static CirclePermission fromString(String name)
	{
		CirclePermission[] permissions = CirclePermission.values();
		
		for(CirclePermission permission : permissions)
		{
			String pname = permission.toString();
			
			pname = pname.replaceAll("_", "");
			pname = pname.toLowerCase();
			
			String query = name;
			query = query.replaceAll("_", "");
			query = query.toLowerCase();
			
			if(pname.equals(query)) return permission;
		}
		
		return null;
	}
}
