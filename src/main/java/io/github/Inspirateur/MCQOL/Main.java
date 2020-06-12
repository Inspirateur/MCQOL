package io.github.Inspirateur.MCQOL;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

public class Main extends JavaPlugin implements Plugin, Listener {
	@Override
	public void onEnable() {
		Bukkit.getPluginManager().registerEvents(this, this);
		getLogger().info("MCQOL Ready");
	}

	@Override
	public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, String label, String[] args) {
		//noinspection SwitchStatementWithTooFewBranches
		switch (label) {
			case "suicide":
				suicide(sender);
				break;
		}
		return true;
	}

	public void suicide(CommandSender sender) {
		Player player = (Player) sender;
		if(player != null) {
			player.setHealth(0.);
		}
	}

	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent event) {
		event.setKeepLevel(true);
		event.setDroppedExp(0);
	}

	@EventHandler
	public void onEntityExplode(EntityExplodeEvent event) {
		event.setYield(100f);
	}
}
