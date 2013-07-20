package me.frodenkvist.armoreditor;

import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;

public class SkillExplosiveArrow
{
	private Location center;
	
	public SkillExplosiveArrow(Location target)
	{
		center = target;
	}
	
	public void run()
	{
		final FireworkEffectPlayer fplayer = new FireworkEffectPlayer();
		FireworkEffect fe = FireworkEffect.builder().flicker(true).with(Type.BALL_LARGE).withColor(Color.RED).trail(false).build();
		try
		{
			fplayer.playFirework(center.getWorld(), center, fe);
			strikeLigtning(center,15,7);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public void strikeLigtning(Location center, int height, int radius)
	{
		int radiusX = radius;
		int radiusZ = radius;
		
		radiusX += 0.5;
        radiusZ += 0.5;
        
        double invRadiusX = 1 / radiusX;
        double invRadiusZ = 1 / radiusZ;

        //int ceilRadiusX = (int) Math.ceil(radiusX);
        //int ceilRadiusZ = (int) Math.ceil(radiusZ);
        
        for(Entity en : center.getWorld().getEntities())
        {
        	if(en == null)
        		continue;
        	if(!(en instanceof LivingEntity))
        		continue;
        	//Bukkit.broadcastMessage("1");
        	LivingEntity le = (LivingEntity)en;
        	Location loc = le.getLocation();
        	//Bukkit.broadcastMessage(center.getBlockX() + "");
        	if(center.getBlockX()+radius-loc.getBlockX() >= 0 && center.getBlockX()+radius-loc.getBlockX() < radius*2)
        	{
        		//Bukkit.broadcastMessage("2");
        		if(center.getBlockZ()+radius-loc.getBlockZ() >= 0 && center.getBlockZ()+radius-loc.getBlockZ() < radius*2)
            	{
        			//Bukkit.broadcastMessage("3");
        			if(center.getBlockY()+height-loc.getBlockY() < center.getBlockY()+height)
                	{
        				//Bukkit.broadcastMessage("4");
                		if(lengthSq((center.getBlockX()+radius-loc.getBlockX() + 1)*invRadiusX, (center.getBlockZ()+radius-loc.getBlockZ() + 1)*invRadiusZ) <= 1)
                		{
                			//Bukkit.broadcastMessage("5");
                			//center.getWorld().strikeLightningEffect(le.getLocation());
                			le.setHealth(le.getHealth()/4);
                			le.setFireTicks(20*6);
                			le.damage(0f);
                		}
                	}
            	}
        	}
        }
	}
	
	private final double lengthSq(double x, double z)
    {
        return (x * x) + (z * z);
    }
}
