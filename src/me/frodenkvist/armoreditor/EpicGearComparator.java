package me.frodenkvist.armoreditor;

import java.util.Comparator;

public class EpicGearComparator implements Comparator<EpicGear>
{

	@Override
	public int compare(EpicGear obj1, EpicGear obj2)
	{
		return obj1.getName().compareTo(obj2.getName());
	}
	
}
