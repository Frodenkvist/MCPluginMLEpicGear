package me.frodenkvist.armoreditor;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class EpicWeapon extends EpicGear
{
	private List<String> ench = new ArrayList<String>();
	private String material = new String();
	private Skill skill;

	@Override
	public boolean load(ConfigurationSection cs, String material, Color color)
	{
		if((this.material = material) == null)
			return false;
		if(!material.equalsIgnoreCase("bow"))
		{
			if((this.type = cs.getString(material + "s" + "." + color.asRGB() + ".Type")) == null)
				return false;
		}
		if((cost = cs.getInt(material + "s" + "." + color.asRGB() + ".Cost")) == -1)
			return false;
		if((lore = cs.getStringList(material + "s" + "." + color.asRGB() + ".Lore")) == null || lore.size() <= 0)
			return false;
		if((code = lore.get(lore.size()-1)) == null)
			return false;
		if((displayName = cs.getString(material + "s" + "." + color.asRGB() + ".DisplayName")) == null)
			return false;
		this.name = cs.getString(material + "s" + "." + color.asRGB() + ".Name");
		List<String> temp = cs.getStringList(material + "s" + "." + color.asRGB() + ".PotionDrinkEffects");
		for(String s : temp)
		{
			String[] split = s.split(",");
			PotionEffect dpe = new PotionEffect(PotionEffectType.getByName(split[0].toUpperCase().replace(" ", "_")),Integer.valueOf(split[2])*20,1);
			drinkEffect.add(dpe);
			drinkEffectsChance.add(Double.valueOf(split[1]));
		}
		temp = cs.getStringList(material + "s" + "." + color.asRGB() + ".PotionSplashEffects");
		for(String s : temp)
		{
			String[] split = s.split(",");
			PotionEffect dpe = new PotionEffect(PotionEffectType.getByName(split[0].toUpperCase().replace(" ", "_")),Integer.valueOf(split[2])*20,1);
			splashEffect.add(dpe);
			splashEffectsChance.add(Double.valueOf(split[1]));
		}
		ench = cs.getStringList(material + "s" + "." + color.asRGB() + ".Enchantments");
		durability = cs.getDouble(material + "s" + "." + color.asRGB() + ".Durability");
		info = cs.getStringList(material + "s" + "." + color.asRGB() + ".Info");
		repairCost = cs.getInt(material + "s" + "." + color.asRGB() + ".RepairCost");
		skill = getSkill(cs.getString(material + "s" + "." + color.asRGB() + ".Skill"));
		
		return true;
	}

	@Override
	public ItemStack getItem()
	{
		ItemStack is = null;
		if(material.equalsIgnoreCase("sword"))
		{
			if(type.equalsIgnoreCase("wood"))
			{
				is = new ItemStack(Material.WOOD_SWORD);
			}
			else if(type.equalsIgnoreCase("stone"))
			{
				is = new ItemStack(Material.STONE_SWORD);
			}
			else if(type.equalsIgnoreCase("iron"))
			{
				is = new ItemStack(Material.IRON_SWORD);
			}
			else if(type.equalsIgnoreCase("gold"))
			{
				is = new ItemStack(Material.GOLD_SWORD);
			}
			else if(type.equalsIgnoreCase("diamond"))
			{
				is = new ItemStack(Material.DIAMOND_SWORD);
			}
		}
		else if(material.equalsIgnoreCase("axe"))
		{
			if(type.equalsIgnoreCase("wood"))
			{
				is = new ItemStack(Material.WOOD_AXE);
			}
			else if(type.equalsIgnoreCase("stone"))
			{
				is = new ItemStack(Material.STONE_AXE);
			}
			else if(type.equalsIgnoreCase("iron"))
			{
				is = new ItemStack(Material.IRON_AXE);
			}
			else if(type.equalsIgnoreCase("gold"))
			{
				is = new ItemStack(Material.GOLD_AXE);
			}
			else if(type.equalsIgnoreCase("diamond"))
			{
				is = new ItemStack(Material.DIAMOND_AXE);
			}
		}
		else if(material.equalsIgnoreCase("bow"))
		{
			is = new ItemStack(Material.BOW);
		}
		Namer.setName(is, name);
		Namer.setLore(is, lore);
		if(durability > 0)
			is.addUnsafeEnchantment(Enchantment.DURABILITY, (int)durability);
		for(String s : ench)
		{
			String[] split = s.split(",");
			if(split.length != 2)
				continue;
			if(!material.equalsIgnoreCase("bow"))
			{
				if(split[0].equalsIgnoreCase("sharpness"))
				{
					is.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, Integer.valueOf(split[1]));
				}
				else if(split[0].equalsIgnoreCase("ARTHROPODS"))
				{
					is.addUnsafeEnchantment(Enchantment.DAMAGE_ARTHROPODS, Integer.valueOf(split[1]));
				}
				else if(split[0].equalsIgnoreCase("undead"))
				{
					is.addUnsafeEnchantment(Enchantment.DAMAGE_UNDEAD, Integer.valueOf(split[1]));
				}
				else if(split[0].equalsIgnoreCase("fire"))
				{
					is.addUnsafeEnchantment(Enchantment.FIRE_ASPECT, Integer.valueOf(split[1]));
				}
				else if(split[0].equalsIgnoreCase("loot"))
				{
					is.addUnsafeEnchantment(Enchantment.LOOT_BONUS_MOBS, Integer.valueOf(split[1]));
				}
				else if(split[0].equalsIgnoreCase("knockback"))
				{
					is.addUnsafeEnchantment(Enchantment.KNOCKBACK, Integer.valueOf(split[1]));
				}
			}
			else
			{
				if(split[0].equalsIgnoreCase("damage"))
				{
					is.addUnsafeEnchantment(Enchantment.ARROW_DAMAGE, Integer.valueOf(split[1]));
				}
				else if(split[0].equalsIgnoreCase("fire"))
				{
					is.addUnsafeEnchantment(Enchantment.ARROW_FIRE, Integer.valueOf(split[1]));
				}
				else if(split[0].equalsIgnoreCase("infinite"))
				{
					is.addUnsafeEnchantment(Enchantment.ARROW_INFINITE, Integer.valueOf(split[1]));
				}
				else if(split[0].equalsIgnoreCase("knockback"))
				{
					is.addUnsafeEnchantment(Enchantment.ARROW_KNOCKBACK, Integer.valueOf(split[1]));
				}
			}
		}
		return is;
	}
	
	public boolean useSkill(AEPlayer player)
	{
		if(skill == null)
			return false;
		if(player.getKillCounter() < skill.getCost())
			return false;
		if(!skill.run(player.getPlayer()))
			return false;
		player.setKillCounter(player.getKillCounter() - skill.getCost());
		return true;
	}
	
	public String getSkillName()
	{
		return skill.getName();
	}
	
	private Skill getSkill(String name)
	{
		String[] split = name.split(",");
		if(split[0].equalsIgnoreCase("firestorm"))
		{
			return new SkillFireStorm(Integer.valueOf(split[1]));
		}
		else if(split[0].equalsIgnoreCase("healingburst"))
		{
			return new SkillHealingBurst(Integer.valueOf(split[1]));
		}
		else if(split[0].equalsIgnoreCase("lightningstorm"))
		{
			return new SkillLightningStorm(Integer.valueOf(split[1]));
		}
		else if(split[0].equalsIgnoreCase("witherstorm"))
		{
			return new SkillWitherStorm(Integer.valueOf(split[1]));
		}
		else if(split[0].equalsIgnoreCase("icestorm"))
		{
			return new SkillIceStorm(Integer.valueOf(split[1]));
		}
		else if(split[0].equalsIgnoreCase("forcepull"))
		{
			return new SkillForcePull(Integer.valueOf(split[1]));
		}
		else if(split[0].equalsIgnoreCase("clash"))
		{
			return new SkillClash(Integer.valueOf(split[1]));
		}
		else if(split[0].equalsIgnoreCase("execution"))
		{
			return new SkillExecution(Integer.valueOf(split[1]));
		}
		else
		{
			return null;
		}
	}
}
