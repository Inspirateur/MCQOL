package io.github.Inspirateur.MCQOL;

import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Plugin, Listener {
	@Override
	public void onEnable() {
		getLogger().info("MCQOL Ready");
	}
}
