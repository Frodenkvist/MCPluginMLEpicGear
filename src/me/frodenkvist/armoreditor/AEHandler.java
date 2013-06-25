package me.frodenkvist.armoreditor;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.inventory.ItemStack;

public class AEHandler
{
	public static List<AEPlayer> aePlayers = new ArrayList<AEPlayer>();
	public final static int BLACK = 1644825;
	public final static int RED = 13388876;
	public final static int GREEN = 8375321;
	public final static int BLUE = 3368652;
	public final static int WHITE = 16777215;
	public final static int PURPLE = 13421772;
	public final static int TEAL = 52394;
	public final static int PINK = 14357690;
	public final static int ORANGE = 16737843;
	public final static int YELLOW = 16776960;
	
	public static void addPlayer(AEPlayer p)
	{
		aePlayers.add(p);
	}
	
	public static AEPlayer getPlayer(String name)
	{
		for(AEPlayer p : aePlayers)
		{
			if(p.getPlayer().getName().equalsIgnoreCase(name))
				return p;
		}
		return null;
	}
	
	public static boolean removePlayer(String name)
	{
		for(AEPlayer p : aePlayers)
		{
			if(p.getPlayer().getName().equalsIgnoreCase(name))
			{
				aePlayers.remove(p);
				return true;
			}
				
		}
		return false;
	}
	
	protected static void clearPlayers()
	{
		aePlayers.clear();
	}
	
	/*
	public static int getRepairCost(String name)
	{
		int cost;
		cost = EpicHelmet.getRepairCost(name);
		if(cost != -1)
		{
			return cost;
		}
		cost = EpicChestplate.getRepairCost(name);
		if(cost != -1)
		{
			return cost;
		}
		cost = EpicLeggings.getRepairCost(name);
		if(cost != -1)
		{
			return cost;
		}
		cost = EpicBoots.getRepairCost(name);
		if(cost != -1)
		{
			return cost;
		}
		return -1;
	}
	
	public static int getRepairCost(ItemStack is)
	{
		String name = getDisplayName(is);
		int cost;
		cost = EpicHelmet.getRepairCost(name);
		if(cost != -1)
		{
			return cost;
		}
		cost = EpicChestplate.getRepairCost(name);
		if(cost != -1)
		{
			return cost;
		}
		cost = EpicLeggings.getRepairCost(name);
		if(cost != -1)
		{
			return cost;
		}
		cost = EpicBoots.getRepairCost(name);
		if(cost != -1)
		{
			return cost;
		}
		int id = is.getTypeId();
		switch(id)
		{
		case 268:
		case 272:
		case 267:
		case 283:
		case 276:
			int i = 1;
			while(ArmorEditor.plugin.getConfig().contains("Weapons.Swords." + i))
			{
				++i;
			}
			--i;
			for(int j = 1;j<=i;++j)
			{
				if(!is.getType().toString().toLowerCase().split("_")[0].equalsIgnoreCase(ArmorEditor.plugin.getConfig().getString("Weapons.Swords." + j + ".Type")))
					continue;
				List<String> thing = ArmorEditor.plugin.getConfig().getStringList("Weapons.Swords." + j + ".Lore");
				String n = thing.get(thing.size()-1);
				n = Namer.addChatColor(n);
				String[] things = Namer.getLoreAsArray(is);
				if(things.length == 0)
					return -1;
				String itemInHandn = things[things.length-1];
					
				if(itemInHandn.equalsIgnoreCase(n))
				{
					return ArmorEditor.plugin.getConfig().getInt("Weapons.Swords." + j + ".RepairCost");
				}
			}
			break;
		case 271:
		case 275:
		case 258:
		case 286:
		case 279:
			int i2 = 1;
			while(ArmorEditor.plugin.getConfig().contains("Weapons.Swords." + i2))
			{
				++i2;
			}
			--i2;
			for(int j = 1;j<=i2;++j)
			{
				if(!is.getType().toString().toLowerCase().split("_")[0].equalsIgnoreCase(ArmorEditor.plugin.getConfig().getString("Weapons.Axes." + j + ".Type")))
					continue;
				List<String> thing = ArmorEditor.plugin.getConfig().getStringList("Weapons.Axes." + j + ".Lore");
				String n = thing.get(thing.size()-1);
				n = Namer.addChatColor(n);
				String[] things = Namer.getLoreAsArray(is);
				if(things.length == 0)
					return -1;
				String itemInHandn = things[things.length-1];
				
				if(itemInHandn.equalsIgnoreCase(n))
				{
					return ArmorEditor.plugin.getConfig().getInt("Weapons.Swords." + j + ".RepairCost");
				}
			}
			break;
		case 261:
			int i3 = 1;
			while(true)
			{
				if(!ArmorEditor.plugin.getConfig().contains("Weapons.Bows." + String.valueOf(i3)))
				{
					--i3;
					break;
				}
				++i3;
			}
			for(int j=1;j<=i3;++j)
			{
				List<String> thing = ArmorEditor.plugin.getConfig().getStringList("Weapons.Bows." + j + ".Lore");
				String n = thing.get(thing.size()-1);
				n = Namer.addChatColor(n);
				String[] things = Namer.getLoreAsArray(is);
				if(things.length == 0)
					return -1;
				String itemInHandn = things[things.length-1];
				
				if(itemInHandn.equalsIgnoreCase(n))
				{
					return ArmorEditor.plugin.getConfig().getInt("Weapons.Swords." + j + ".RepairCost");
				}
			}
			break;
		}
		return -1;
	}*/
	
