package HBans.Comandos;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import HBans.Main;
import HBans.EventApi.APIGeral;

public class ReportarC implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender instanceof Player) {
			Player p = (Player) sender;
			if (label.equalsIgnoreCase("reportar")) {
				if (!(args.length >= 2)) {
					sender.sendMessage(Main.m.getConfig().getString("Prefix").replace("&", "§") + " "
							+ Main.m.getConfig().getString("ReportarI").replace("&", "§"));
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

						Date now = new Date();
						SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
						
						APIGeral.Reportar(p, t, allArgs, format.format(now));
					} else {
						sender.sendMessage(Main.m.getConfig().getString("Prefix").replace("&", "§") + " "
								+ Main.m.getConfig().getString("PlayerOff").replace("&", "§"));
						return true;
					}
					return true;
				} else {
					sender.sendMessage(
							Main.m.getConfig().getString(Main.m.getConfig().getString("Prefix").replace("&", "§") + " "
									+ Main.m.getConfig().getString("ReportI").replace("&", "§")));
				}
			}
		}
		return false;
	}
}