package me.frodenkvist.armoreditor;

import java.util.Iterator;

import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import PvpBalance.Damage;

public class SkillClash extends Skill
{
	public SkillClash(int cost)
	{
		super(cost);
	}
	
	public boolean run(Player caster)
	{
		final FireworkEffectPlayer fplayer = new FireworkEffectPlayer();
		FireworkEffect fe = FireworkEffect.builder().with(Type.BURST).withColor(Color.BLUE).withColor(Color.AQUA).flicker(true).build();
		Iterator<Entity> itr = caster.getNearbyEntities(5, 5, 5).iterator();
		PotionEffect blind = new PotionEffect(PotionEffectType.BLINDNESS,20*5,1);
		while(itr.hasNext())
		{
			
			Entity e = itr.next();
			if(!(e instanceof LivingEntity))
				continue;
			
			LivingEntity target = (LivingEntity)e;
			if(!Damage.canHit(target, caster))
				continue;
			try
			{
				fplayer.playFirework(target.getWorld(), target.getLocation(), fe);
			}
			catch(Exception exc)
			{
			}
			Vector vec = Utils.getTargetVector(caster.getLocation(), target.getLocation());
			target.setVelocity(vec.setY(0.1D).multiply(3));
			target.addPotionEffect(blind);
		}
		caster.setVelocity(caster.getLocation().getDirection().multiply(6));
		return true;
	}
	
	public String getName()
	{
		return "Clash";
	}
}
