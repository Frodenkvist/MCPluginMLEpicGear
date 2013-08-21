package me.frodenkvist.armoreditor;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Skeleton;
import org.bukkit.entity.Skeleton.SkeletonType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import PvpBalance.PvpHandler;

public class SkillSummonQuaxis extends Skill
{
	public SkillSummonQuaxis(int cost)
	{
		super(cost);
	}

	@Override
	public boolean run(Player caster)
	{
		Entity en = Utils.getTarget(caster, 25);
		if(en == null)
			return false;
		if(!(en instanceof Player))
			return false;
		final Player target = (Player)en;
		FireworkEffectPlayer fplayer = new FireworkEffectPlayer();
		FireworkEffect fe = FireworkEffect.builder().withColor(Color.AQUA).withColor(Color.BLUE).withColor(Color.NAVY).with(Type.BALL_LARGE).build();
		final Skeleton quaxis = (Skeleton)target.getWorld().spawnEntity(target.getLocation(), EntityType.SKELETON);
		quaxis.setSkeletonType(SkeletonType.WITHER);
		quaxis.setCustomName(ChatColor.DARK_PURPLE + "Quaxis The Ruthless");
		quaxis.setCustomNameVisible(true);
		quaxis.setMaxHealth(99999);
		quaxis.setHealth(quaxis.getMaxHealth());
		quaxis.getEquipment().setHelmet(new ItemStack(Material.DIAMOND_HELMET));
		quaxis.getEquipment().setHelmetDropChance(0);
		quaxis.getEquipment().setItemInHand(new ItemStack(Material.DIAMOND_HOE));
		quaxis.getEquipment().setItemInHandDropChance(0);
		target.setPassenger(quaxis);
		try
		{
			fplayer.playFirework(target.getWorld(), target.getLocation(), fe);
			fplayer.playFirework(target.getWorld(), target.getLocation(), fe);
		}
		catch(Exception e)
		{
		}
		target.addPotionEffect(new PotionEffect(PotionEffectType.WITHER,20*6,1));
		Bukkit.getScheduler().scheduleSyncDelayedTask(ArmorEditor.plugin, new Runnable()
		{
			@Override
			public void run()
			{
				PvpHandler.getPvpPlayer(target).uncheckedDamage(200);
				Bukkit.getScheduler().scheduleSyncDelayedTask(ArmorEditor.plugin, new Runnable()
				{
					@Override
					public void run()
					{
						PvpHandler.getPvpPlayer(target).uncheckedDamage(200);
						Bukkit.getScheduler().scheduleSyncDelayedTask(ArmorEditor.plugin, new Runnable()
						{
							@Override
							public void run()
							{
								PvpHandler.getPvpPlayer(target).uncheckedDamage(200);
								quaxis.remove();
							}
						},40L);
					}
				},40L);
			}
		},40L);
		
		
		return true;
	}

	@Override
	public String getName()
	{
		return "Summon Quaxis";
	}
	
}
