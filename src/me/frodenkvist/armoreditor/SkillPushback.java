package me.frodenkvist.armoreditor;

import me.ThaH3lper.com.Entitys.Mob;
import me.ThaH3lper.com.Entitys.MobsHandler;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import PvpBalance.Damage;
import PvpBalance.Effects;
import PvpBalance.PvpHandler;

	public class SkillPushback extends Skill
	{
		public SkillPushback(int cost)
		{
			super(cost);
		}
		
		public boolean run(final Player caster)
		{
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
	        
	        for(Entity en : center.getWorld().getEntities())
	        {
	        	if(en == null)
	        		continue;
	        	if(!(en instanceof LivingEntity))
	        		continue;
	        	if(en instanceof Player)
	        	{
	        		if(((Player)en).equals(caster))
	        			continue;
	        	}
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
	                			center.getWorld().strikeLightningEffect(le.getLocation());
	                			if(!Damage.canHit(le, caster))
	                				continue;
	                			Mob mob = MobsHandler.getMob(le);
	                			if(mob != null)
	                			{
	                				if(mob.isEpicImmune())
	                					continue;
	                			}
	                			if(le instanceof Player)
	                				push((Player)le);
	                		}
	                	}
	            	}
	        	}
	        }
		}
		
		public String getName()
		{
			return "Expel";
		}
		
		private final double lengthSq(double x, double z)
	    {
	        return (x * x) + (z * z);
	    }
		private void push(Player hit){
				double d = 0.9;
		        float hForce = 15 / 5.0F;
		        float vForce = 12 / 5.0F;
				Vector direction = hit.getLocation().getDirection();
			    Vector forward = direction.multiply(-6);
			    Vector v = hit.getLocation().toVector().subtract(hit.getLocation().add(0,0,0).toVector());
			    v.add(forward);
			    v.setY(0.7);
			    v.normalize();
			    v.multiply(hForce*d);
			    v.setY(vForce*d);
			    hit.setVelocity(v);
				Effects.blockedPlayer(hit);
				hit.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "YOU HAVE BEEN FORCED BACK!");
	   }
}
