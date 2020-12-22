package HBans.Comandos;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import HBans.Main;
import HBans.EventApi.APIGeral;

public class DupeipC implements CommandExecutor {
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player)) {
			return true;
		}
		Player p = (Player) sender;
		if (label.equalsIgnoreCase("dupeip")) {
			if (!sender.hasPermission("hbans.dupeip")) {
				sender.sendMessage(Main.m.getConfig().getString("Prefix").replace("&", "§") + " "
						+ Main.m.getConfig().getString("SemPermissao"));
				return true;
			}
			if (args.length != 1) {
				sender.sendMessage(Main.m.getConfig().getString("Prefix").replace("&", "§") + " "
						+ Main.m.getConfig().getString("VerificarI").replace("&", "§"));
				return true;
			}
			Player t = Bukkit.getPlayer(args[0]);
			if (t == null) {
				sender.sendMessage(Main.m.getConfig().getString("Prefix").replace("&", "§") + " "
						+ Main.m.getConfig().getString("PlayerOff").replace("&", "§"));
				return true;
			}
			if (APIGeral.getIp(p.getName()) == null) {
				p.sendMessage(Main.m.getConfig().getString("PlayerOff").replace("&", "§"));
			} else {
				p.sendMessage("");
				p.sendMessage("§fLista de contas registradas com o ip §a" + t.getAddress().getHostName() + " §a[Online] §7[Offline] §c[Mutado] §4[Banido]");
				for (String ips : APIGeral.getIp(t.getAddress().getHostName().replace(".", "-"))) {
					p.sendMessage("§f• " + APIGeral.getColorDupeIp(ips, t.getAddress().getAddress().getHostAddress().toString()) + ips);
				}
				p.sendMessage("");
			}
		}
		return false;

	}
}
