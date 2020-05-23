package HBans.Comandos;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import HBans.Main;
import HBans.EventApi.APIGeral;

public class BanC implements CommandExecutor {

	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (label.equalsIgnoreCase("ban")) {
			if (!sender.hasPermission("HBans.ban")) {
				sender.sendMessage(Main.m.getConfig().getString("Prefix").replace("&", "§") + " "
						+ Main.m.getConfig().getString("SemPermissao").replace("&", "§"));
				return true;
			}
			if (args.length >= 2) {

				StringBuilder sb = new StringBuilder();
				for (int i = 1; i < args.length; i++) {
					sb.append(args[i]).append(" ");
				}

				String allArgs = sb.toString().trim();
				Player targeton = Bukkit.getPlayer(args[0]);
				OfflinePlayer targetoff = Bukkit.getOfflinePlayer(args[0]);
				if (targeton != null) {
					if (APIGeral.CheckBan(targeton.getName()) == true) {
						sender.sendMessage(Main.m.getConfig().getString("Prefix").replace("&", "§") + " "
								+ Main.m.getConfig().getString("JaBanido").replace("&", "§"));
						return true;
					}
					if (targeton.hasPermission("HBans.Imune.Ban")){
						sender.sendMessage(Main.m.getConfig().getString("Prefix").replace("&", "§") + " "
								+ Main.m.getConfig().getString("PlayerImune").replace("&", "§"));
						return true;
					}

					APIGeral.Ban(sender.getName(), targeton, allArgs, true, targeton.getName());
					APIGeral.addBan();

				} else {
					if (APIGeral.CheckBan(targetoff.getName()) == true) {
						sender.sendMessage(Main.m.getConfig().getString("Prefix").replace("&", "§") + " "
								+ Main.m.getConfig().getString("JaBanido").replace("&", "§"));
						return true;
					}

					APIGeral.Ban(sender.getName(), targetoff.getPlayer(), allArgs, false, args[0]);
					APIGeral.addBan();

				}
				return true;

			} else {
				sender.sendMessage((Main.m.getConfig().getString("Prefix").replace("&", "§") + " "
						+ Main.m.getConfig().getString("BanI").replace("&", "§")));
			}
		}

		return false;
	}

}
