package me.frodenkvist.armoreditor;

import java.util.List;

import me.ThaH3lper.com.Api.BossDeathEvent;

import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.enchantment.EnchantItemEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;

public class PlayerListener implements Listener
{
	public ArmorEditor plugin;
	private final int BLACK = 1644825;
	private final int RED = 13388876;
	private final int GREEN = 8375321;
	private final int BLUE = 3368652;
	private final int WHITE = 16777215;
	
	private final int PURPLE = 13421772;
	private final int TEAL = 52394;
	private final int PINK = 14357690;
	private final int ORANGE = 16737843;
	private final int YELLOW = 16776960;
	
	private Location loc;
	
	public PlayerListener(ArmorEditor instance)
	{
		plugin = instance;
	}
	
	@EventHandler
	public void onPlayerPickupItemEvent(PlayerPickupItemEvent event)
	{
		ItemStack is = event.getItem().getItemStack();
		if(isLeatherArmor(is) && event.getPlayer().hasPermission("epicgear.admin"))
		{
			if(hasValidColor(is))
				Store.addNameAndLore(is);
		}
		else if(plugin.getConfig().getInt("MoneyItem.ID") != -1 && is.getTypeId() == plugin.getConfig().getInt("MoneyItem.ID"))
		{
			is = Namer.setName(is, plugin.getConfig().getString("MoneyItem.Name"));
			is = Namer.setLore(is, plugin.getConfig().getStringList("MoneyItem.Lore"));
		}
		
	}
	
	@EventHandler
	public void onCraftItemEvent(CraftItemEvent event)
	{
		Player player = (Player) event.getWhoClicked();
		if(player.hasPermission("epicgear.craft") || player.hasPermission("epicgear.admin"))
			return;
		Inventory inv = event.getInventory();
		boolean armor = false;
		boolean dye = false;
		for(ItemStack is : inv.getContents())
		{
			if(is.getTypeId() == 299)
				armor = true;
			if(is.getTypeId() == 298)
				armor = true;
			if(is.getTypeId() == 300)
				armor = true;
			if(is.getTypeId() == 301)
				armor = true;
			if(is.getTypeId() == 351)
				dye = true;
			if(armor && dye)
				event.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onEnchantItemEvent(EnchantItemEvent event)
	{
		//Bukkit.broadcastMessage("fork");
		ItemStack is = event.getItem();
		//EpicArmor ea = Store.getEpicArmor(is);
		if(isLeatherArmor(is))
		{
			//Bukkit.broadcastMessage("" + ea.canEnchant());
			
			event.setCancelled(true);
		}
	}
	
	@EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = false)
	public void onEntityDamageByEntityEvent(EntityDamageByEntityEvent event)
	{
		if(!(event.getEntity() instanceof Player))
		{
			loc = event.getEntity().getLocation();
			return;
		}
	}
	
	@EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = false)
	public void onEntityDamageEvent(EntityDamageEvent event)
	{
		if(!(event.getEntity() instanceof Player))
			return;
		if(event.getCause().equals(DamageCause.DROWNING) || event.getCause().equals(DamageCause.FALL) || event.getCause().equals(DamageCause.FALLING_BLOCK)
				|| event.getCause().equals(DamageCause.MAGIC) || event.getCause().equals(DamageCause.POISON) || event.getCause().equals(DamageCause.STARVATION)
				|| event.getCause().equals(DamageCause.SUFFOCATION) || event.getCause().equals(DamageCause.WITHER))
			return;
		Player player = (Player)event.getEntity();
		if(player.getNoDamageTicks() > 10)
			return;
		for(ItemStack is : player.getInventory().getArmorContents())
		{
			if(isLeatherArmor(is))
			{	
				if(Math.random() <= 0.99)
				{
					is.setDurability((short) (is.getDurability()-1));
					if(is.getDurability() < 0)
						is.setDurability((short)0);
				}
			}
		}
	}
	
