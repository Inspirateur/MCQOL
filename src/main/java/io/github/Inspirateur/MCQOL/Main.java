package io.github.Inspirateur.MCQOL;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

public class Main extends JavaPlugin implements Plugin, Listener {
	private Pacifists pacifists;

	@Override
	public void onEnable() {
		pacifists = new Pacifists();
		Bukkit.getPluginManager().registerEvents(this, this);
		getLogger().info("MCQOL Ready");
	}

	@Override
	public void onDisable() {
		pacifists.save();
	}

	@Override
	public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, String label, String[] args) {
		switch (label) {
			case "suicide":
				suicide(sender);
				break;
			case "pacifist":
				pacifist(sender, args);
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

	public void pacifist(CommandSender sender, String[] args) {
		Player player = (Player) sender;
		if(player != null) {
			if(args.length == 0) {
				boolean isPacifist = pacifists.is(player.getUniqueId());
				player.sendMessage(String.format("Your pacifism is currently set to %b", isPacifist));
			} else {
				try {
					boolean isPacifist = Boolean.parseBoolean(args[0]);
					pacifists.set(player.getUniqueId(), isPacifist);
					player.sendMessage(String.format("Pacifist succesfully set to %b", isPacifist));
				} catch (RuntimeException e) {
					player.sendMessage(e.toString());
				}
			}
		}
	}

	@EventHandler
	public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
		// if a player is going to get hurt
		if (event.getEntity() instanceof Player) {
			Player damagee = (Player) event.getEntity();
			// check if the damager is another player
			Player damager = null;
			if (event.getDamager() instanceof Player) {
				damager = (Player) event.getDamager();
			} else if (event.getDamager() instanceof Projectile) {
				Projectile projectile = (Projectile)event.getDamager();
				if (projectile.getShooter() instanceof Player) {
					damager = (Player) projectile.getShooter();
				}
			}
			if (damager != null) {
				// if one of them are pacifists, cancel the event
				if (pacifists.is(damagee.getUniqueId()) || pacifists.is(damager.getUniqueId())) {
					event.setCancelled(true);
				}
			}
		}
	}

	@EventHandler
	public void onEntityExplode(EntityExplodeEvent event) {
		event.setYield(100f);
	}
}
