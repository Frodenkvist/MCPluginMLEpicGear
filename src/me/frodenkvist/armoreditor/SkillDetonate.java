package me.frodenkvist.armoreditor;

import java.util.Iterator;
import java.util.List;

import me.ThaH3lper.com.Entitys.Mob;
import me.ThaH3lper.com.Entitys.MobsHandler;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import PvpBalance.Damage;
import PvpBalance.PvpHandler;

public class SkillDetonate extends Skill
{
	public SkillDetonate(int cost)
	{
		super(cost);
	}

	@Override
	public boolean run(final Player caster)
	{
		Bukkit.getScheduler().scheduleSyncDelayedTask(ArmorEditor.plugin, new Runnable()
		{
			@Override
			public void run()
			{
				FireworkEffectPlayer fplayer = new FireworkEffectPlayer();
				FireworkEffect fe = FireworkEffect.builder().withColor(Color.RED).with(Type.BALL_LARGE).withColor(Color.YELLOW).withColor(Color.ORANGE).build();
				try
				{
					caster.getWorld().createExplosion(caster.getLocation(), 6);
					fplayer.playFirework(caster.getWorld(), caster.getLocation(), fe);
					fplayer.playFirework(caster.getWorld(), caster.getLocation(), fe);
				}
				catch(Exception e)
				{
				}
				List<Entity> entities = caster.getNearbyEntities(6, 6, 6);
				Iterator<Entity> itr = entities.iterator();
				while(itr.hasNext())
				{
					Entity en = itr.next();
					if(!(en instanceof LivingEntity))
						continue;
					LivingEntity le = (LivingEntity)en;
					if(!Damage.canHit(en, caster))
						continue;
					if(le instanceof Player)
					{
						PvpHandler.getPvpPlayer((Player)le).uncheckedDamage(PvpHandler.getPvpPlayer((Player)le).gethealth()/2);
						le.damage(0);
					}
					else
					{
						Mob mob = MobsHandler.getMob(le);
            			if(mob != null)
            			{
            				if(mob.isEpicImmune())
            					continue;
            			}
						le.setHealth(le.getHealth()/2);
						le.damage(0);
					}
				}
			}
		},20L*3);
		
		return true;
	}

	@Override
	public String getName()
	{
		return "Detonate";
	}
}
