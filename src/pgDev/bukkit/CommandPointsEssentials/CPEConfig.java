package pgDev.bukkit.CommandPointsEssentials;

import java.awt.Color;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Properties;

public class CPEConfig {
	private Properties properties;
	private final CommandPointsEssentials plugin;
	public boolean upToDate = true;
	
	// List of Config Options
	HashMap<String, Integer> commandCosts = new HashMap<String, Integer>();
	boolean backAfterQuit = false;
	
	public CPEConfig(Properties p, final CommandPointsEssentials plugin) {
        properties = p;
        this.plugin = plugin;
        
        // Grab values here.
        commandCosts.put("ctp", getInt("ctp", 1));
        commandCosts.put("day", getInt("day", 1));
        commandCosts.put("night", getInt("night", 1));
        commandCosts.put("spawn", getInt("spawn", 1));
        commandCosts.put("bed", getInt("bed", 1));
        commandCosts.put("buyexp", getInt("buyexp", 1));
        commandCosts.put("back", getInt("back", 1));
        backAfterQuit = getBoolean("backAfterQuit", false);
        
	}
	
	// Value obtaining functions down below
	public int getInt(String label, int thedefault) {
		String value;
        try {
        	value = getString(label);
        	return Integer.parseInt(value);
        } catch (NoSuchElementException e) {
        	return thedefault;
        }
    }
    
    public double getDouble(String label) throws NoSuchElementException {
        String value = getString(label);
        return Double.parseDouble(value);
    }
    
    public File getFile(String label) throws NoSuchElementException {
        String value = getString(label);
        return new File(value);
    }

    public boolean getBoolean(String label, boolean thedefault) {
    	String values;
        try {
        	values = getString(label);
        	return Boolean.valueOf(values).booleanValue();
        } catch (NoSuchElementException e) {
        	return thedefault;
        }
    }
    
    public Color getColor(String label) {
        String value = getString(label);
        Color color = Color.decode(value);
        return color;
    }
    
    public HashSet<String> getSet(String label, String thedefault) {
        String values;
        try {
        	values = getString(label);
        } catch (NoSuchElementException e) {
        	values = thedefault;
        }
        String[] tokens = values.split(",");
        HashSet<String> set = new HashSet<String>();
        for (int i = 0; i < tokens.length; i++) {
            set.add(tokens[i].trim().toLowerCase());
        }
        return set;
    }
    
    public LinkedList<String> getList(String label, String thedefault) {
    	String values;
        try {
        	values = getString(label);
        } catch (NoSuchElementException e) {
        	values = thedefault;
        }
        if(!values.equals("")) {
            String[] tokens = values.split(",");
            LinkedList<String> set = new LinkedList<String>();
            for (int i = 0; i < tokens.length; i++) {
                set.add(tokens[i].trim().toLowerCase());
            }
            return set;
        }else {
        	return new LinkedList<String>();
        }
    }
    
    public String getString(String label) throws NoSuchElementException {
        String value = properties.getProperty(label);
        if (value == null) {
        	upToDate = false;
            throw new NoSuchElementException("Config did not contain: " + label);
        }
        return value;
    }
    
    public String getString(String label, String thedefault) {
    	String value;
    	try {
        	value = getString(label);
        } catch (NoSuchElementException e) {
        	value = thedefault;
        }
        return value;
    }
    
    public String linkedListToString(LinkedList<String> list) {
    	if(list.size() > 0) {
    		String compounded = "";
    		boolean first = true;
        	for (String value : list) {
        		if (first) {
        			compounded = value;
        			first = false;
        		} else {
        			compounded = compounded + "," + value;
        		}
        	}
        	return compounded;
    	}
    	return "";
    }
    
    
    // Config creation method
    public void createConfig() {
    	try{
    		BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(plugin.pluginConfigLocation)));
    		out.write("#\r\n");
    		out.write("# CommandPoints Essentials Configuration\r\n");
    		out.write("#\r\n");
    		out.write("\r\n");
    		out.write("# Command Costs\r\n");
    		out.write("#	Here you set the amount of commandpoints needed\r\n");
    		out.write("#	to run specific commands.\r\n");
    		out.write("ctp=" + commandCosts.get("ctp") + "\r\n");
    		out.write("day=" + commandCosts.get("day") + "\r\n");
    		out.write("night=" + commandCosts.get("night") + "\r\n");
    		out.write("spawn=" + commandCosts.get("spawn") + "\r\n");
    		out.write("bed=" + commandCosts.get("bed") + "\r\n");
    		out.write("buyexp=" + commandCosts.get("buyexp") + "\r\n");
    		out.write("back=" + commandCosts.get("back") + "\r\n");
    		out.write("\r\n");
    		out.write("# Using \"/back\" after quit\r\n");
    		out.write("#	Here you specify whether or not a player may teleport\r\n");
    		out.write("#	to his last death location after he had quit.\r\n");
    		out.write("backAfterQuit=" + backAfterQuit + "\r\n");
    		out.close();
    	} catch (Exception e) {
    		System.out.println(e);
    	}
    }
}
