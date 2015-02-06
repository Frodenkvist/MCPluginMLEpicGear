package me.frodenkvist.armoreditor;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import PvpBalance.Damage;
import PvpBalance.PvpHandler;

public class SkillExecution extends Skill
{
	public SkillExecution(int cost)
	{
		super(cost);
	}
	
	public boolean run(Player caster)
	{
		Entity e = Utils.getTarget(caster, 25);
		if(e == null)
			return false;
		if(!(e instanceof Player))
			return false;
		final Player target = (Player)e;
		if(!Damage.canHit(target, caster))
			return false;
		final double x = ((caster.getLocation().getX() - target.getLocation().getX()) / 10) * -1;
		final double y = ((caster.getLocation().getY() - target.getLocation().getY()) / 10) * -1;
		final double z = ((caster.getLocation().getZ() - target.getLocation().getZ()) / 10) * -1;
		final Location loc = caster.getLocation().add(x,y,z);
		try
		{
			ParticleEffect.sendToLocation(ParticleEffect.TOWN_AURA, loc.clone().add(0, 1, 0),0.2f,0.5f,0.2f, (float)0.02, 50);
			loc.add(x,y,z);
			Bukkit.getScheduler().scheduleSyncDelayedTask(ArmorEditor.plugin, new Runnable()
			{
				@Override
				public void run()
				{
					try
					{
						ParticleEffect.sendToLocation(ParticleEffect.TOWN_AURA, loc.clone().add(0, 1, 0),0.2f,0.5f,0.2f, (float)0.02, 50);
						loc.add(x,y,z);
						Bukkit.getScheduler().scheduleSyncDelayedTask(ArmorEditor.plugin, new Runnable()
						{
							@Override
							public void run()
							{
								try
								{
									ParticleEffect.sendToLocation(ParticleEffect.TOWN_AURA, loc.clone().add(0, 1, 0),0.2f,0.5f,0.2f, (float)0.02, 50);
									loc.add(x,y,z);
									Bukkit.getScheduler().scheduleSyncDelayedTask(ArmorEditor.plugin, new Runnable()
									{
										@Override
										public void run()
										{
											try
											{
												ParticleEffect.sendToLocation(ParticleEffect.TOWN_AURA, loc.clone().add(0, 1, 0),0.2f,0.5f,0.2f, (float)0.02, 50);
												loc.add(x,y,z);
												Bukkit.getScheduler().scheduleSyncDelayedTask(ArmorEditor.plugin, new Runnable()
												{
													@Override
													public void run()
													{
														try
														{
															ParticleEffect.sendToLocation(ParticleEffect.TOWN_AURA, loc.clone().add(0, 1, 0),0.2f,0.5f,0.2f, (float)0.02, 50);
															loc.add(x,y,z);
															Bukkit.getScheduler().scheduleSyncDelayedTask(ArmorEditor.plugin, new Runnable()
															{
																@Override
																public void run()
																{
																	try
																	{
																		ParticleEffect.sendToLocation(ParticleEffect.TOWN_AURA, loc.clone().add(0, 1, 0),0.2f,0.5f,0.2f, (float)0.02, 50);
																		loc.add(x,y,z);
																		Bukkit.getScheduler().scheduleSyncDelayedTask(ArmorEditor.plugin, new Runnable()
																		{
																			@Override
																			public void run()
																			{
																				try
																				{
																					ParticleEffect.sendToLocation(ParticleEffect.TOWN_AURA, loc.clone().add(0, 1, 0),0.2f,0.5f,0.2f, (float)0.02, 50);
																					loc.add(x,y,z);
																					Bukkit.getScheduler().scheduleSyncDelayedTask(ArmorEditor.plugin, new Runnable()
																					{
																						@Override
																						public void run()
																						{
																							try
																							{
																								ParticleEffect.sendToLocation(ParticleEffect.TOWN_AURA, loc.clone().add(0, 1, 0),0.2f,0.5f,0.2f, (float)0.02, 50);
																								loc.add(x,y,z);
																								Bukkit.getScheduler().scheduleSyncDelayedTask(ArmorEditor.plugin, new Runnable()
																								{
																									@Override
																									public void run()
																									{
																										try
																										{
																											ParticleEffect.sendToLocation(ParticleEffect.TOWN_AURA, loc.clone().add(0, 1, 0),0.2f,0.5f,0.2f, (float)0.02, 50);
																											loc.add(x,y,z);
																											Bukkit.getScheduler().scheduleSyncDelayedTask(ArmorEditor.plugin, new Runnable()
																											{
																												@Override
																												public void run()
																												{
																													try
																													{
																														ParticleEffect.sendToLocation(ParticleEffect.TOWN_AURA, target.getLocation().add(0, 1, 0),1f,1f,1f, (float)0.02, 200);
																														FireworkEffectPlayer fplayer = new FireworkEffectPlayer();
																														FireworkEffect fe = FireworkEffect.builder().with(Type.STAR).withColor(Color.BLACK).flicker(true).build();
																														try
																														{
																															fplayer.playFirework(target.getWorld(), target.getLocation(), fe);
																														}
																														catch(Exception exc)
																														{
																															try
																															{
																																fplayer.playFirework(target.getWorld(), target.getLocation(), fe);
																															}
																															catch(Exception exc2)
																															{
																																
																															}
																														}
																														PvpHandler.getPvpPlayer(target).uncheckedDamage(200 + PvpHandler.getPvpPlayer(target).gethealth()/2);
																														target.damage(0D);
																													}
																													catch(Exception exc)
																													{
																													}
																												}
																											},1L);
																										}
																										catch(Exception exc)
																										{
																										}
																									}
																								},1L);
																							}
																							catch(Exception exc)
																							{
																							}
																						}
																					},1L);
																				}
																				catch(Exception exc)
																				{
																				}
																			}
																		},1L);
																	}
																	catch(Exception exc)
																	{
																	}
																}
															},1L);
														}
														catch(Exception exc)
														{
														}
													}
												},1L);
											}
											catch(Exception exc)
											{
											}
										}
									},1L);
								}
								catch(Exception exc)
								{
								}
							}
						},1L);
					}
					catch(Exception exc)
					{
					}
				}
			},1L);
		}
		catch(Exception exc)
		{
		}
		return true;
	}
	
	public String getName()
	{
		return "Execution";
	}
}
