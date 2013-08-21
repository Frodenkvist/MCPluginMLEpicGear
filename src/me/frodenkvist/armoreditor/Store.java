package me.frodenkvist.armoreditor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;

import Util.ItemUtils;

public class Store
{
	private static List<EpicGear> inventory = new ArrayList<EpicGear>();
	
	public static boolean addItem(EpicGear item)
	{
		return inventory.add(item);
	}
	
	public static void load(FileConfiguration fc)
	{
		inventory.clear();
		String c = new String();
		for(int i = 0;i < 10; ++i)
		{
			switch(i)
			{
			case 0:
				c = "Black";
				break;
			case 1:
				c = "Red";
				break;
			case 2:
				c = "Green";
				break;
			case 3:
				c = "Blue";
				break;
			case 4:
				c = "White";
				break;
			case 5:
				c = "Purple";
				break;
			case 6:
				c = "Teal";
				break;
			case 7:
				c = "Pink";
				break;
			case 8:
				c = "Orange";
				break;
			case 9:
				c = "Yellow";
				break;
			}
			EpicGear eg = new EpicArmor();
			if(eg.load(fc.getConfigurationSection(c), "Helmet", AEHandler.getColorByName(c)))
				inventory.add(eg);
			eg = new EpicArmor();
			if(eg.load(fc.getConfigurationSection(c), "Chestplate", AEHandler.getColorByName(c)))
				inventory.add(eg);
			eg = new EpicArmor();
			if(eg.load(fc.getConfigurationSection(c), "Leggings", AEHandler.getColorByName(c)))
				inventory.add(eg);
			eg = new EpicArmor();
			if(eg.load(fc.getConfigurationSection(c), "Boots", AEHandler.getColorByName(c)))
				inventory.add(eg);
		}
		for(int i = 0;i < 4; ++i)
		{
			switch(i)
			{
			case 0:
				c = "Sword";
				break;
			case 1:
				c = "Axe";
				break;
			case 2:
				c = "Bow";
				break;
			case 3:
				c = "Hoe";
				break;
			}
			for(int j=1;fc.contains("Weapons." + c + "s" + "." + j);++j)
			{
				EpicGear eg = new EpicWeapon();
				if(eg.load(fc.getConfigurationSection("Weapons"), c, Color.fromRGB(j)))
					inventory.add(eg);
			}
		}
		Collections.sort(inventory, new EpicGearComparator());
		ArmorEditor.plugin.logger.info("" + inventory.size());
	}
	
	public static ItemStack getItem(String displayname)
	{
		for(EpicGear eg : inventory)
		{
			if(eg.getDisplayName().equalsIgnoreCase(displayname))
				return eg.getItem();
		}
		return null;
	}
	
	public static void addNameAndLore(ItemStack is)
	{
		LeatherArmorMeta lam = (LeatherArmorMeta)is.getItemMeta();
		for(EpicGear eg : inventory)
		{
			if(!(eg instanceof EpicArmor))
				continue;
			EpicArmor ea = (EpicArmor)eg;
			if(ea.getColor().equals(lam.getColor()))
			{
				if(is.getType().toString().replace("leather_", "").equalsIgnoreCase(ea.getType()))
				{
					Namer.setName(is, ea.getName());
					Namer.setLore(is, ea.getLore());
					return;
				}
			}
		}
	}
	
	public static boolean isEpicGear(ItemStack is)
	{
		if(is.getItemMeta() == null)
			return false;
		List<String> lore = is.getItemMeta().getLore();
		if(lore == null || lore.isEmpty())
			return false;
		for(EpicGear eg : inventory)
		{
			if(eg.getCode().equalsIgnoreCase(lore.get(lore.size()-1)))
				return true;
		}
		return false;
	}
	
