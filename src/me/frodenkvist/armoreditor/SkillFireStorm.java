package me.frodenkvist.armoreditor;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import PvpBalance.Damage;
import PvpBalance.PvpHandler;

public class SkillFireStorm
{
	private Location center;
	private Player player;
	
	public SkillFireStorm(Location target, Player player)
	{
		center = target;
		this.player = player;
	}
	
	public void run()
	{
		final FireworkEffectPlayer fplayer = new FireworkEffectPlayer();
		final Location start1 = new Location(center.getWorld(),center.getX()+7,center.getY()+1,center.getZ());
		final Location start2 = start1.clone();
		try
		{
			final FireworkEffect fe = FireworkEffect.builder().flicker(true).with(Type.BURST).withColor(Color.RED).trail(false).build();
			fplayer.playFirework(start1.getWorld(), start1, fe);
			Bukkit.getScheduler().scheduleSyncDelayedTask(ArmorEditor.plugin, new Runnable()
			{
				@Override
				public void run()
				{
					try
					{
						fplayer.playFirework(start1.getWorld(), start1.add(0, 0, 1), fe);
						fplayer.playFirework(start2.getWorld(), start2.add(0, 0, -1), fe);
						Bukkit.getScheduler().scheduleSyncDelayedTask(ArmorEditor.plugin, new Runnable()
						{
							@Override
							public void run()
							{
								try
								{
									fplayer.playFirework(start1.getWorld(), start1.add(0, 0, 1), fe);
									fplayer.playFirework(start2.getWorld(), start2.add(0, 0, -1), fe);
									Bukkit.getScheduler().scheduleSyncDelayedTask(ArmorEditor.plugin, new Runnable()
									{
										@Override
										public void run()
										{
											try
											{
												fplayer.playFirework(start1.getWorld(), start1.add(-1, 0, 1), fe);
												fplayer.playFirework(start2.getWorld(), start2.add(-1, 0, -1), fe);
												Bukkit.getScheduler().scheduleSyncDelayedTask(ArmorEditor.plugin, new Runnable()
												{
													@Override
													public void run()
													{
														try
														{
															fplayer.playFirework(start1.getWorld(), start1.add(0, 0, 1), fe);
															fplayer.playFirework(start2.getWorld(), start2.add(0, 0, -1), fe);
															Bukkit.getScheduler().scheduleSyncDelayedTask(ArmorEditor.plugin, new Runnable()
															{
																@Override
																public void run()
																{
																	try
																	{
																		fplayer.playFirework(start1.getWorld(), start1.add(-1, 0, 1), fe);
																		fplayer.playFirework(start2.getWorld(), start2.add(-1, 0, -1), fe);
																		Bukkit.getScheduler().scheduleSyncDelayedTask(ArmorEditor.plugin, new Runnable()
																		{
																			@Override
																			public void run()
																			{
																				try
																				{
																					fplayer.playFirework(start1.getWorld(), start1.add(-1, 0, 1), fe);
																					fplayer.playFirework(start2.getWorld(), start2.add(-1, 0, -1), fe);
																					Bukkit.getScheduler().scheduleSyncDelayedTask(ArmorEditor.plugin, new Runnable()
																					{
																						@Override
																						public void run()
																						{
																							try
																							{
																								fplayer.playFirework(start1.getWorld(), start1.add(-1, 0, 0), fe);
																								fplayer.playFirework(start2.getWorld(), start2.add(-1, 0, 0), fe);
																								Bukkit.getScheduler().scheduleSyncDelayedTask(ArmorEditor.plugin, new Runnable()
																								{
																									@Override
																									public void run()
																									{
																										try
																										{
																											fplayer.playFirework(start1.getWorld(), start1.add(-1, 0, 1), fe);
																											fplayer.playFirework(start2.getWorld(), start2.add(-1, 0, -1), fe);
																											Bukkit.getScheduler().scheduleSyncDelayedTask(ArmorEditor.plugin, new Runnable()
																											{
																												@Override
																												public void run()
																												{
																													try
																													{
																														fplayer.playFirework(start1.getWorld(), start1.add(-1, 0, 0), fe);
																														fplayer.playFirework(start2.getWorld(), start2.add(-1, 0, 0), fe);
																														Bukkit.getScheduler().scheduleSyncDelayedTask(ArmorEditor.plugin, new Runnable()
																														{
																															@Override
																															public void run()
																															{
																																try
																																{
																																	fplayer.playFirework(start1.getWorld(), start1.add(-1, 0, 0), fe);
																																	fplayer.playFirework(start2.getWorld(), start2.add(-1, 0, 0), fe);
																																	Bukkit.getScheduler().scheduleSyncDelayedTask(ArmorEditor.plugin, new Runnable()
																																	{
																																		@Override
																																		public void run()
																																		{
																																			try
																																			{
																																				fplayer.playFirework(start1.getWorld(), start1.add(-1, 0, 0), fe);
																																				fplayer.playFirework(start2.getWorld(), start2.add(-1, 0, 0), fe);
																																				Bukkit.getScheduler().scheduleSyncDelayedTask(ArmorEditor.plugin, new Runnable()
																																				{
																																					@Override
																																					public void run()
																																					{
																																						try
																																						{
																																							fplayer.playFirework(start1.getWorld(), start1.add(-1, 0, 0), fe);
																																							fplayer.playFirework(start2.getWorld(), start2.add(-1, 0, 0), fe);
																																							Bukkit.getScheduler().scheduleSyncDelayedTask(ArmorEditor.plugin, new Runnable()
																																							{
																																								@Override
																																								public void run()
																																								{
																																									try
																																									{
																																										fplayer.playFirework(start1.getWorld(), start1.add(-1, 0, -1), fe);
																																										fplayer.playFirework(start2.getWorld(), start2.add(-1, 0, 1), fe);
																																										Bukkit.getScheduler().scheduleSyncDelayedTask(ArmorEditor.plugin, new Runnable()
																																										{
																																											@Override
																																											public void run()
																																											{
																																												try
																																												{
																																													fplayer.playFirework(start1.getWorld(), start1.add(-1, 0, 0), fe);
																																													fplayer.playFirework(start2.getWorld(), start2.add(-1, 0, 0), fe);
																																													Bukkit.getScheduler().scheduleSyncDelayedTask(ArmorEditor.plugin, new Runnable()
																																													{
																																														@Override
																																														public void run()
																																														{
																																															try
																																															{
																																																fplayer.playFirework(start1.getWorld(), start1.add(-1, 0, -1), fe);
																																																fplayer.playFirework(start2.getWorld(), start2.add(-1, 0, 1), fe);
																																																Bukkit.getScheduler().scheduleSyncDelayedTask(ArmorEditor.plugin, new Runnable()
																																																{
																																																	@Override
																																																	public void run()
																																																	{
																																																		try
																																																		{
																																																			fplayer.playFirework(start1.getWorld(), start1.add(-1, 0, -1), fe);
																																																			fplayer.playFirework(start2.getWorld(), start2.add(-1, 0, 1), fe);
																																																			Bukkit.getScheduler().scheduleSyncDelayedTask(ArmorEditor.plugin, new Runnable()
																																																			{
																																																				@Override
																																																				public void run()
																																																				{
																																																					try
																																																					{
																																																						fplayer.playFirework(start1.getWorld(), start1.add(0, 0, -1), fe);
																																																						fplayer.playFirework(start2.getWorld(), start2.add(0, 0, 1), fe);
																																																						Bukkit.getScheduler().scheduleSyncDelayedTask(ArmorEditor.plugin, new Runnable()
																																																						{
																																																							@Override
																																																							public void run()
																																																							{
																																																								try
																																																								{
																																																									fplayer.playFirework(start1.getWorld(), start1.add(-1, 0, -1), fe);
																																																									fplayer.playFirework(start2.getWorld(), start2.add(-1, 0, 1), fe);
																																																									Bukkit.getScheduler().scheduleSyncDelayedTask(ArmorEditor.plugin, new Runnable()
																																																									{
																																																										@Override
																																																										public void run()
																																																										{
																																																											try
																																																											{
																																																												fplayer.playFirework(start1.getWorld(), start1.add(0, 0, -1), fe);
																																																												fplayer.playFirework(start2.getWorld(), start2.add(0, 0, 1), fe);
																																																												Bukkit.getScheduler().scheduleSyncDelayedTask(ArmorEditor.plugin, new Runnable()
																																																												{
																																																													@Override
																																																													public void run()
																																																													{
																																																														try
																																																														{
																																																															fplayer.playFirework(start1.getWorld(), start1.add(0, 0, -1), fe);
																																																															fplayer.playFirework(start2.getWorld(), start2.add(0, 0, 1), fe);
																																																															Bukkit.getScheduler().scheduleSyncDelayedTask(ArmorEditor.plugin, new Runnable()
																																																															{
																																																																@Override
																																																																public void run()
																																																																{
																																																																	strikeLigtning(center,15,7);
																																																																}
																																																															},(long)1.65);
																																																														}
																																																														catch (Exception e)
																																																														{
																																																															e.printStackTrace();
																																																														}
																																																													}
																																																												},(long)1.65);
																																																											}
																																																											catch (Exception e)
																																																											{
																																																												e.printStackTrace();
																																																											}
																																																										}
																																																									},(long)1.65);
																																																								}
																																																								catch (Exception e)
																																																								{
																																																									e.printStackTrace();
																																																								}
																																																							}
																																																						},(long)1.65);
																																																					}
																																																					catch (Exception e)
																																																					{
																																																						e.printStackTrace();
																																																					}
																																																				}
																																																			},(long)1.65);
																																																		}
																																																		catch (Exception e)
																																																		{
																																																			e.printStackTrace();
																																																		}
																																																	}
																																																},(long)1.65);
																																															}
																																															catch (Exception e)
																																															{
																																																e.printStackTrace();
																																															}
																																														}
																																													},(long)1.65);
																																												}
																																												catch (Exception e)
																																												{
																																													e.printStackTrace();
																																												}
																																											}
																																										},(long)1.65);
																																									}
																																									catch (Exception e)
																																									{
																																										e.printStackTrace();
																																									}
																																								}
																																							},(long)1.65);
																																						}
																																						catch (Exception e)
																																						{
																																							e.printStackTrace();
																																						}
																																					}
																																				},(long)1.65);
																																			}
																																			catch (Exception e)
																																			{
																																				e.printStackTrace();
																																			}
																																		}
																																	},(long)1.65);
																																}
																																catch (Exception e)
																																{
																																	e.printStackTrace();
																																}
																															}
																														},(long)1.65);
																													}
																													catch (Exception e)
																													{
																														e.printStackTrace();
																													}
																												}
																											},(long)1.65);
																										}
																										catch (Exception e)
																										{
																											e.printStackTrace();
																										}
																									}
																								},(long)1.65);
																							}
																							catch (Exception e)
																							{
																								e.printStackTrace();
																							}
																						}
																					},(long)1.65);
																				}
																				catch (Exception e)
																				{
																					e.printStackTrace();
																				}
																			}
																		},(long)1.65);
																	}
																	catch (Exception e)
																	{
																		e.printStackTrace();
																	}
																}
															},(long)1.65);
														}
														catch (Exception e)
														{
															e.printStackTrace();
														}
													}
												},(long)1.65);
											}
											catch (Exception e)
											{
												e.printStackTrace();
											}
										}
									},(long)1.65);
								}
								catch (Exception e)
								{
									e.printStackTrace();
								}
							}
						},(long)1.65);
					}
					catch (Exception e)
					{
						e.printStackTrace();
					}
				}
			},(long)1.65);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public void strikeLigtning(Location center, int height, int radius)
	{
		int radiusX = radius;
		int radiusZ = radius;
		
		radiusX += 0.5;
        radiusZ += 0.5;
        
        double invRadiusX = 1 / radiusX;
        double invRadiusZ = 1 / radiusZ;

        //int ceilRadiusX = (int) Math.ceil(radiusX);
        //int ceilRadiusZ = (int) Math.ceil(radiusZ);
        
        for(Entity en : center.getWorld().getEntities())
        {
        	if(en == null)
        		continue;
        	if(!(en instanceof LivingEntity))
        		continue;
        	if(en instanceof Player)
        	{
        		Player le = (Player)en;
        		if(le.equals(player))
        			continue;
        	}
        	LivingEntity le = (LivingEntity)en;
        	//Bukkit.broadcastMessage("1");
        	Location loc = le.getLocation();
        	//Bukkit.broadcastMessage(center.getBlockX() + "");
        	if(center.getBlockX()+radius-loc.getBlockX() >= 0 && center.getBlockX()+radius-loc.getBlockX() < radius*2)
        	{
	        		//Bukkit.broadcastMessage("2");
	        		if(center.getBlockZ()+radius-loc.getBlockZ() >= 0 && center.getBlockZ()+radius-loc.getBlockZ() < radius*2)
	            	{
	        			//Bukkit.broadcastMessage("3");
	        			if(center.getBlockY()+height-loc.getBlockY() < center.getBlockY()+height)
	                	{
	        				//Bukkit.broadcastMessage("4");
	                		if(lengthSq((center.getBlockX()+radius-loc.getBlockX() + 1)*invRadiusX, (center.getBlockZ()+radius-loc.getBlockZ() + 1)*invRadiusZ) <= 1)
	                		{
	                			//Bukkit.broadcastMessage("5");
	                			//center.getWorld().strikeLightningEffect(le.getLocation());
	                			if(!Damage.canHit(le, player))
	                				continue;
	                			if(le instanceof Player)
	                				PvpHandler.getPvpPlayer((Player)le).uncheckedDamage(PvpHandler.getPvpPlayer((Player)le).gethealth()/2);
	                			else
	                				le.setHealth(le.getHealth()/2);
	                			le.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 20*10, 1));
	                			le.damage(0f);
	                		}
	                	}
	            	}
	        	}
        	
        }
	}
	
	private final double lengthSq(double x, double z)
    {
        return (x * x) + (z * z);
    }
}
