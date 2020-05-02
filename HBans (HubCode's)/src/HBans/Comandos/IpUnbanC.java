package HBans.Comandos;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import HBans.Main;
import HBans.Config.IpBanConfig;
import HBans.Config.IpBanConfig2;

public class IpUnbanC implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!sender.hasPermission("HBans.ipunban")) {
			sender.sendMessage(Main.m.getConfig().getString("Prefix").replace("&", "§") + " "
					+ Main.m.getConfig().getString("SemPermissao"));
			return true;
		}

		if (args.length >= 1) {
			String t2 = args[0];
			String t = args[0].replace(".", "-");
			String ipplayer = IpBanConfig2.fc.getString(t2 + ".Ip");
			if (IpBanConfig.fc.getBoolean(ipplayer.replace(".", "-") + ".banido") == true || IpBanConfig.fc.getBoolean(t + ".banido") == true)  {
				sender.sendMessage(Main.m.getConfig().getString("IpUnban").replace("&", "§").replace("%b", t2));
				if (args[0].contains(".")){
					IpBanConfig.fc.set(t + ".banido", false);
					IpBanConfig.SaveConfig();
				}else{
					IpBanConfig.fc.set(ipplayer.replace(".", "-") + ".banido", false);
					IpBanConfig.SaveConfig();
				}
			} else {
				sender.sendMessage(Main.m.getConfig().getString("Prefix").replace("&", "§") + " "
						+ Main.m.getConfig().getString("JaUnban").replace("&", "§"));
			}
		} else {
			sender.sendMessage(Main.m.getConfig().getString("Prefix").replace("&", "§") + " "
					+ Main.m.getConfig().getString("IpUnbanI").replace("&", "§"));
		}
		return false;

	}

}
