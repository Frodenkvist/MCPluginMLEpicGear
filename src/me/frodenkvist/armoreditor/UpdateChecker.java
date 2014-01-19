package me.frodenkvist.armoreditor;

import java.io.InputStream;
import java.net.URL;

import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class UpdateChecker
{
	private ArmorEditor plugin;
	private URL fileFeed;
	
	private String version;
	private String link;
	
	public UpdateChecker(ArmorEditor instance, String url)
	{
		plugin = instance;
		try
		{
			fileFeed = new URL(url);
		}
		catch(Exception e)
		{
		}
	}
	
	public boolean isUpdateNeeded()
	{
		try
		{
			//
		}
		catch(Exception e)
		{
		}
		
		return false;
	}
	
	public String getVersion()
	{
		return version;
	}
	
	public String getLink()
	{
		return link;
	}
}