	@EventHandler
	public void onBossDeathEvent(BossDeathEvent event)
	{
		String bossName = event.getBossName();
		if(plugin.getConfig().contains("Drops." + bossName))
		{
			List<String> items = plugin.getConfig().getStringList("Drops." + bossName);
			for(String s : items)
			{
				String[] things = s.split(",");
				double chance = Double.valueOf(things[1]);
				String[] itemName = things[0].split(" ");
				if(Math.random() <= chance)
				{
					if(itemName.length == 2)
					{
						String temp = itemName[1].toLowerCase();
						char c = temp.charAt(0);
						char c2 = Character.toUpperCase(c);
						//String type = temp.replace(temp.charAt(0), c2);
						temp = itemName[0].toLowerCase();
						c = temp.charAt(0);
						c2 = Character.toUpperCase(c);
						String color = temp.replace(temp.charAt(0), c2);
						
						ItemStack is = new ItemStack(Material.valueOf("LEATHER_" + itemName[1].toUpperCase()));
						Item i = event.getPlayer().getWorld().dropItemNaturally(loc, is);
						is = i.getItemStack();
						is = setColor(is,color);
						Store.addNameAndLore(is);
					}
					else
					{
						int swordCounter=1;
						while(true)
						{
							if(!plugin.getConfig().contains("Weapons.Swords." + swordCounter + ".Name"))
							{
								--swordCounter;
								break;
							}
								
							++swordCounter;
						}
						int axeCounter=1;
						while(true)
						{
							if(!plugin.getConfig().contains("Weapons.Swords." + axeCounter + ".Name"))
							{
								--axeCounter;
								break;
							}
							++axeCounter;
						}
						int bowCounter=1;
						while(true)
						{
							if(!plugin.getConfig().contains("Weapons.Swords." + bowCounter + ".Name"))
							{
								--bowCounter;
								break;
							}
							++bowCounter;
						}
						for(int i=1;i<=swordCounter;++i)
						{
							if(things[0].equalsIgnoreCase(plugin.getConfig().getString("Weapons.Swords." + String.valueOf(i) + ".Name")))
							{
								String type = plugin.getConfig().getString("Weapons.Swords." + i + ".Type");
								ItemStack is = null;
								if(type.equalsIgnoreCase("wood"))
								{
									is = new ItemStack(268);
								}
								else if(type.equalsIgnoreCase("stone"))
								{
									is = new ItemStack(272);
								}
								else if(type.equalsIgnoreCase("iron"))
								{
									is = new ItemStack(267);
								}
								else if(type.equalsIgnoreCase("gold"))
								{
									is = new ItemStack(283);
								}
								else if(type.equalsIgnoreCase("diamond"))
								{
									is = new ItemStack(276);
								}
								Item item = event.getPlayer().getWorld().dropItemNaturally(loc, is);
								Namer.setName(item.getItemStack(), plugin.getConfig().getString("Weapons.Swords." + i + ".Name"));
								Namer.setLore(item.getItemStack(), plugin.getConfig().getStringList("Weapons.Swords." + i + ".Lore"));
								item.getItemStack().addUnsafeEnchantment(Enchantment.DURABILITY, plugin.getConfig().getInt("Weapons.Swords." + i + ".Durability"));
								List<String> enchantments = plugin.getConfig().getStringList("Weapons.Swords." + i + ".Enchantments");
								for(String en : enchantments)
								{
									String[] ent = en.split(",");
									if(ent[0].equalsIgnoreCase("sharpness"))
									{
										item.getItemStack().addUnsafeEnchantment(Enchantment.DAMAGE_ALL, Integer.valueOf(ent[1]));
									}
									else if(ent[0].equalsIgnoreCase("ARTHROPODS"))
									{
										item.getItemStack().addUnsafeEnchantment(Enchantment.DAMAGE_ARTHROPODS, Integer.valueOf(ent[1]));
									}
									else if(ent[0].equalsIgnoreCase("undead"))
									{
										item.getItemStack().addUnsafeEnchantment(Enchantment.DAMAGE_UNDEAD, Integer.valueOf(ent[1]));
									}
									else if(ent[0].equalsIgnoreCase("fire"))
									{
										item.getItemStack().addUnsafeEnchantment(Enchantment.FIRE_ASPECT, Integer.valueOf(ent[1]));
									}
									else if(ent[0].equalsIgnoreCase("loot"))
									{
										item.getItemStack().addUnsafeEnchantment(Enchantment.LOOT_BONUS_MOBS, Integer.valueOf(ent[1]));
									}
									else if(ent[0].equalsIgnoreCase("knockback"))
									{
										item.getItemStack().addUnsafeEnchantment(Enchantment.KNOCKBACK, Integer.valueOf(ent[1]));
									}
								}
								break;
							}
						}
						for(int i=1;i<=axeCounter;++i)
						{
							if(things[0].equalsIgnoreCase(plugin.getConfig().getString("Weapons.Axes." + i + ".Name")))
							{
								String type = plugin.getConfig().getString("Weapons.Axes." + i + ".Type");
								ItemStack is = null;
								if(type.equalsIgnoreCase("wood"))
								{
									is = new ItemStack(271);
								}
								else if(type.equalsIgnoreCase("stone"))
								{
									is = new ItemStack(275);
								}
								else if(type.equalsIgnoreCase("iron"))
								{
									is = new ItemStack(258);
								}
								else if(type.equalsIgnoreCase("gold"))
								{
									is = new ItemStack(286);
								}
								else if(type.equalsIgnoreCase("diamond"))
								{
									is = new ItemStack(279);
								}
								Item item = event.getPlayer().getWorld().dropItemNaturally(loc, is);
								Namer.setName(item.getItemStack(), plugin.getConfig().getString("Weapons.Axes." + i + ".Name"));
								Namer.setLore(item.getItemStack(), plugin.getConfig().getStringList("Weapons.Axes." + i + ".Lore"));
								item.getItemStack().addUnsafeEnchantment(Enchantment.DURABILITY, plugin.getConfig().getInt("Weapons.Axes." + i + ".Durability"));
								List<String> enchantments = plugin.getConfig().getStringList("Weapons.Axes." + i + ".Enchantments");
								for(String en : enchantments)
								{
									String[] ent = en.split(",");
									if(ent[0].equalsIgnoreCase("sharpness"))
									{
										item.getItemStack().addUnsafeEnchantment(Enchantment.DAMAGE_ALL, Integer.valueOf(ent[1]));
									}
									else if(ent[0].equalsIgnoreCase("ARTHROPODS"))
									{
										item.getItemStack().addUnsafeEnchantment(Enchantment.DAMAGE_ARTHROPODS, Integer.valueOf(ent[1]));
									}
									else if(ent[0].equalsIgnoreCase("undead"))
									{
										item.getItemStack().addUnsafeEnchantment(Enchantment.DAMAGE_UNDEAD, Integer.valueOf(ent[1]));
									}
									else if(ent[0].equalsIgnoreCase("fire"))
									{
										item.getItemStack().addUnsafeEnchantment(Enchantment.FIRE_ASPECT, Integer.valueOf(ent[1]));
									}
									else if(ent[0].equalsIgnoreCase("loot"))
									{
										item.getItemStack().addUnsafeEnchantment(Enchantment.LOOT_BONUS_MOBS, Integer.valueOf(ent[1]));
									}
									else if(ent[0].equalsIgnoreCase("knockback"))
									{
										item.getItemStack().addUnsafeEnchantment(Enchantment.KNOCKBACK, Integer.valueOf(ent[1]));
									}
								}
								break;
							}
						}
						for(int i=1;i<=bowCounter;++i)
						{
							if(things[0].equalsIgnoreCase(plugin.getConfig().getString("Weapons.Bows." + i + ".Name")))
							{
								ItemStack is = new ItemStack(261);
								Item item = event.getPlayer().getWorld().dropItemNaturally(loc, is);
								Namer.setName(item.getItemStack(), plugin.getConfig().getString("Weapons.Bows." + i + ".Name"));
								Namer.setLore(item.getItemStack(), plugin.getConfig().getStringList("Weapons.Bows." + i + ".Lore"));
								item.getItemStack().addUnsafeEnchantment(Enchantment.DURABILITY, plugin.getConfig().getInt("Weapons.Bows." + i + ".Durability"));
								List<String> enchantments = plugin.getConfig().getStringList("Weapons.Bows." + i + ".Enchantments");
								for(String en : enchantments)
								{
									String[] ent = en.split(",");
									if(ent[0].equalsIgnoreCase("damage"))
									{
										item.getItemStack().addUnsafeEnchantment(Enchantment.ARROW_DAMAGE, Integer.valueOf(ent[1]));
									}
									else if(ent[0].equalsIgnoreCase("fire"))
									{
										item.getItemStack().addUnsafeEnchantment(Enchantment.ARROW_FIRE, Integer.valueOf(ent[1]));
									}
									else if(ent[0].equalsIgnoreCase("infinite"))
									{
										item.getItemStack().addUnsafeEnchantment(Enchantment.ARROW_INFINITE, Integer.valueOf(ent[1]));
									}
									else if(ent[0].equalsIgnoreCase("knockback"))
									{
										item.getItemStack().addUnsafeEnchantment(Enchantment.ARROW_KNOCKBACK, Integer.valueOf(ent[1]));
									}
								}
								break;
							}
						}
					}
				}
			}
		}
	}
	
