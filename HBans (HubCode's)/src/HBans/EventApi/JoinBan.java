package HBans.EventApi;

import java.util.List;

import org.bukkit.Bukkit;
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
		String p = e.getPlayer().getName();
		if (APIGeral.CheckBan(p) == true) {
			if (Main.m.getConfig().getBoolean("MySQL.Ativado") == true) {
				List<String> messagesbanlogin = Main.m.getConfig().getStringList("BanLogin");
				String messagebanlogin = "";
				for (String mbanlogin : messagesbanlogin) {
					messagebanlogin += mbanlogin.replace("&", "§").replace("%m", Data.getBanMotivo(p))
							.replace("%sender", Data.getBanStaff(p));
					messagebanlogin += "\n";
				}
				e.getPlayer().kickPlayer(messagebanlogin);
			} else {
				List<String> messagesbanlogin = Main.m.getConfig().getStringList("BanLogin");
				String messagebanlogin = "";
				for (String mbanlogin : messagesbanlogin) {
					messagebanlogin += mbanlogin.replace("&", "§")
							.replace("%m", BanConfig.fc.getString(p + ".motivo"))
							.replace("%sender", BanConfig.fc.getString(p + ".Staff"));
					messagebanlogin += "\n";
				}
				e.getPlayer().kickPlayer(messagebanlogin);
			}
		}
	}

	@EventHandler
	public void entrar5(PlayerJoinEvent e) {
		Player p = Bukkit.getPlayer(e.getPlayer().getName());
		String ip = e.getPlayer().getAddress().getHostName().replace(".", "-");
		if (APIGeral.CheckIpBan(ip) == true) {
			if (Main.m.getConfig().getBoolean("MySQL.Ativado") == true) {

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
		String p = e.getPlayer().getName();
		if (APIGeral.CheckTempBan(p) == true) {
			if (Main.m.getConfig().getBoolean("MySQL.Ativado") == true) {
				long endOfBan = Data.getTempBanTempo(p);
				long now = System.currentTimeMillis();
				long diff = endOfBan - now;
				if (diff <= 0) {
					Data.tempunban(p);
				} else {
					List<String> messagesbanlogin = Main.m.getConfig().getStringList("TempBanLogin");
					String messagebanlogin = "";
					for (String mbanlogin : messagesbanlogin) {
						messagebanlogin += mbanlogin.replace("&", "§").replace("%b", p)
								.replace("%m", Data.getTempBanMotivo(p))
								.replace("%t", TimeAPI.getMSG(Data.getTempBanTempo(p)).replace("%sender",
										Data.getTempBanStaff(p)));
						messagebanlogin += "\n";
					}
					e.getPlayer().kickPlayer(messagebanlogin);
				}
			} else {
				long endOfBan = Long.valueOf(TempBanConfig.fc.getString(p + ".Tempo"));
				long now = System.currentTimeMillis();
				long diff = endOfBan - now;
				if (diff <= 0) {
					TempBanConfig.fc.set(p + ".banido", false);
					TempBanConfig.SaveConfig();
				} else {
					List<String> messagesbanlogin = Main.m.getConfig().getStringList("TempBanLogin");
					String messagebanlogin = "";
					for (String mbanlogin : messagesbanlogin) {
						messagebanlogin += mbanlogin.replace("&", "§").replace("%b", p)
								.replace("%m", TempBanConfig.fc.getString(p + ".Motivo")).replace("%t",
										TimeAPI.getMSG(Long.valueOf(TempBanConfig.fc.getString(p + ".Tempo"))))
												.replace("%sender",TempBanConfig.fc.getString(p + ".Staff"));
						messagebanlogin += "\n";
					}
					e.getPlayer().kickPlayer(messagebanlogin);
				}
			}
		}
	}
	
	@EventHandler
	public void dupeip(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		if (!APIGeral.getIp(p.getAddress().getHostName().replace(".", "-")).contains(p.getName())) {
			APIGeral.addIp(p.getAddress().getHostName().replace(".", "-"), p.getName());
		}
	}
}
