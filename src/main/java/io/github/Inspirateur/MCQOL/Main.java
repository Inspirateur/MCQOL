package io.github.Inspirateur.MCQOL;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.weather.WeatherChangeEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class Main extends JavaPlugin implements Plugin, Listener, TabCompleter {
	private boolean rain = true;

	@Override
	public void onEnable() {
		Bukkit.getPluginManager().registerEvents(this, this);
		getLogger().info("MCQOL Ready");
	}

	@EventHandler
	public void onWeatherChange(WeatherChangeEvent event) {
		if (!rain) {
			event.setCancelled(event.toWeatherState());
		}
	}

	@Override
	public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, String label, String[] args) {
		switch (label) {
			case "suicide" -> suicide(sender);
			case "rain" -> rain(sender, args);
		}
		return true;
	}

	@Override
	public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, String[] args) {
		if ("rain".equals(label)) {
			return Arrays.asList("true", "false");
		}
		return new ArrayList<>();
	}

	public void rain(CommandSender sender, String[] args) {
		if(args.length == 0) {
			sender.sendMessage("You must specify true or false, like /rain false");
			return;
		}
		try {
			if (sender.isOp()) {
				rain = Boolean.parseBoolean(args[0]);
				for(Player player: Bukkit.getOnlinePlayers()) {
					player.sendMessage(String.format("Rain is set to %b", rain));
				}
			}
		} catch (NumberFormatException e) {
			sender.sendMessage(String.format("%s is not a valid boolean", args[0]));
		}
	}

	public void suicide(CommandSender sender) {
		Player player = (Player) sender;
		if(player != null) {
			player.setHealth(0.);
		}
	}

	@EventHandler
	public void onEntityExplode(EntityExplodeEvent event) {
		event.setYield(100f);
	}
}
