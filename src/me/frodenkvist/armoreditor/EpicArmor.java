package me.frodenkvist.armoreditor;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class EpicArmor extends EpicGear
{
	private  boolean canEnchant = false;
	private  int pveLowestDamage = 0;
	private  int pveHighestDamage = 0;
	private  int pvpLowestDamage = 0;
	private  int pvpHighestDamage = 0;
	private  double pveDamageBuff = 0;
	private  double pvpDamageBuff = 0;
	private Color color;
	private List<String> ench = new ArrayList<String>();
	
	public EpicArmor()
	{
	}
	
	@Override
	public boolean load(ConfigurationSection cs, String type, Color color)
	{
		if((this.type = type) == null)
			return false;
		if((this.color = color) == null)
			return false;
		if((cost = cs.getInt(type + ".Cost")) == -1)
			return false;
		if((lore = cs.getStringList(type + ".Lore")) == null || lore.size() <= 0)
			return false;
		if((code = lore.get(lore.size()-1)) == null)
			return false;
		if((displayName = cs.getString(type + ".DisplayName")) == null)
			return false;
		this.name = cs.getString(type + ".Name");
		List<String> temp = cs.getStringList(type + ".PotionDrinkEffects");
		for(String s : temp)
		{
			String[] split = s.split(",");
			PotionEffect dpe = new PotionEffect(PotionEffectType.getByName(split[0].toUpperCase().replace(" ", "_")),Integer.valueOf(split[2])*20,1);
			drinkEffect.add(dpe);
			drinkEffectsChance.add(Double.valueOf(split[1]));
		}
		temp = cs.getStringList(type + ".PotionSplashEffects");
		for(String s : temp)
		{
			String[] split = s.split(",");
			PotionEffect dpe = new PotionEffect(PotionEffectType.getByName(split[0].toUpperCase().replace(" ", "_")),Integer.valueOf(split[2])*20,1);
			splashEffect.add(dpe);
			splashEffectsChance.add(Double.valueOf(split[1]));
		}
		durability = cs.getDouble(type + ".Durability");
		canEnchant = cs.getBoolean(type + ".CanEnchant");
		pveLowestDamage = cs.getInt(type + ".PveLowestDamage");
		pveHighestDamage = cs.getInt(type + ".PveHighestDamage");
		pvpLowestDamage = cs.getInt(type + ".PvpLowestDamage");
		pvpHighestDamage = cs.getInt(type + ".PvpHighestDamage");
		info = cs.getStringList(type + ".Info");
		ench = cs.getStringList(type + ".Enchantments");
		repairCost = cs.getInt(type + ".RepairCost");
		
		return true;
	}
	
	@Override
	public ItemStack getItem()
	{
		String material = ("leather_" + type).toUpperCase();
		ItemStack is = new ItemStack(Material.getMaterial(material));
		Namer.setName(is, name);
		Namer.setLore(is, lore);
		LeatherArmorMeta lam = (LeatherArmorMeta)is.getItemMeta();
		lam.setColor(color);
		is.setItemMeta(lam);
		for(String s : ench)
		{
			String[] split = s.split(",");
			if(split.length != 2)
				continue;
			if(split[0].equalsIgnoreCase("protection"))
			{
				is.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, Integer.valueOf(split[1]));
			}
			else if(split[0].equalsIgnoreCase("fireprotection"))
			{
				is.addUnsafeEnchantment(Enchantment.PROTECTION_FIRE, Integer.valueOf(split[1]));
			}
			else if(split[0].equalsIgnoreCase("featherfalling"))
			{
				if(!is.getType().equals(Material.LEATHER_BOOTS))
					continue;
				is.addUnsafeEnchantment(Enchantment.PROTECTION_FALL, Integer.valueOf(split[1]));
			}
			else if(split[0].equalsIgnoreCase("blastprotection"))
			{
				is.addUnsafeEnchantment(Enchantment.PROTECTION_EXPLOSIONS, Integer.valueOf(split[1]));
			}
			else if(split[0].equalsIgnoreCase("projectileprotection"))
			{
				is.addUnsafeEnchantment(Enchantment.PROTECTION_PROJECTILE, Integer.valueOf(split[1]));
			}
			else if(split[0].equalsIgnoreCase("respiration"))
			{
				if(!is.getType().equals(Material.LEATHER_HELMET))
					continue;
				is.addUnsafeEnchantment(Enchantment.OXYGEN, Integer.valueOf(split[1]));
			}
			else if(split[0].equalsIgnoreCase("aquaaffinity"))
			{
				if(!is.getType().equals(Material.LEATHER_HELMET))
					continue;
				is.addUnsafeEnchantment(Enchantment.WATER_WORKER, Integer.valueOf(split[1]));
			}
			if(split[0].equalsIgnoreCase("thorns"))
			{
				is.addUnsafeEnchantment(Enchantment.THORNS, Integer.valueOf(split[1]));
			}
			if(split[0].equalsIgnoreCase("unbreaking"))
			{
				is.addUnsafeEnchantment(Enchantment.DURABILITY, Integer.valueOf(split[1]));
			}
		}
		return is;
	}
	
	public int getPveLowestDamage()
	{
		return pveLowestDamage;
	}

	public int getPvpLowestDamage()
	{
		return pvpLowestDamage;
	}
	
	public int getPveHighestDamage()
	{
		return pveHighestDamage;
	}
	
	public int getPvpHighestDamage()
	{
		return pvpHighestDamage;
	}
	
	public double getPveDamageBuff()
	{
		return pveDamageBuff;
	}
	
	public double getPvpDamageBuff()
	{
		return pvpDamageBuff;
	}
	
	public double getDurability()
	{
		return durability;
	}
	
	public boolean canEnchant()
	{
		return canEnchant;
	}
	
	public Color getColor()
	{
		return color;
	}
}