	@EventHandler
	public void onPlayerJoinEvent(PlayerJoinEvent event)
	{
		if(plugin.getConfig().getBoolean("UpdateCheckerEnabled"))
		{
			if(event.getPlayer().hasPermission("epicgear.update") || event.getPlayer().hasPermission("epicgear.admin"))
			{
				if(plugin.uc.isUpdateNeeded())
				{
					event.getPlayer().sendMessage(ChatColor.LIGHT_PURPLE + "New Version Of EpicGear Available: " + plugin.uc.getVersion());
					event.getPlayer().sendMessage(ChatColor.LIGHT_PURPLE + "Get The New Version At: " + plugin.uc.getLink());
				}
			}
		}
		AEHandler.addPlayer(new AEPlayer(event.getPlayer()));
	}
	
	@EventHandler
	public void onPlayerQuitEvent(PlayerQuitEvent event)
	{
		AEHandler.removePlayer(event.getPlayer().getName());
	}
	
	@EventHandler
	public void onInventoryClickEvent(InventoryClickEvent event)
	{
		if(event.getInventory().getType().equals(InventoryType.ANVIL))
		{
			ItemStack is = event.getCurrentItem();
			if(isLeatherArmor(is))
			{
				if(getColorName(is) != null)
				{
					event.setCancelled(true);
					return;
				}
			}
			else if(isWeapon(is))
			{
				if(Namer.getName(is) != null && !Namer.getName(is).isEmpty() && Namer.getName(is).length() != 0)
				{
					int id = is.getTypeId();
					switch(id)
					{
					case 268:
					case 272:
					case 267:
					case 283:
					case 276:
						int i = 1;
						while(plugin.getConfig().contains("Weapons.Swords." + i))
						{
							++i;
						}
						--i;
						for(int j = 1;j<=i;++j)
						{
							if(!is.getType().toString().toLowerCase().split("_")[0].equalsIgnoreCase(plugin.getConfig().getString("Weapons.Swords." + j + ".Type")))
								continue;
							List<String> thing = plugin.getConfig().getStringList("Weapons.Swords." + j + ".Lore");
							String name = thing.get(thing.size()-1);
							name = Namer.addChatColor(name);
							String[] things = Namer.getLoreAsArray(is);
							if(things.length == 0)
								return;
							String itemInHandName = things[things.length-1];
								
							if(itemInHandName.equalsIgnoreCase(name))
							{
								event.setCancelled(true);
								return;
							}
						}
					case 271:
					case 275:
					case 258:
					case 286:
					case 279:
						int i2 = 1;
						while(plugin.getConfig().contains("Weapons.Swords." + i2))
						{
							++i2;
						}
						--i2;
						for(int j = 1;j<=i2;++j)
						{
							if(!is.getType().toString().toLowerCase().split("_")[0].equalsIgnoreCase(plugin.getConfig().getString("Weapons.Axes." + j + ".Type")))
								continue;
							List<String> thing = plugin.getConfig().getStringList("Weapons.Axes." + j + ".Lore");
							String name = thing.get(thing.size()-1);
							name = Namer.addChatColor(name);
							String[] things = Namer.getLoreAsArray(is);
							if(things.length == 0)
								return;
							String itemInHandName = things[things.length-1];
							
							if(itemInHandName.equalsIgnoreCase(name))
							{
								event.setCancelled(true);
								return;
							}
						}
					case 261:
						int i3 = 1;
						while(true)
						{
							if(!plugin.getConfig().contains("Weapons.Bows." + String.valueOf(i3)))
							{
								--i3;
								break;
							}
							++i3;
						}
						for(int j=1;j<=i3;++j)
						{
							List<String> thing = plugin.getConfig().getStringList("Weapons.Bows." + j + ".Lore");
							String name = thing.get(thing.size()-1);
							name = Namer.addChatColor(name);
							String[] things = Namer.getLoreAsArray(is);
							if(things.length == 0)
								return;
							String itemInHandName = things[things.length-1];
							
							if(itemInHandName.equalsIgnoreCase(name))
							{
								event.setCancelled(true);
								return;
							}
						}
					}
				}	
			}
		}
	}
	
