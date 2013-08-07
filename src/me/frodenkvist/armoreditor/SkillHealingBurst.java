package me.frodenkvist.armoreditor;

import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import PvpBalance.PvpHandler;

public class SkillHealingBurst extends Skill
{
	public SkillHealingBurst(int cost)
	{
		super(cost);
	}
	
	public boolean run(Player caster)
	{
		Location center = caster.getLocation();
		final FireworkEffectPlayer fplayer = new FireworkEffectPlayer();
		FireworkEffect fe = FireworkEffect.builder().flicker(true).with(Type.BALL_LARGE).withColor(Color.fromRGB(0, 204, 0)).trail(false).build();
		try
		{
			fplayer.playFirework(center.getWorld(), center, fe);
		}
		catch (Exception e)
		{
			return false;
		}
		strikeLigtning(center,4,4);
		return true;
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
        	if(!(en instanceof Player))
        		continue;
        	//Bukkit.broadcastMessage("1");
        	Player le = (Player)en;
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
                			PvpHandler.getPvpPlayer(le).sethealth(PvpHandler.getPvpPlayer(le).getMaxHealth()); //TODO FIX TO ONLY PARTY MEMBERS
                			//le.setFireTicks(20*6);
                			//le.damage(0);
                		}
                	}
            	}
        	}
        }
	}
	
	public String getName()
	{
		return "Healing Burst";
	}
	
	private final double lengthSq(double x, double z)
    {
        return (x * x) + (z * z);
    }
}
