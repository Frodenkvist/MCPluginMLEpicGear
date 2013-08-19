package me.frodenkvist.armoreditor;

import me.ThaH3lper.com.Entitys.Mob;
import me.ThaH3lper.com.Entitys.MobsHandler;

import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import PvpBalance.Damage;
import PvpBalance.PvpHandler;

public class SkillForcePull extends Skill
{
	public SkillForcePull(int cost)
	{
		super(cost);
	}
	
	public boolean run(Player caster)
	{
		Entity e = Utils.getTarget(caster, 50);
		if(e == null)
			return false;
		if(!(e instanceof LivingEntity))
			return false;
		LivingEntity target = (LivingEntity)e;
		if(!Damage.canHit(target, caster))
			return false;
		Mob mob = MobsHandler.getMob(target);
		if(mob != null)
		{
			if(mob.isEpicImmune())
				return false;
		}
		final FireworkEffectPlayer fplayer = new FireworkEffectPlayer();
		FireworkEffect fe = FireworkEffect.builder().with(Type.BURST).withColor(Color.BLUE).withColor(Color.AQUA).flicker(true).build();
		try
		{
			fplayer.playFirework(target.getWorld(), target.getLocation(), fe);
		}
		catch(Exception exc)
		{
			return false;
		}
		Vector vec = Utils.getTargetVector(target.getLocation(), caster.getLocation());
		target.setVelocity(vec.setY(0.1D));
		if(target instanceof Player)
		{
			Player pTarget = (Player)target;
			PvpHandler.getPvpPlayer(pTarget).uncheckedDamage(PvpHandler.getPvpPlayer(pTarget).gethealth()/2);
		}
		else
		{
			target.damage(target.getHealth()/2);
		}
		return true;
	}
	
	public String getName()
	{
		return "Force Pull";
	}
}
