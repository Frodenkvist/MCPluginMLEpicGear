package me.frodenkvist.armoreditor;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Logger;

import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class ArmorEditor extends JavaPlugin
{
	public File configFile;
	public final Logger logger = Logger.getLogger("Minecraft");
	public static ArmorEditor plugin;
	public final PlayerListener pl = new PlayerListener(this);
	public boolean townyEnabled = false;
	protected UpdateChecker uc;
	
	@Override
	public void onDisable()
	{
		PluginDescriptionFile pdfFile = this.getDescription();
		this.logger.info(pdfFile.getName() + " Has Been Disabled!");
	}
	
	@Override
	public void onEnable()
	{
		plugin = this;
		if(getConfig().getBoolean("UpdateCheckerEnabled"))
		{
			uc = new UpdateChecker(this,"http://dev.bukkit.org/server-mods/epicgear/files.rss");
			if(uc.isUpdateNeeded())
			{
				logger.info("New Version Of EpicGear Available: " + uc.getVersion());
				logger.info("Get The New Version At: " + uc.getLink());
			}
		}
		//Bukkit.getMessenger().registerOutgoingPluginChannel(this, "Test");
		configFile = new File(getDataFolder(), "config.yml");
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvents(this.pl, this);
		this.logger.info("Loading Config...");
		if(pm.isPluginEnabled("Towny"))
		{
			townyEnabled = true;
		}
		try
		{
			firstRun();
			new File("plugins/EpicGear/log.txt").createNewFile();
			this.logger.info("Config Loaded");
	    }
		catch (Exception e)
		{
	        e.printStackTrace();
	    }
		loadPlayers();
		this.logger.info("Loading Store...");
		
		Store.load(getConfig());
		this.logger.info("Store Loaded");
		AEHandler.load(this);
		PluginDescriptionFile pdfFile = this.getDescription();
		this.logger.info(pdfFile.getName() + " Version " + pdfFile.getVersion() + " Has Been Enabled!");
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args)
	{
		if(sender instanceof Player)
		{
			final Player player = (Player)sender;
			if(commandLabel.equalsIgnoreCase("charge"))
			{
				AdditionalEnchants.EnchantManagment.addCharges(player, player.getItemInHand());
			}
			if(commandLabel.equalsIgnoreCase("ae"))
			{
				if(args.length == 1)
				{
					if(player.hasPermission("epicgear.admin") && args[0].equalsIgnoreCase("reload"))
					{
						reloadConfig();
						loadPlayers();
						Store.load(getConfig());
						AEHandler.load(this);
						player.sendMessage(ChatColor.GREEN + "Epic Gear Reloaded!");
						return true;
					}
					else if(player.hasPermission("epicgear.admin") && args[0].equalsIgnoreCase("maxpoints"))
					{
						AEHandler.getPlayer(player).setKillCounter(AEPlayer.MAX_KILLCOUNTER);
						player.sendMessage(ChatColor.GREEN + "You Have Recived The Maximum Amount Of Points");
					}
					else if(args[0].equalsIgnoreCase("store")/* && (player.hasPermission("epicgear.admin") || player.hasPermission("epicgear.store"))*/)
					{
						Store.displayStoreList(player, 1);
						return true;
					}
					/*else if(args[0].equalsIgnoreCase("ls"))
					{
						/*ScoreboardManager sm = Bukkit.getScoreboardManager();
						Scoreboard sb = sm.getMainScoreboard();
						sb.registerNewObjective("Hit", "Thing");
						Objective obj = sb.getObjective("Hit");
						obj.setDisplayName("Plos");
						obj.setDisplaySlot(DisplaySlot.SIDEBAR);
						Score score = obj.getScore(player);
						score.setScore(31);
						player.setScoreboard(sb);
						player.sendPluginMessage(this, "Test", "thing".getBytes());
						Bukkit.getMessenger().dispatchIncomingMessage(player, "Test", "thingy".getBytes());
					}*/
					else if(args[0].equalsIgnoreCase("repair"))
					{
						int cost;
				        int playerHave;
				        FireworkEffectPlayer fep;
				        if ((commandLabel.equalsIgnoreCase("ae")) && (args[0].equalsIgnoreCase("repair")))
				        {
				        	if(!Store.isEpicGear(player.getItemInHand()))
				        	{
				        		player.sendMessage(ChatColor.RED + "You Need To Have An Epic Armor/Weapon In Your Hand!");
				        		return false;
				        	}
				        	EpicGear eg = Store.getEpicGear(player.getItemInHand());
				        	int amount = 0;
				        	for (int i = 0; i < player.getInventory().getContents().length; i++)
				        	{
				        		ItemStack is = player.getInventory().getContents()[i];
				        		if (is != null)
				        		{
				        			if (is.getType().equals(Material.NETHER_STAR))
				        			{
				        				if (AEHandler.isRepairToken(is))
				        				{
				        					amount += is.getAmount();
				        					is.setType(Material.AIR);
				        					player.getInventory().setItem(i, is);
				        				}
				        			}
				        		}
				        	}
				        	//ItemStack is = player.getItemInHand();
				        	cost = eg.getRepairCost();
				        	if (amount >= cost)
				        	{
				        		playerHave = amount - cost;

				        		amount = playerHave / 64;
				        		if (amount == 0)
				        		{
				        			ItemStack token = AEHandler.getRepairToken();
				        			token.setAmount(playerHave);
				        			player.getInventory().addItem(new ItemStack[] { token });
				        		}
				        		else
				        		{
				        			for (int i = 0; i < amount; i++)
				        			{
				        				ItemStack token = AEHandler.getRepairToken();
				        				token.setAmount(64);
				        				player.getInventory().addItem(new ItemStack[] { token });
				        			}
				        			if (playerHave % 64 != 0)
				        			{
				        				ItemStack token = AEHandler.getRepairToken();
				        				token.setAmount(playerHave % 64);
				        				player.getInventory().addItem(new ItemStack[] { token });
				        			}
				        		}
				        		fep = new FireworkEffectPlayer();
				        		if (Math.random() <= 0.15D)
				        		{
				        			player.getInventory().setItemInHand(new ItemStack(Material.AIR));
				        			try
				        			{
				        				fep.playFirework(player.getWorld(), player.getLocation(), FireworkEffect.builder().flicker(true).with(FireworkEffect.Type.BURST).withColor(Color.BLACK).build());
				        			}
				        			catch (Exception e)
				        			{
				        				e.printStackTrace();
				        			}

				        		}
				        		else
				        		{
				        			player.getInventory().getItemInHand().setDurability((short)0);
				        			try
				        			{
				        				fep.playFirework(player.getWorld(), player.getLocation(), FireworkEffect.builder().flicker(true).with(FireworkEffect.Type.BURST).withColor(Color.GREEN).build());
				        			}
				        			catch (Exception e)
				        			{
				        				e.printStackTrace();
				        			}
				        		}
				        		return true;
				        	}
				    	     
				        	player.sendMessage(ChatColor.RED + "You Dont Have Enough Tokens To Repair This, It Costs " + cost + " Tokens To Repair This Item!");
				        	return false;
				        }
					}
				}
				else if(args.length == 2)
				{
					if(args[0].equalsIgnoreCase("store")/* && (player.hasPermission("epicgear.admin") || player.hasPermission("epicgear.store"))*/)
					{
						Store.displayStoreList(player, Integer.valueOf(args[1]));
						return true;
					}
					/*else if(args[0].equalsIgnoreCase("create") && (player.hasPermission("epicgear.admin") || player.hasPermission("epicgear.create")))
					{
						if(args[1].equalsIgnoreCase("armor"))
						{
							
						}
						else if(args[1].equalsIgnoreCase("weapon"))
						{
							
						}
					}*/
					else if(args[0].equalsIgnoreCase("info") && (player.hasPermission("epicgear.admin") || player.hasPermission("epicgear.info")))
					{
						EpicGear eg = Store.getEpicGear(args[1]);
						if(eg == null)
						{
							player.sendMessage(ChatColor.RED + "That Item Could Not Be Found!");
							return false;
						}
						eg.displayInfo(player);
					}
					else if(args[0].equalsIgnoreCase("buy")/* && (player.hasPermission("epicgear.admin") || player.hasPermission("epicgear.buy"))*/)
					{
						boolean check = false;
						for(ItemStack is : player.getInventory().getContents())
						{
							if(is == null)
							{
								check = true;
								break;
							}
								
							if(is.getType().equals(Material.AIR))
							{
								check = true;
								break;
							}
								
						}
						if(!check)
						{
							player.sendMessage(ChatColor.RED + "You Need To Have An Empty Slot In Your Inventory To Buy");
							return false;
						}
						
						if(args[1].equalsIgnoreCase("repair"))
						{
							int cost = getConfig().getInt("RepairItem.Cost");
							
							if(player.hasPermission("epicgear.admin"))
							{
								final ItemStack is = AEHandler.getRepairToken();
								getServer().getScheduler().scheduleSyncDelayedTask(this, new Runnable()
								{
									@Override
									public void run()
									{
										player.getInventory().addItem(is);
										player.sendMessage(ChatColor.GREEN + "You Bought A(n) " + Namer.getName(is));
										try
										{
											PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("plugins/EpicGear/log.txt", true)));
											DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
											Date date = new Date();
										    out.println("At " + dateFormat.format(date) + " " + player.getName() + " Bought A " + Namer.getName(is));
										    out.close();
										}
										catch(Exception e)
										{
											logger.info("Could Not Write In Log!");
											e.printStackTrace();
										}
									}
								},1L);
								return true;
							}
							else
							{
								Inventory inv = player.getInventory();
								double playerHave = 0;
								if(getConfig().getInt("MoneyItem.ID") != -1)
								{
									for(int i = 0;i<inv.getContents().length;++i)
									{
										ItemStack is = inv.getContents()[i];
										
										if(is != null && is.getTypeId() == getConfig().getInt("MoneyItem.ID")/* && !AEHandler.isRepairToken(is)*/)
										{
											playerHave += is.getAmount();
											//temp.add(i);
											inv.setItem(i, null);
										}
									}
								}
								if(playerHave >= cost)
								{
									if(getConfig().getInt("MoneyItem.ID") != -1)
									{
										playerHave = playerHave - cost;
										int amount = ((int)playerHave)/64;
										if(amount==0)
										{
											ItemStack money = AEHandler.getToken();
											money.setAmount((int)playerHave);
											inv.addItem(money);
										}
										else
										{
											for(int i=0;i<amount;++i)
											{
												ItemStack money = AEHandler.getToken();
												money.setAmount(((int)playerHave)%64);
												inv.addItem(money);
											}
											if(((int)playerHave)%64 != 0)
											{
												ItemStack money = AEHandler.getToken();
												money.setAmount(64);
												inv.addItem(money);
											}
										}
									}
									final ItemStack is = AEHandler.getRepairToken();
									getServer().getScheduler().scheduleSyncDelayedTask(this, new Runnable()
									{
										@Override
										public void run()
										{
											player.sendMessage(ChatColor.GREEN + "You Bought A(n) " + Namer.getName(is));
											player.getInventory().addItem(is);
											try
											{
												PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("plugins/EpicGear/log.txt", true)));
												DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
												Date date = new Date();
											    out.println("At " + dateFormat.format(date) + " " + player.getName() + " Bought A " + Namer.getName(is));
											    out.close();
											}
											catch(Exception e)
											{
												logger.info("Could Not Write In Log!");
											}
										}
									},1L);
								}
								else
								{
									if(playerHave != 0)
									{
										int amount = ((int)playerHave)/64;
										if(amount==0)
										{
											ItemStack money = AEHandler.getToken();
											money.setAmount((int)playerHave);
											inv.addItem(money);
										}
										else
										{
											for(int i=0;i<amount;++i)
											{
												ItemStack money = AEHandler.getToken();
												money.setAmount(((int)playerHave)%64);
												inv.addItem(money);
											}
											if(((int)playerHave)%64 != 0)
											{
												ItemStack money = AEHandler.getToken();
												money.setAmount(64);
												inv.addItem(money);
											}
										}
									}
									player.sendMessage(ChatColor.RED + "You Do Not Have Enough Money To Buy That!");
								}
								return true;
							}
						}
						
						String name = args[1];
						EpicGear eg = Store.getEpicGear(name);
						
						if(eg == null)
						{
							player.sendMessage(ChatColor.RED + "That Item Could Not Be Found!");
							return false;
						}
						int cost = eg.getCost();
						if(player.hasPermission("epicgear.admin"))
						{
							ItemStack b = eg.getItem();
							final ItemStack is = b.clone();
							getServer().getScheduler().scheduleSyncDelayedTask(this, new Runnable()
							{
								@Override
								public void run()
								{
									player.getInventory().addItem(is);
									player.sendMessage(ChatColor.GREEN + "You Bought A(n) " + Namer.getName(is));
									try
									{
										PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("plugins/EpicGear/log.txt", true)));
										DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
										Date date = new Date();
									    out.println("At " + dateFormat.format(date) + " " + player.getName() + " Bought A " + Namer.getName(is));
									    out.close();
									}
									catch(Exception e)
									{
										logger.info("Could Not Write In Log!");
										e.printStackTrace();
									}
								}
							},1L);
							return true;
						}
						else
						{
							if(eg.isHidden())
							{
								player.sendMessage(ChatColor.RED + "That Item Could Not Be Found!");
								return false;
							}
						}
						Inventory inv = player.getInventory();
						double playerHave = 0;
						if(getConfig().getInt("MoneyItem.ID") != -1)
						{
							for(int i = 0;i<inv.getContents().length;++i)
							{
								ItemStack is = inv.getContents()[i];
								
								if(is != null && is.getTypeId() == getConfig().getInt("MoneyItem.ID")/* && !AEHandler.isRepairToken(is)*/)
								{
									playerHave += is.getAmount();
									//temp.add(i);
									inv.setItem(i, null);
								}
							}
						}
						if(playerHave >= cost)
						{
							if(getConfig().getInt("MoneyItem.ID") != -1)
							{
								playerHave = playerHave - cost;
								int amount = ((int)playerHave)/64;
								if(amount==0)
								{
									ItemStack money = AEHandler.getToken();
									money.setAmount((int)playerHave);
									inv.addItem(money);
								}
								else
								{
									for(int i=0;i<amount;++i)
									{
										ItemStack money = AEHandler.getToken();
										money.setAmount(((int)playerHave)%64);
										inv.addItem(money);
									}
									if(((int)playerHave)%64 != 0)
									{
										ItemStack money = AEHandler.getToken();
										money.setAmount(64);
										inv.addItem(money);
									}
								}
							}
							ItemStack b = eg.getItem();
							final ItemStack is = b.clone();
							getServer().getScheduler().scheduleSyncDelayedTask(this, new Runnable()
							{
								@Override
								public void run()
								{
									player.sendMessage(ChatColor.GREEN + "You Bought A(n) " + Namer.getName(is));
									player.getInventory().addItem(is);
									try
									{
										PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("plugins/EpicGear/log.txt", true)));
										DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
										Date date = new Date();
									    out.println("At " + dateFormat.format(date) + " " + player.getName() + " Bought A " + Namer.getName(is));
									    out.close();
									}
									catch(Exception e)
									{
										logger.info("Could Not Write In Log!");
									}
								}
							},1L);
						}
						else
						{
							if(playerHave != 0)
							{
								int amount = ((int)playerHave)/64;
								if(amount==0)
								{
									ItemStack money = AEHandler.getToken();
									money.setAmount((int)playerHave);
									inv.addItem(money);
								}
								else
								{
									for(int i=0;i<amount;++i)
									{
										ItemStack money = AEHandler.getToken();
										money.setAmount(((int)playerHave)%64);
										inv.addItem(money);
									}
									if(((int)playerHave)%64 != 0)
									{
										ItemStack money = AEHandler.getToken();
										money.setAmount(64);
										inv.addItem(money);
									}
								}
							}
							player.sendMessage(ChatColor.RED + "You Do Not Have Enough Money To Buy That!");
						}
						return true;
					}
				}
			}
		}
		else
		{
			if(commandLabel.equalsIgnoreCase("eg") && args.length == 1 && args[0].equalsIgnoreCase("reload"))
			{
				reloadConfig();
				loadPlayers();
				
				Store.load(getConfig());
				sender.sendMessage(ChatColor.GREEN + "Epic Gear Reloaded!");
			}
		}
		return true;
	}
	
	
	
	private void firstRun() throws Exception
	{
	    if(!configFile.exists())
	    {
	        configFile.getParentFile().mkdirs();
	        copy(getResource("config.yml"), configFile);
	    }
	}
	private void copy(InputStream in, File file)
	{
	    try
	    {
	        OutputStream out = new FileOutputStream(file);
	        byte[] buf = new byte[1024];
	        int len;
	        while((len=in.read(buf))>0)
	        {
	            out.write(buf,0,len);
	        }
	        out.close();
	        in.close();
	    }
	    catch (Exception e)
	    {
	        e.printStackTrace();
	    }
	}
	
	public ItemStack setColor(ItemStack item, int color)
	{
		LeatherArmorMeta lam = (LeatherArmorMeta) item.getItemMeta();
		lam.setColor(Color.fromRGB(color));
		item.setItemMeta(lam);
		return item;
	}
	
	public void loadPlayers()
	{
		AEHandler.clearPlayers();
		for(Player p : getServer().getOnlinePlayers())
		{
			if(p == null)
				continue;
			AEHandler.addPlayer(new AEPlayer(p));
		}
	}
}