	@EventHandler
	public void onPlayerDeathEvent(PlayerDeathEvent event)
	{
		double chance = AEHandler.getDeathRemoveChance();
		for(ItemStack is : event.getDrops())
		{
			if(isArmor(is) || isWeapon(is))
			{
				if(Math.random() <= chance)
					event.getDrops().remove(is);
			}
		}
	}
	
	public int getColor(ItemStack item)
	{
		LeatherArmorMeta lam = (LeatherArmorMeta) item.getItemMeta();
		return lam.getColor().asRGB();
	}
	
	private ItemStack setColor(ItemStack item, int color)
	{
		LeatherArmorMeta lam = (LeatherArmorMeta) item.getItemMeta();
		lam.setColor(Color.fromRGB(color));
		item.setItemMeta(lam);
		return item;
	}

	public boolean isLeatherArmor(ItemStack is)
	{
		if(is == null)
			return false;
		Material ma = is.getType();
		if(ma == null)
			return false;
		if(ma.equals(Material.LEATHER_HELMET) || ma.equals(Material.LEATHER_CHESTPLATE) || ma.equals(Material.LEATHER_LEGGINGS) || ma.equals(Material.LEATHER_BOOTS))
			return true;
		return false;
	}

	public boolean compare(ItemStack is1, ItemStack is2)
	{
		LeatherArmorMeta lam1 = (LeatherArmorMeta) is1.getItemMeta();
		LeatherArmorMeta lam2 = (LeatherArmorMeta) is2.getItemMeta();
		
		int co1 = lam1.getColor().asRGB();
		int co2 = lam2.getColor().asRGB();
		
		if(co1 == co2)
			return true;
		else
			return false;
	}
	