	public static boolean isEpicArmor(ItemStack is)
	{
		if(is.getItemMeta() == null)
			return false;
		List<String> lore = is.getItemMeta().getLore();
		if(lore == null || lore.isEmpty())
			return false;
		for(EpicGear eg : inventory)
		{
			if(!(eg instanceof EpicArmor))
				continue;
			if(eg.getCode().equalsIgnoreCase(lore.get(lore.size()-1)))
				return true;
		}
		return false;
	}
	
	public static boolean isEpicWeapon(ItemStack is)
	{
		if(is.getItemMeta() == null)
			return false;
		List<String> lore = is.getItemMeta().getLore();
		if(lore == null || lore.isEmpty())
			return false;
		for(EpicGear eg : inventory)
		{
			if(!(eg instanceof EpicWeapon))
				continue;
			if(Namer.addChatColor(eg.getCode()).equalsIgnoreCase(Namer.addChatColor(lore.get(lore.size()-1))))
				return true;
		}
		return false;
	}
	
	public static EpicArmor getEpicArmor(ItemStack is)
	{
		if(is.getItemMeta() == null)
			return null;
		List<String> lore = is.getItemMeta().getLore();
		if(lore == null || lore.isEmpty())
			return null;
		for(EpicGear eg : inventory)
		{
			if(!(eg instanceof EpicArmor))
				continue;
			if(Namer.addChatColor(eg.getCode()).equalsIgnoreCase(Namer.addChatColor(lore.get(lore.size()-1))))
				return (EpicArmor)eg;
		}
		return null;
	}
	
	public static EpicGear getEpicGear(ItemStack is)
	{
		if(is.getItemMeta() == null)
			return null;
		List<String> lore = ItemUtils.getLore(is);
		if(lore == null || lore.isEmpty())
			return null;
		for(EpicGear eg : inventory)
		{
			//if(!(eg instanceof EpicArmor))
			if(Namer.addChatColor(eg.getCode()).equalsIgnoreCase(Namer.addChatColor(lore.get(lore.size()-1))))
				return eg;
		}
		return null;
	}
	
	public static EpicWeapon getEpicWeapon(ItemStack is)
	{
		if(is.getItemMeta() == null)
			return null;
		List<String> lore = is.getItemMeta().getLore();
		if(lore == null || lore.isEmpty())
			return null;
		for(EpicGear eg : inventory)
		{
			if(!(eg instanceof EpicWeapon))
				continue;
			if(Namer.addChatColor(eg.getCode()).equalsIgnoreCase(Namer.addChatColor(lore.get(lore.size()-1))))
				return (EpicWeapon)eg;
		}
		return null;
	}
	
	public static EpicGear getEpicGear(String diplayname)
	{
		for(EpicGear eg : inventory)
		{
			if(eg.getDisplayName().equalsIgnoreCase(diplayname))
				return eg;
		}
		return null;
	}
	
	public static void displayStoreList(Player player, int nr)
	{
		int size = 0;
		List<EpicGear> cencList = new ArrayList<EpicGear>();
		if(!player.hasPermission("epicgear.admin"))
		{
			Iterator<EpicGear> itr = inventory.iterator();
			while(itr.hasNext())
			{
				EpicGear temp = itr.next();
				if(!temp.isHidden())
					cencList.add(temp);
			}
			
			size = cencList.size();
		}
		else
			size = inventory.size();
		if(size <= 0)
		{
			player.sendMessage(ChatColor.RED + "The Is Nothing In The Store Right Now");
			return;
		}
		int pages = size/8;
		if((size%8) != 0)
			++pages;
		if(nr > pages)
			nr = pages;
		player.sendMessage(ChatColor.GOLD + "----" + ChatColor.YELLOW + " EpicGear Store " + ChatColor.GOLD + "----(pg. " + nr + "/" + pages + ")");
		player.sendMessage(ChatColor.GOLD + "| " + ChatColor.AQUA + " Name " + ChatColor.GOLD + " | " + ChatColor.DARK_AQUA + " Buy Name " + ChatColor.GOLD + " | " + ChatColor.GRAY + " Cost " + ChatColor.GOLD + " |");
		for(int i = ((8*nr)-8);i < (nr*8);++i)
		{
			if(player.hasPermission("epicgear.admin"))
			{
				EpicGear eg = inventory.get(i);
				player.sendMessage(ChatColor.GOLD + "- " + ChatColor.AQUA + Namer.removeTag(eg.getName()) + " " + ChatColor.DARK_AQUA + eg.getDisplayName() + " " + ChatColor.GRAY + eg.getCost());
				if((i+1) >= inventory.size())
					break;
			}
			else
			{
				EpicGear eg = cencList.get(i);
				player.sendMessage(ChatColor.GOLD + "- " + ChatColor.AQUA + Namer.removeTag(eg.getName()) + " " + ChatColor.DARK_AQUA + eg.getDisplayName() + " " + ChatColor.GRAY + eg.getCost());
				if((i+1) >= cencList.size())
					break;
			}
		}
	}
	
