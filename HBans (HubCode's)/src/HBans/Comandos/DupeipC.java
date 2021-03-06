package HBans.Comandos;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import HBans.Main;
import HBans.Config.IpConfig;
import HBans.EventApi.APIGeral;

public class DupeipC implements CommandExecutor {
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player)) {
			return true;
		}
		Player p = (Player) sender;
		if (label.equalsIgnoreCase("dupeip")) {
			if (!sender.hasPermission("hbans.dupeip")) {
				sender.sendMessage(Main.m.getConfig().getString("Prefix").replace("&", "�") + " "
						+ Main.m.getConfig().getString("SemPermissao"));
				return true;
			}
			if (args.length != 1) {
				sender.sendMessage(Main.m.getConfig().getString("Prefix").replace("&", "�") + " "
						+ Main.m.getConfig().getString("DupeIpI").replace("&", "�"));
				return true;
			}
			if (!IpConfig.fc.contains(args[0])) {
				p.sendMessage(Main.m.getConfig().getString("PlayerI").replace("&", "�"));
				return true;
			}

			Player t = Bukkit.getPlayer(args[0]);
			if (t == null) {
				String toff = args[0];
				String ip = APIGeral.getPlayerIp(toff);
				if (APIGeral.getIp(ip.replace(".", "-")) == null) {
					p.sendMessage(Main.m.getConfig().getString("PlayerI").replace("&", "�"));
				} else {
					p.sendMessage("");
					p.sendMessage("�fLista de contas registradas com o ip �a" + ip.replace("-", "."));
					p.sendMessage("�a[Online] �7[Offline] �c[Mutado] �4[Banido]");
					for (String ips : APIGeral.getIp(ip.replace(".", "-"))) {
						p.sendMessage("�f� " + APIGeral.getColorDupeIp(ips, ip.replace("-", ".")) + ips);
					}
					p.sendMessage("");
				}
			} else {
				if (APIGeral.getIp(t.getAddress().getAddress().getHostAddress().replace(".", "-")) == null) {
					p.sendMessage(Main.m.getConfig().getString("PlayerI").replace("&", "�"));
				} else {
					p.sendMessage("");
					p.sendMessage(
							"�fLista de contas registradas com o ip �a" + t.getAddress().getAddress().getHostAddress());
					p.sendMessage("�a[Online] �7[Offline] �c[Mutado] �4[Banido]");
					for (String ips : APIGeral.getIp(t.getAddress().getAddress().getHostAddress().replace(".", "-"))) {
						p.sendMessage("�f� "
								+ APIGeral.getColorDupeIp(ips, t.getAddress().getAddress().getHostAddress().toString())
								+ ips);
					}
					p.sendMessage("");
				}
			}
		}
		return false;

	}
}
