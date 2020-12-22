package HBans.Comandos;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import HBans.Main;
import HBans.EventApi.APIGeral;

public class KickC implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (label.equalsIgnoreCase("kick")) {
			if (!sender.hasPermission("hbans.kick")) {
				sender.sendMessage(Main.m.getConfig().getString("Prefix").replace("&", "§") + " "
						+ Main.m.getConfig().getString("SemPermissao").replace("&", "§"));
				return true;
			}

			if (args.length == 0) {
				sender.sendMessage(Main.m.getConfig().getString("Prefix").replace("&", "§") + " "
						+ Main.m.getConfig().getString("KickI").replace("&", "§"));
				return true;
			}
			StringBuilder sb = new StringBuilder();
			for (int i = 1; i < args.length; i++) {
				sb.append(args[i]).append(" ");
			}

			String allArgs = sb.toString().trim();
			Player t = Bukkit.getPlayer(args[0]);
			if (args.length >= 1) {
				if (t != null) {
					
					if (t.hasPermission("hbans.Imune.Kick")){
						sender.sendMessage(Main.m.getConfig().getString("Prefix").replace("&", "§") + " "
								+ Main.m.getConfig().getString("PlayerImune").replace("&", "§"));
						return true;
					}
					
					APIGeral.Kick(sender.getName(), t, allArgs);
				}
				return true;
			} else {
				sender.sendMessage(Main.m.getConfig().getString("Prefix").replace("&", "§")
						+ " " + Main.m.getConfig().getString("KickI").replace("&", "§"));
			}
		}
		return false;
	}

}