	public static double getDurability(ItemStack is)
	{
		Iterator<String> itr = Namer.getLore(is).iterator();
		while(itr.hasNext())
		{
			String s = itr.next();
			if(!s.contains("dur:"))
			{
				continue;
			}
			return Double.valueOf(s.split(":")[1]);
		}
		return -1;
	}
	
	public static void setDurability(ItemStack is, double durability)
	{
		int counter = 0;
		Iterator<String> itr = Namer.getLore(is).iterator();
		while(itr.hasNext())
		{
			String s = itr.next();
			if(!s.contains("dur:"))
			{
				++counter;
				continue;
			}
			/*if(durability <= 0)
			{
				is = null;
				break;
			}*/
			Namer.setLore(is, "&kdur:" + durability, counter);
			//Bukkit.broadcastMessage("" + durability + " " + getEpicGear(is).getDurability());
			double percent = durability / getEpicGear(is).getDurability();
			//Bukkit.broadcastMessage("" + percent);
			short realDur = (short) (is.getType().getMaxDurability() * (1 - percent));
			if(realDur >= is.getType().getMaxDurability())
				realDur = (short) (is.getType().getMaxDurability() - 1);
			is.setDurability(realDur);
			break;
		}
	}
	
	public static void setDecay(ItemStack is, int decay, int dayNum)
	{
		if(Namer.getLore(is) == null)
			return;
		int counter = 0;
		Iterator<String> itr = Namer.getLore(is).iterator();
		while(itr.hasNext())
		{
			String s = itr.next();
			if(!s.contains("Decay "))
			{
				++counter;
				continue;
			}
			/*if(durability <= 0)
			{
				is = null;
				break;
			}*/
			Namer.setLore(is, "Decay " + decay + "&k:" + dayNum, counter);
			break;
		}
	}
	
	public static String getDecay(ItemStack is)
	{
		if(Namer.getLore(is) == null)
			return null;
		Iterator<String> itr = Namer.getLore(is).iterator();
		while(itr.hasNext())
		{
			String s = itr.next();
			if(!s.contains("Decay "))
			{
				continue;
			}
			String re = s.replace("Decay ", "").replace("§k", "");
			return re;
		}
		return null;
	}
	
	public static void changeLore(ItemStack is)
	{
		if(is == null)
			return;
		if(is.getItemMeta() == null)
			return;
		List<String> lore = ItemUtils.getLore(is);
		if(lore == null)
			return;
		if(lore.size() <= 0)
			return;
		String s = lore.get(lore.size()-1);
		if(!s.contains("Polished"))
			return;
		lore.remove(lore.size()-1);
		List<String> newLore = new ArrayList<String>();
		newLore.add(s);
		Iterator<String> itr = lore.iterator();
		while(itr.hasNext())
		{
			newLore.add(itr.next());
		}
		ItemUtils.setLore(is, newLore);
	}
}
