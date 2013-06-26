package me.frodenkvist.armoreditor;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.inventory.ItemStack;

public class AEHandler
{
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
	
	private static List<AEPlayer> aePlayers = new ArrayList<AEPlayer>();
	private static double deathRemoveChance;
	
	public static void load(ArmorEditor plugin)
	{
		deathRemoveChance = plugin.getConfig().getDouble("DeathRemoveChance");
	}
	
	public static double getDeathRemoveChance()
	{
		return deathRemoveChance;
	}
	
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
