package HBans.EventApi;

import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import HBans.Main;
import HBans.Config.BanConfig;
import HBans.Config.IpBanConfig;
import HBans.Config.TempBanConfig;
import HBans.Mysql.Data;

public class JoinBan implements Listener {

	@EventHandler
	public void entrar(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		if (APIGeral.CheckBan(p.getName()) == true) {
			if (Main.m.getConfig().getBoolean("MySQL.ativado") == true) {
				List<String> messagesbanlogin = Main.m.getConfig().getStringList("BanLogin");
				String messagebanlogin = "";
				for (String mbanlogin : messagesbanlogin) {
					messagebanlogin += mbanlogin.replace("&", "§").replace("%m", Data.getBanMotivo(p.getName()))
							.replace("%sender", Data.getBanStaff(p.getName()));
					messagebanlogin += "\n";
				}
				e.getPlayer().kickPlayer(messagebanlogin);
			} else {
				List<String> messagesbanlogin = Main.m.getConfig().getStringList("BanLogin");
				String messagebanlogin = "";
				for (String mbanlogin : messagesbanlogin) {
					messagebanlogin += mbanlogin.replace("&", "§")
							.replace("%m", BanConfig.fc.getString(p.getName() + ".motivo"))
							.replace("%sender", BanConfig.fc.getString(p.getName() + ".Staff"));
					messagebanlogin += "\n";
				}
				e.getPlayer().kickPlayer(messagebanlogin);
			}
		}
	}

	@EventHandler
	public void entrar5(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		if (APIGeral.CheckIpBan(p) == true) {
			if (Main.m.getConfig().getBoolean("MySQL.ativado") == true) {

				List<String> messagesbanlogin = Main.m.getConfig().getStringList("IpBanLogin");
				String messagebanlogin = "";
				for (String mbanlogin : messagesbanlogin) {
					messagebanlogin += mbanlogin.replace("&", "§")
							.replace("%m", Data.getipBanMotivo(p.getAddress().getHostName()))
							.replace("%sender", Data.getipBanStaff(p.getAddress().getHostName()));
					messagebanlogin += "\n";
				}
				e.getPlayer().kickPlayer(messagebanlogin);

			} else {
				List<String> messagesbanlogin = Main.m.getConfig().getStringList("IpBanLogin");
				String messagebanlogin = "";
				for (String mbanlogin : messagesbanlogin) {
					messagebanlogin += mbanlogin.replace("&", "§")
							.replace("%m",
									IpBanConfig.fc
											.getString(p.getAddress().getHostName().replace(".", "-") + ".motivo"))
							.replace("%sender", IpBanConfig.fc
									.getString(p.getAddress().getHostName().replace(".", "-") + ".Staff"));
					messagebanlogin += "\n";
				}
				e.getPlayer().kickPlayer(messagebanlogin);
			}
		}
	}

	@EventHandler
	public void entrar2(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		if (APIGeral.CheckTempBan(p.getName()) == true) {
			if (Main.m.getConfig().getBoolean("MySQL.ativado") == true) {
				long endOfBan = Data.getTempBanTempo(p.getName());
				long now = System.currentTimeMillis();
				long diff = endOfBan - now;
				if (diff <= 0) {
					Data.tempunban(p.getName());
				} else {
					List<String> messagesbanlogin = Main.m.getConfig().getStringList("TempBanLogin");
					String messagebanlogin = "";
					for (String mbanlogin : messagesbanlogin) {
						messagebanlogin += mbanlogin.replace("&", "§").replace("%b", p.getName())
								.replace("%m", Data.getTempBanMotivo(p.getName()))
								.replace("%t", TimeAPI.getMSG(Data.getTempBanTempo(p.getName())).replace("%sender",
										Data.getTempBanStaff(p.getName())));
						messagebanlogin += "\n";
					}
					e.getPlayer().kickPlayer(messagebanlogin);
				}
			} else {
				long endOfBan = Long.valueOf(TempBanConfig.fc.getString(p.getName() + ".Tempo"));
				long now = System.currentTimeMillis();
				long diff = endOfBan - now;
				if (diff <= 0) {
					TempBanConfig.fc.set(p.getName() + ".banido", false);
					TempBanConfig.SaveConfig();
				} else {
					List<String> messagesbanlogin = Main.m.getConfig().getStringList("TempBanLogin");
					String messagebanlogin = "";
					for (String mbanlogin : messagesbanlogin) {
						messagebanlogin += mbanlogin.replace("&", "§").replace("%b", p.getName())
								.replace("%m", TempBanConfig.fc.getString(p.getName() + ".Motivo")).replace("%t",
										TimeAPI.getMSG(Long.valueOf(TempBanConfig.fc.getString(p.getName() + ".Tempo"))))
												.replace("%sender",TempBanConfig.fc.getString(p.getName() + ".Staff"));
						messagebanlogin += "\n";
					}
					e.getPlayer().kickPlayer(messagebanlogin);
				}
			}
		}
	}
}
