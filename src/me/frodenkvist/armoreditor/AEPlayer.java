package me.frodenkvist.armoreditor;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class AEPlayer
{
	private Player player;
	private boolean creating;
	private boolean createArmor1;
	//private int killCounter = 0;
	//private Arrow a;
	
	public AEPlayer(Player p)
	{
		player = p;
	}
	
	public Player getPlayer()
	{
		return player;
	}
	
	public void sendMessage(String message)
	{
		player.sendMessage(message);
	}
	
	public void createArmor1()
	{
		creating = true;
		createArmor1 = true;
		player.sendMessage(ChatColor.RED + "RED");
		player.sendMessage(ChatColor.BLACK + "BLACK");
		player.sendMessage(ChatColor.GREEN + "GREEN");
		player.sendMessage(ChatColor.BLUE + "BLUE");
		player.sendMessage(ChatColor.WHITE + "WHITE");
		player.sendMessage(ChatColor.DARK_PURPLE + "PURPLE");
		player.sendMessage(ChatColor.AQUA + "TEAL");
		player.sendMessage(ChatColor.LIGHT_PURPLE + "PINK");
		player.sendMessage(ChatColor.GOLD + "ORANGE");
		player.sendMessage(ChatColor.YELLOW + "YELLOW");
		player.sendMessage(ChatColor.GREEN + "Please Type A Color To Chose It.");
	}
	
	public boolean isCreating()
	{
		return creating;
	}
	
	public boolean isCreatingArmor1()
	{
		return createArmor1;
	}
	
	/*public void setKillCounter(int value)
	{
		killCounter = value;
	}
	
	public int getKillCounter()
	{
		return killCounter;
	}
	
	public void setSpeciallArrow(Arrow a)
	{
		this.a = a;
	}
	
	public Arrow getSpeciallArrow()
	{
		return a;
	}*/
}
