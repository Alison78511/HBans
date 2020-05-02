package HBans.Comandos;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import HBans.Main;
import HBans.EventApi.APIGeral;

public class MuteC implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (label.equalsIgnoreCase("mute") || label.equalsIgnoreCase("mutar")) {
			if (!sender.hasPermission("HBans.mute")) {
				sender.sendMessage(Main.m.getConfig().getString("Prefix").replace("&", "§") + " "
						+ Main.m.getConfig().getString("SemPermissao"));
				return true;
			}

			if (!(args.length >= 2)) {
				sender.sendMessage(Main.m.getConfig().getString("Prefix").replace("&", "§") + " "
						+ Main.m.getConfig().getString("MuteI").replace("&", "§"));
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
					if (APIGeral.CheckMute(t) == true) {
						sender.sendMessage(Main.m.getConfig().getString("Prefix").replace("&", "§") + " "
								+ Main.m.getConfig().getString("JaMute").replace("&", "§"));
						return true;
					}
					
					if (t.hasPermission("HBans.Imune.Mute")){
						sender.sendMessage(Main.m.getConfig().getString("Prefix").replace("&", "§") + " "
								+ Main.m.getConfig().getString("PlayerImune").replace("&", "§"));
						return true;
					}

					APIGeral.Mute(sender.getName(), t, allArgs);
				}
				return true;
			} else {
				sender.sendMessage(Main.m.getConfig().getString(Main.m.getConfig().getString("Prefix").replace("&", "§")
						+ " " + Main.m.getConfig().getString("MuteI").replace("&", "§")));
			}
		}
		return false;
	}
}