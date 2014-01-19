package me.frodenkvist.armoreditor;

import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import PvpBalance.PVPPlayer;
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
		strikeLigtning(center,4,10,caster);
		return true;
	}
	
	public void strikeLigtning(Location center, int height, int radius, Player caster)
	{
		int radiusX = radius;
		int radiusZ = radius;
		
		radiusX += 0.5;
        radiusZ += 0.5;
        
        double invRadiusX = 1 / radiusX;
        double invRadiusZ = 1 / radiusZ;

        //int ceilRadiusX = (int) Math.ceil(radiusX);
        //int ceilRadiusZ = (int) Math.ceil(radiusZ);
        
        for(Entity en : caster.getNearbyEntities(10, 10, 10))
        {
        	if(en == null)
        		continue;
        	if(!(en instanceof Player))
        		continue;
			PVPPlayer PVPCaster = PvpHandler.getPvpPlayer(caster);
			if(PVPCaster.isInParty())
			{
				PVPPlayer PVPTarget = PvpHandler.getPvpPlayer((Player)en);
				if(PVPCaster.getParty().isMember(PVPTarget))
				{
					 PVPTarget.sethealth(PVPTarget.getMaxHealth());
					 caster.sendMessage(ChatColor.GREEN + "" + ChatColor.BOLD + PVPTarget.getPlayer().getName() + " FULLY HEALED!");
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