	public static ItemStack getToken()
	{
		ItemStack is = new ItemStack(ArmorEditor.plugin.getConfig().getInt("MoneyItem.ID"));
		is = Namer.setName(is, ArmorEditor.plugin.getConfig().getString("MoneyItem.Name"));
		is = Namer.setLore(is, ArmorEditor.plugin.getConfig().getStringList("MoneyItem.Lore"));
		return is;
	}
	
	public static ItemStack getRepairToken()
	{
		ItemStack is = new ItemStack(ArmorEditor.plugin.getConfig().getInt("MoneyItem.ID"));
	    Namer.setName(is, ChatColor.GOLD + "Armor Repair Token");
	    List<String> lore = new ArrayList<String>(1);
	    lore.add(ChatColor.GREEN + "Hold the item you want to repair and say" + ChatColor.AQUA + " /ae repair " + ChatColor.GREEN + "to repair");
	    Namer.setLore(is, lore);
	    return is;
	}
	
	public static boolean isRepairToken(ItemStack is)
	{
		if (is == null)
			return false;
		if (Namer.getName(is) == null)
			return false;
		if (Namer.getName(is).equalsIgnoreCase(ChatColor.GOLD + "Armor Repair Token"))
		{
			if ((Namer.getLore(is).size() == 1) && (((String)Namer.getLore(is).get(0)).equalsIgnoreCase(ChatColor.GREEN + "Hold the item you want to repair and say" + ChatColor.AQUA + " /ae repair " + 
					ChatColor.GREEN + "to repair")))
			{
				return true;
			}
		}
		return false;
	}
	
	public static Color getColorByName(String name)
	{
		if(name.equalsIgnoreCase("Black"))
			return Color.fromRGB(BLACK);
		else if(name.equalsIgnoreCase("Red"))
			return Color.fromRGB(RED);
		else if(name.equalsIgnoreCase("Green"))
			return Color.fromRGB(GREEN);
		else if(name.equalsIgnoreCase("Blue"))
			return Color.fromRGB(BLUE);
		else if(name.equalsIgnoreCase("White"))
			return Color.fromRGB(WHITE);
		else if(name.equalsIgnoreCase("Purple"))
			return Color.fromRGB(PURPLE);
		else if(name.equalsIgnoreCase("Teal"))
			return Color.fromRGB(TEAL);
		else if(name.equalsIgnoreCase("Pink"))
			return Color.fromRGB(PINK);
		else if(name.equalsIgnoreCase("Orange"))
			return Color.fromRGB(ORANGE);
		else if(name.equalsIgnoreCase("Yellow"))
			return Color.fromRGB(YELLOW);
		else
			return null;
	}
}