	private boolean isWeapon(ItemStack is)
	{
		if(is == null)
			return false;
		int ID = is.getTypeId();
		if(ID == 268 || ID == 267 || ID == 276 || ID == 283 || ID == 271 || ID == 275 || ID == 286 || ID == 279)
		{
			return true;
		}
		return false;
	}
	
	private boolean isArmor(ItemStack is)
	{
		if(is == null)
			return false;
		switch(is.getTypeId())
		{
		case 298:
		case 299:
		case 300:
		case 301:
		case 302:
		case 303:
		case 304:
		case 305:
		case 306:
		case 307:
		case 308:
		case 309:
		case 310:
		case 311:
		case 312:
		case 313:
		case 314:
		case 315:
		case 316:
		case 317:
			return true;
		default:
			return false;	
		}
	}
	
	public void activateSkill(String skill, Location target, Player player)
	{
		if(skill.equalsIgnoreCase("lightningstorm"))
		{
			new SkillLightningStorm(target,player).run();
		}
		else if(skill.equalsIgnoreCase("firestorm"))
		{
			new SkillFireStorm(target,player).run();
		}
		else if(skill.equalsIgnoreCase("witherstorm"))
		{
			new SkillWitherStorm(target,player).run();
		}
	}
	
	public String getColorName(ItemStack is)
	{
		LeatherArmorMeta lam = (LeatherArmorMeta) is.getItemMeta();
		int c = lam.getColor().asRGB();
		switch(c)
		{
		case BLACK:
			return "Black";
		case RED:
			return "Red";
		case GREEN:
			return "Green";
		case BLUE:
			return "Blue";
		case WHITE:
			return "White";
		case PURPLE:
			return "Purple";
		case TEAL:
			return "Teal";
		case PINK:
			return "Pink";
		case ORANGE:
			return "Orange";
		case YELLOW:
			return "Yellow";
		default:
			return null;
		}
	}
	
