package me.frodenkvist.armoreditor;

import org.bukkit.entity.Player;

public abstract class Skill
{
	private int cost;
	
	public Skill(int cost)
	{
		this.cost = cost;
	}
	
	public int getCost()
	{
		return cost;
	}
	
	public abstract boolean run(Player caster);
	
	public abstract String getName();
}
