package HBans.Comandos;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import HBans.Main;
import HBans.EventApi.APIGeral;

public class UnbanC implements CommandExecutor {

	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!sender.hasPermission("hbans.unban")) {
			sender.sendMessage(Main.m.getConfig().getString("Prefix").replace("&", "§") + " "
					+ Main.m.getConfig().getString("SemPermissao"));
			return true;
		}

		if (args.length >= 1) {
			OfflinePlayer toff = Bukkit.getOfflinePlayer(args[0]);
			APIGeral.Unban(toff.getName(), sender);
		}
		return false;

	}

}
