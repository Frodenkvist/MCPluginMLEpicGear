package me.frodenkvist.armoreditor;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

public abstract class EpicGear
{
	protected String name = new String();
	protected List<String> lore = new ArrayList<String>();
	protected String displayName = new String();
	protected int cost = -1;
	protected List<PotionEffect> drinkEffect = new ArrayList<PotionEffect>();
	protected List<Double> drinkEffectsChance = new ArrayList<Double>();
	protected List<PotionEffect> splashEffect = new ArrayList<PotionEffect>();
	protected List<Double> splashEffectsChance = new ArrayList<Double>();
	protected String code = new String();
	protected String type = new String();
	protected double durability = -1D;
	protected List<String> info = new ArrayList<String>();
	protected int repairCost;
	protected boolean dontDropOnDeath;
	protected int decay = -1;
	protected boolean hidden;
	
	public abstract boolean load(ConfigurationSection cs, String type, Color color);
	
	public abstract ItemStack getItem();
	
	public void addDrinkPotionEffect(LivingEntity player)
	{
		for(int i = 0;i < drinkEffect.size();++i)
		{
			if(Math.random() <= drinkEffectsChance.get(i))
			{
				player.addPotionEffect(drinkEffect.get(i));
			}
		}
	}
	
	public void addSplashPotionEffect(LivingEntity player)
	{
		for(int i = 0;i < splashEffect.size();++i)
		{
			if(Math.random() <= splashEffectsChance.get(i))
			{
				player.addPotionEffect(splashEffect.get(i));
			}
		}
	}
	
	public void setName(String name)
	{
		this.name = name;
	}
	
	public void setLore(List<String> lore)
	{
		this.lore = lore;
		code = lore.get(lore.size()-1);
	}
	
	public void setDisplayName(String disName)
	{
		displayName = disName;
	}
	
	public void setCost(int cost)
	{
		this.cost = cost;
	}
	
	public String getDisplayName()
	{
		return displayName;
	}
	
	public String getName()
	{
		return name;
	}
	
	public List<String> getLore()
	{
		return lore;
	}
	
	public String getType()
	{
		return type;
	}
	
	public String getCode()
	{
		return code;
	}
	
	public int getCost()
	{
		return cost;
	}
	
	public int getRepairCost()
	{
		return repairCost;
	}
	
	public double getDurability()
	{
		return durability;
	}
	
	public void setDontDropOnDeath(boolean value)
	{
		dontDropOnDeath = value;
	}
	
	public boolean getDontDropOnDeath()
	{
		return dontDropOnDeath;
	}
	
	public void displayInfo(Player player)
	{
		if(info == null || info.isEmpty())
			player.sendMessage(ChatColor.RED + "No Info Found About This Item");
		for(String s : info)
		{
			player.sendMessage(Namer.addChatColor(s));
		}
	}
	
	public void setHidden(boolean value)
	{
		hidden = value;
	}
	
	public boolean isHidden()
	{
		return hidden;
	}
}