	public ItemStack setColor(ItemStack is, String color)
	{
		if(color.equalsIgnoreCase("black"))
		{
			return setColor(is,BLACK);
		}
		else if(color.equalsIgnoreCase("red"))
		{
			return setColor(is,RED);
		}
		else if(color.equalsIgnoreCase("green"))
		{
			return setColor(is,GREEN);
		}
		else if(color.equalsIgnoreCase("blue"))
		{
			return setColor(is,BLUE);
		}
		else if(color.equalsIgnoreCase("white"))
		{
			return setColor(is,WHITE);
		}
		else if(color.equalsIgnoreCase("purple"))
		{
			return setColor(is,PURPLE);
		}
		else if(color.equalsIgnoreCase("teal"))
		{
			return setColor(is,TEAL);
		}
		else if(color.equalsIgnoreCase("pink"))
		{
			return setColor(is,PINK);
		}
		else if(color.equalsIgnoreCase("orange"))
		{
			return setColor(is,ORANGE);
		}
		else if(color.equalsIgnoreCase("yellow"))
		{
			return setColor(is,YELLOW);
		}
		return null;
	}
	
	public boolean isColor(String color)
	{
		if(color.equalsIgnoreCase("black"))
		{
			return true;
		}
		else if(color.equalsIgnoreCase("red"))
		{
			return true;
		}
		else if(color.equalsIgnoreCase("green"))
		{
			return true;
		}
		else if(color.equalsIgnoreCase("blue"))
		{
			return true;
		}
		else if(color.equalsIgnoreCase("white"))
		{
			return true;
		}
		else if(color.equalsIgnoreCase("purple"))
		{
			return true;
		}
		else if(color.equalsIgnoreCase("teal"))
		{
			return true;
		}
		else if(color.equalsIgnoreCase("pink"))
		{
			return true;
		}
		else if(color.equalsIgnoreCase("orange"))
		{
			return true;
		}
		else if(color.equalsIgnoreCase("yellow"))
		{
			return true;
		}
		return false;
	}
	
	private boolean hasValidColor(ItemStack is)
	{
		LeatherArmorMeta lam = (LeatherArmorMeta)is.getItemMeta();
		if(lam.getColor().asRGB() == BLACK)
			return true;
		else if(lam.getColor().asRGB() == RED)
			return true;
		else if(lam.getColor().asRGB() == GREEN)
			return true;
		else if(lam.getColor().asRGB() == BLUE)
			return true;
		else if(lam.getColor().asRGB() == WHITE)
			return true;
		else if(lam.getColor().asRGB() == PURPLE)
			return true;
		else if(lam.getColor().asRGB() == TEAL)
			return true;
		else if(lam.getColor().asRGB() == PINK)
			return true;
		else if(lam.getColor().asRGB() == ORANGE)
			return true;
		else if(lam.getColor().asRGB() == YELLOW)
			return true;
		return false;
	}
}
