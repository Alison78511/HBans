package HBans.EventApi;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import HBans.Main;
import HBans.Config.BanConfig;
import HBans.Config.IpBanConfig;
import HBans.Config.IpBanConfig2;
import HBans.Config.MotivoReportConfig;
import HBans.Config.MuteConfig;
import HBans.Config.ReportesConfig;
import HBans.Config.TempBanConfig;
import HBans.Config.TempMuteConfig;
import HBans.Config.WarnConfig;
import HBans.Mysql.Data;
import fr.galaxyoyo.spigot.twitterapi.TwitterAPI;
import net.dv8tion.jda.core.EmbedBuilder;

public class APIGeral implements Listener {

	public static void Ban(String Staff, Player p, String Motivo, boolean Online, String Player) {

		// DataBase
		if (Main.m.getConfig().getBoolean("MySQL.ativado") == true) {
			Data.ban(Player, Staff, Motivo);
		} else {
			BanConfig.fc.set(Player + ".motivo", Motivo);
			BanConfig.fc.set(Player + ".Staff", Staff);
			BanConfig.SaveConfig();
		}
		// DataBase

		// Broadcast
		List<String> messagesbanon = Main.m.getConfig().getStringList("Ban");
		String messagebanon = "";
		for (String mbanon : messagesbanon) {
			messagebanon += mbanon.replace("&", "§").replace("%b", Player).replace("%m", Motivo).replace("%sender",
					Staff);
			messagebanon += "\n";
		}
		Bukkit.broadcastMessage(messagebanon);
		// Broadcast

		// Twitter
		if (Main.m.getConfig().getBoolean("Twitter") == true) {
			List<String> messagesbant = Main.m.getConfig().getStringList("BanT");
			String messagebant = "";
			for (String mbant : messagesbant) {
				messagebant += mbant.replace("&", "§").replace("%b", Player).replace("%m", Motivo).replace("%sender",
						Staff);
				messagebant += "\n";
			}
			TwitterAPI.instance().tweet(messagebant);
		}
		// Twitter

		// Discord
		if (Main.m.getConfig().getBoolean("Discord") == true) {
			List<String> messagesband = Main.m.getConfig().getStringList("BanD.Mensagem");
			String messageband = "";
			for (String mband : messagesband) {
				messageband += mband.replace("&", "§").replace("%b", Player).replace("%m", Motivo).replace("%sender",
						Staff);
				messageband += "\n";
			}
			EmbedBuilder embed = new EmbedBuilder();
			embed.setColor(java.awt.Color.red);
			embed.setTitle(Main.m.getConfig().getString("BanD.Titulo"));
			embed.setDescription(messageband);
			if (Main.m.getConfig().getBoolean("BanD.Imagem") == true) {
				embed.setThumbnail(Main.m.getConfig().getString("BanD.ImagemLink"));
			}
			embed.setFooter(Main.m.getConfig().getString("BanD.Footer"),
					Main.m.getConfig().getString("BanD.ImagemFooter"));
			Main.jda.getTextChannelById(Main.m.getConfig().getString("Bot.Canal")).sendMessage(embed.build()).queue();
		}
		// Discord

		// KickPlayer
		if (Online == true) {
			List<String> messagesbanlogin = Main.m.getConfig().getStringList("BanLogin");
			String messagebanlogin = "";
			for (String mbanlogin : messagesbanlogin) {
				messagebanlogin += mbanlogin.replace("&", "§").replace("%m", Motivo).replace("%sender", Staff);
				messagebanlogin += "\n";
			}
			p.kickPlayer(messagebanlogin);
		}
		// KickPlayer
	}

	public static boolean CheckBan(String Player) {
		if (Main.m.getConfig().getBoolean("MySQL.ativado") == true) {
			if (Data.checkban(Player)) {
				return true;
			} else {
				return false;
			}
		} else {
			if (BanConfig.fc.contains(Player)) {
				return true;
			} else {
				return false;
			}
		}
	}

	public static void IpBan(String Staff, String Ip, String Motivo, Player p) {

		String Player = p.getName();

		// DataBase
		if (Main.m.getConfig().getBoolean("MySQL.ativado") == true) {
			Data.ipban(Ip, Player, Staff, Motivo);
		} else {
			IpBanConfig.fc.set(Ip.replace(".", "-") + ".motivo", Motivo);
			IpBanConfig.fc.set(Ip.replace(".", "-") + ".Staff", Staff);
			IpBanConfig2.fc.set(Player + ".Ip", Ip.replace("-", "."));
			IpBanConfig2.SaveConfig();
		}
		// DataBase

		// Broadcast
		List<String> messagesbanon = Main.m.getConfig().getStringList("IpBan");
		String messagebanon = "";
		for (String mbanon : messagesbanon) {
			messagebanon += mbanon.replace("&", "§").replace("%b", Player).replace("%m", Motivo).replace("%sender",
					Staff);
			messagebanon += "\n";
		}
		Bukkit.broadcastMessage(messagebanon);
		// Broadcast

		// Twitter
		if (Main.m.getConfig().getBoolean("Twitter") == true) {
			List<String> messagesIpBanT = Main.m.getConfig().getStringList("IpBanT");
			String messageIpBanT = "";
			for (String mIpBanT : messagesIpBanT) {
				messageIpBanT += mIpBanT.replace("&", "§").replace("%b", Player).replace("%m", Motivo)
						.replace("%sender", Staff);
				messageIpBanT += "\n";
			}
			TwitterAPI.instance().tweet(messageIpBanT);
		}
		// Twitter

		// Discord
		if (Main.m.getConfig().getBoolean("Discord") == true) {
			List<String> messagesIpBanD = Main.m.getConfig().getStringList("IpBanD.Mensagem");
			String messageIpBanD = "";
			for (String mIpBanD : messagesIpBanD) {
				messageIpBanD += mIpBanD.replace("&", "§").replace("%b", Player).replace("%m", Motivo)
						.replace("%sender", Staff);
				messageIpBanD += "\n";
			}
			EmbedBuilder embed = new EmbedBuilder();
			embed.setColor(java.awt.Color.red);
			embed.setTitle(Main.m.getConfig().getString("IpBanD.Titulo"));
			embed.setDescription(messageIpBanD);
			if (Main.m.getConfig().getBoolean("IpBanD.Imagem") == true) {
				embed.setThumbnail(Main.m.getConfig().getString("IpBanD.ImagemLink"));
			}
			embed.setFooter(Main.m.getConfig().getString("IpBanD.Footer"),
					Main.m.getConfig().getString("IpBanD.ImagemFooter"));
			Main.jda.getTextChannelById(Main.m.getConfig().getString("Bot.Canal")).sendMessage(embed.build()).queue();
		}
		// Discord

		// KickPlayer
		List<String> messagesIpBanLogin = Main.m.getConfig().getStringList("IpBanLogin");
		String messageIpBanLogin = "";
		for (String mIpBanLogin : messagesIpBanLogin) {
			messageIpBanLogin += mIpBanLogin.replace("&", "§").replace("%b", Player).replace("%m", Motivo)
					.replace("%sender", Staff);
			messageIpBanLogin += "\n";
		}
		p.kickPlayer(messageIpBanLogin);
		// KickPlayer
	}

	public static boolean CheckIpBan(Player p) {
		String Player = p.getName();
		if (Main.m.getConfig().getBoolean("MySQL.ativado") == true) {
			if (Data.checkipban(Player)) {
				return true;
			} else {
				return false;
			}
		} else {
			if (IpBanConfig.fc.contains(p.getAddress().getHostName().replace(".", "-"))) {
				return true;
			} else {
				return false;
			}
		}
	}

	public static void Kick(String Staff, Player p, String Motivo) {
		String Player = p.getName();

		// Kick
		List<String> messagesKick = Main.m.getConfig().getStringList("Kick");
		String messageKick = "";
		for (String mKick : messagesKick) {
			messageKick += mKick.replace("&", "§").replace("%b", Player).replace("%m", Motivo).replace("%sender",
					Staff);
			messageKick += "\n";
		}
		Bukkit.broadcastMessage(messageKick);
		// Kick

		// KickLogin
		List<String> messageskicklogin = Main.m.getConfig().getStringList("KickLogin");
		String messagekicklogin = "";
		for (String mkicklogin : messageskicklogin) {
			messagekicklogin += mkicklogin.replace("&", "§").replace("%b", Player).replace("%m", Motivo)
					.replace("%sender", Staff);
			messagekicklogin += "\n";
		}
		p.kickPlayer(messagekicklogin);
		// KickLogin
	}

	public static void Mute(String Staff, Player p, String Motivo) {
		String Player = p.getName();

		// DataBase
		if (Main.m.getConfig().getBoolean("MySQL.ativado") == true) {
			Data.mute(Player, Staff, Motivo);
		} else {
			MuteConfig.fc.set(Player + ".motivo", Motivo);
			MuteConfig.fc.set(Player + ".Staff", Staff);
			MuteConfig.Save();
		}
		// DataBase

		// Broadcast
		List<String> messagesmute = Main.m.getConfig().getStringList("Mute");
		String messagemute = "";
		for (String mmute : messagesmute) {
			messagemute += mmute.replace("&", "§").replace("%b", Player).replace("%m", Motivo).replace("%sender",
					Staff);
			messagemute += "\n";
		}
		Bukkit.broadcastMessage(messagemute);
		// Broadcast

		// PlayerMessage
		List<String> messagesMuteChat = Main.m.getConfig().getStringList("MuteChat");
		String messageMuteChat = "";
		for (String mMuteChat : messagesMuteChat) {
			messageMuteChat += mMuteChat.replace("&", "§").replace("%b", Player).replace("%m", Motivo)
					.replace("%sender", Staff);
			messageMuteChat += "\n";
		}
		p.sendMessage(messageMuteChat);
		// PlayerMessage

		// Twitter
		if (Main.m.getConfig().getBoolean("Twitter") == true) {
			List<String> messagesMuteT = Main.m.getConfig().getStringList("MuteT");
			String messageMuteT = "";
			for (String mMuteT : messagesMuteT) {
				messageMuteT += mMuteT.replace("&", "§").replace("%b", Player).replace("%m", Motivo).replace("%sender",
						Staff);
				messageMuteT += "\n";
			}
			TwitterAPI.instance().tweet(messageMuteT);
		}
		// Twitter

		// Discord
		if (Main.m.getConfig().getBoolean("Discord") == true) {
			List<String> messagesMuteD = Main.m.getConfig().getStringList("MuteD.Mensagem");
			String messageMuteD = "";
			for (String mMuteD : messagesMuteD) {
				messageMuteD += mMuteD.replace("&", "§").replace("%b", Player).replace("%m", Motivo).replace("%sender",
						Staff);
				messageMuteD += "\n";
			}
			EmbedBuilder embed = new EmbedBuilder();
			embed.setColor(java.awt.Color.red);
			embed.setTitle(Main.m.getConfig().getString("MuteD.Titulo"));
			embed.setDescription(messageMuteD);
			if (Main.m.getConfig().getBoolean("MuteD.Imagem") == true) {
				embed.setThumbnail(Main.m.getConfig().getString("MuteD.ImagemLink"));
			}
			embed.setFooter(Main.m.getConfig().getString("MuteD.Footer"),
					Main.m.getConfig().getString("MuteD.ImagemFooter"));
			Main.jda.getTextChannelById(Main.m.getConfig().getString("Bot.Canal")).sendMessage(embed.build()).queue();
		}
		// Discord
	}

	public static boolean CheckMute(Player p) {
		String Player = p.getName();
		if (Main.m.getConfig().getBoolean("MySQL.ativado") == true) {
			if (Data.checkmute(Player)) {
				return true;
			} else {
				return false;
			}
		} else {
			if (MuteConfig.fc.contains(Player)) {
				return true;
			} else {
				return false;
			}
		}
	}

	public static void Reportar(Player Sender, Player Reportado, String Motivo, String DataT) {
		String Player = Reportado.getName();
		String Reporter = Sender.getName();

		// DataBase
		if (Main.m.getConfig().getBoolean("MySQL.ativado") == true) {
			Data.report(Player, Reporter, Motivo, DataT);
		} else {
			MotivoReportConfig.fc.set(Player + ".Reporter", Reporter);
			MotivoReportConfig.fc.set(Player + ".Motivo", Motivo);
			MotivoReportConfig.fc.set(Player + ".Data", DataT);
			MotivoReportConfig.SaveConfig();
			List<String> list = ReportesConfig.fc.getStringList("Reportados");
			list.add(Player);
			if (ReportesConfig.fc.getStringList("Reportados").contains(Player)) {
				return;
			}
			ReportesConfig.fc.set("Reportados", list);
			ReportesConfig.SaveConfig();
		}
		// DataBase

		// StaffMessage
		for (Player s : Bukkit.getOnlinePlayers()) {
			if (s.hasPermission("FBans.reportes")) {
				if (Main.m.getConfig().getBoolean("SomReport") == true) {
					s.playSound(s.getLocation(), Sound.ANVIL_LAND, 15.0F, 1.0F);
				}
				List<String> messagesStaff = Main.m.getConfig().getStringList("ReportS");
				String messageStaff = "";
				for (String mStaff : messagesStaff) {
					messageStaff += mStaff.replace("&", "§").replace("%m", Motivo).replace("%sender", Reporter)
							.replace("%b", Player);
					messageStaff += "\n";
				}
				s.sendMessage(messageStaff);
			}
		}
		// StaffMessage

		// ReporterMessage
		List<String> messagesReporter = Main.m.getConfig().getStringList("Report");
		String messageReporter = "";
		for (String mReporter : messagesReporter) {
			messageReporter += mReporter.replace("&", "§").replace("%m", Motivo).replace("%sender", Reporter)
					.replace("%b", Player);
			messageReporter += "\n";
		}
		Sender.sendMessage(messageReporter);
		// ReporterMessage

		// Twitter
		if (Main.m.getConfig().getBoolean("Twitter-Reportes") == true) {
			List<String> messagesReportT = Main.m.getConfig().getStringList("ReportT");
			String messageReportT = "";
			for (String mReportT : messagesReportT) {
				messageReportT += mReportT.replace("%m", Motivo).replace("%sender", Reporter).replace("%b", Player);
				messageReportT += "\n";
			}
			TwitterAPI.instance().tweet(messageReportT);
		}
		// Twitter

		// Discord
		if (Main.m.getConfig().getBoolean("Discord-Reportes") == true) {
			List<String> messagesReportD = Main.m.getConfig().getStringList("ReportD.Mensagem");
			String messageReportD = "";
			for (String mReportD : messagesReportD) {
				messageReportD += mReportD.replace("%m", Motivo).replace("%sender", Reporter).replace("%b", Player);
				messageReportD += "\n";
			}
			EmbedBuilder embed = new EmbedBuilder();
			embed.setColor(java.awt.Color.red);
			embed.setTitle(Main.m.getConfig().getString("ReportD.Titulo"));
			embed.setDescription(messageReportD);
			if (Main.m.getConfig().getBoolean("ReportD.Imagem") == true) {
				embed.setThumbnail(Main.m.getConfig().getString("ReportD.ImagemLink"));
			}
			embed.setFooter(Main.m.getConfig().getString("ReportD.Footer"),
					Main.m.getConfig().getString("ReportD.ImagemFooter"));
			Main.jda.getTextChannelById(Main.m.getConfig().getString("Bot.Canal-Reportes")).sendMessage(embed.build())
					.queue();
		}
		// Discord

	}

	public static void TempBan(String Sender, String Target, String Motivo, long endOfBan, Player p) {
		String Player = Target;
		String Staff = Sender;
		String Tempo = TimeAPI.getMSG(endOfBan);

		// DataBase
		if (Main.m.getConfig().getBoolean("MySQL.ativado") == true) {
			Data.tempban(Player, Staff, Motivo, endOfBan);
		} else {
			TempBanConfig.fc.set(Target + ".Staff", Staff);
			TempBanConfig.fc.set(Target + ".Motivo", Motivo);
			TempBanConfig.fc.set(Target + ".Tempo", endOfBan);
			TempBanConfig.SaveConfig();
		}
		// DataBase

		// Broadcast
		List<String> messagesTempBanon = Main.m.getConfig().getStringList("TempBan");
		String messageTempBanon = "";
		for (String mTempBanon : messagesTempBanon) {
			messageTempBanon += mTempBanon.replace("&", "§").replace("%b", Player).replace("%m", Motivo)
					.replace("%sender", Staff).replace("%t", Tempo);
			messageTempBanon += "\n";
		}
		Bukkit.broadcastMessage(messageTempBanon);
		// Broadcast

		// Twitter
		if (Main.m.getConfig().getBoolean("Twitter") == true) {
			List<String> messagesTempBant = Main.m.getConfig().getStringList("TempBanT");
			String messageTempBant = "";
			for (String mTempBant : messagesTempBant) {
				messageTempBant += mTempBant.replace("&", "§").replace("%b", Player).replace("%m", Motivo)
						.replace("%sender", Staff).replace("%t", Tempo);
				messageTempBant += "\n";
			}
			TwitterAPI.instance().tweet(messageTempBant);
		}
		// Twitter

		// Discord
		if (Main.m.getConfig().getBoolean("Discord") == true) {
			List<String> messagesTempBand = Main.m.getConfig().getStringList("TempBanD.Mensagem");
			String messageTempBand = "";
			for (String mTempBand : messagesTempBand) {
				messageTempBand += mTempBand.replace("&", "§").replace("%b", Player).replace("%m", Motivo)
						.replace("%sender", Staff).replace("%t", Tempo);
				messageTempBand += "\n";
			}
			EmbedBuilder embed = new EmbedBuilder();
			embed.setColor(java.awt.Color.red);
			embed.setTitle(Main.m.getConfig().getString("TempBanD.Titulo"));
			embed.setDescription(messageTempBand);
			if (Main.m.getConfig().getBoolean("TempBanD.Imagem") == true) {
				embed.setThumbnail(Main.m.getConfig().getString("TempBanD.ImagemLink"));
			}
			embed.setFooter(Main.m.getConfig().getString("TempBanD.Footer"),
					Main.m.getConfig().getString("TempBanD.ImagemFooter"));
			Main.jda.getTextChannelById(Main.m.getConfig().getString("Bot.Canal")).sendMessage(embed.build()).queue();
		}
		// Discord

		// KickPlayer
			List<String> messagesbanlogin = Main.m.getConfig().getStringList("TempBanLogin");
			String messagebanlogin = "";
			for (String mbanlogin : messagesbanlogin) {
				messagebanlogin += mbanlogin.replace("&", "§").replace("%b", Player).replace("%m", Motivo)
						.replace("%sender", Staff).replace("%t", Tempo);
				messagebanlogin += "\n";
			}
			p.kickPlayer(messagebanlogin);
		// KickPlayer

	}

	public static boolean CheckTempBan(String p) {
		String Player = p;
		if (Main.m.getConfig().getBoolean("MySQL.ativado") == true) {
			if (Data.checktempban(Player)) {
				return true;
			} else {
				return false;
			}
		} else {
			if (TempBanConfig.fc.contains(Player)) {
				return true;
			} else {
				return false;
			}
		}
	}

	public static void TempMute(String Sender, String Target, String Motivo, long endOfMute) {
		String Player = Target;
		String Staff = Sender;
		String Tempo = TimeAPI.getMSG(endOfMute);

		// DataBase
		if (Main.m.getConfig().getBoolean("MySQL.ativado") == true) {
			Data.tempmute(Player, Staff, Motivo, endOfMute);
		} else {
			TempMuteConfig.fc.set(Target + ".Staff", Staff);
			TempMuteConfig.fc.set(Target + ".Motivo", Motivo);
			TempMuteConfig.fc.set(Target + ".Tempo", endOfMute);
			TempMuteConfig.Save();
		}
		// DataBase

		// Broadcast
		List<String> messagesTempMuteon = Main.m.getConfig().getStringList("TempMute");
		String messageTempMuteon = "";
		for (String mTempMuteon : messagesTempMuteon) {
			messageTempMuteon += mTempMuteon.replace("&", "§").replace("%b", Player).replace("%m", Motivo)
					.replace("%sender", Staff).replace("%t", Tempo);
			messageTempMuteon += "\n";
		}
		Bukkit.broadcastMessage(messageTempMuteon);
		// Broadcast

		// Twitter
		if (Main.m.getConfig().getBoolean("Twitter") == true) {
			List<String> messagesTempMutet = Main.m.getConfig().getStringList("TempMuteT");
			String messageTempMutet = "";
			for (String mTempMutet : messagesTempMutet) {
				messageTempMutet += mTempMutet.replace("&", "§").replace("%b", Player).replace("%m", Motivo)
						.replace("%sender", Staff).replace("%t", Tempo);
				messageTempMutet += "\n";
			}
			TwitterAPI.instance().tweet(messageTempMutet);
		}
		// Twitter

		// Discord
		if (Main.m.getConfig().getBoolean("Discord") == true) {
			List<String> messagesTempMuted = Main.m.getConfig().getStringList("TempMuteD.Mensagem");
			String messageTempMuted = "";
			for (String mTempMuted : messagesTempMuted) {
				messageTempMuted += mTempMuted.replace("&", "§").replace("%b", Player).replace("%m", Motivo)
						.replace("%sender", Staff).replace("%t", Tempo);
				messageTempMuted += "\n";
			}
			EmbedBuilder embed = new EmbedBuilder();
			embed.setColor(java.awt.Color.red);
			embed.setTitle(Main.m.getConfig().getString("TempMuteD.Titulo"));
			embed.setDescription(messageTempMuted);
			if (Main.m.getConfig().getBoolean("TempMuteD.Imagem") == true) {
				embed.setThumbnail(Main.m.getConfig().getString("TempMuteD.ImagemLink"));
			}
			embed.setFooter(Main.m.getConfig().getString("TempMuteD.Footer"),
					Main.m.getConfig().getString("TempMuteD.ImagemFooter"));
			Main.jda.getTextChannelById(Main.m.getConfig().getString("Bot.Canal")).sendMessage(embed.build()).queue();
		}
		// Discord

	}

	public static boolean CheckTempMute(Player p) {
		String Player = p.getName();
		if (Main.m.getConfig().getBoolean("MySQL.ativado") == true) {
			if (Data.checktempmute(Player)) {
				return true;
			} else {
				return false;
			}
		} else {
			if (TempMuteConfig.fc.contains(p.getName())) {
				return true;
			} else {
				return false;
			}
		}
	}

	public static void Unban(String p, CommandSender sender) {
		String Player = p;
		if (Main.m.getConfig().getBoolean("MySQL.ativado") == true) {
			if (CheckBan(Player) == true) {
				Data.unban(Player);
				sender.sendMessage(Main.m.getConfig().getString("Prefix").replace("&", "§") + " "
						+ Main.m.getConfig().getString("Unban").replace("&", "§").replace("%b", p));
			}
			if (CheckTempBan(p) == true) {
				Data.tempunban(Player);
				sender.sendMessage(Main.m.getConfig().getString("Prefix").replace("&", "§") + " "
						+ Main.m.getConfig().getString("Unban").replace("&", "§").replace("%b", p));
			} else {
				sender.sendMessage(Main.m.getConfig().getString("Prefix").replace("&", "§") + " "
						+ Main.m.getConfig().getString("JaUnban").replace("&", "§"));
			}
		} else {
			if (CheckBan(Player) == true) {
				BanConfig.fc.set(Player, null);
				BanConfig.SaveConfig();
				sender.sendMessage(Main.m.getConfig().getString("Prefix").replace("&", "§") + " "
						+ Main.m.getConfig().getString("Unban").replace("&", "§").replace("%b", p));
			} else if (CheckTempBan(p) == true) {
				TempBanConfig.fc.set(Player, null);
				TempBanConfig.SaveConfig();
				sender.sendMessage(Main.m.getConfig().getString("Prefix").replace("&", "§") + " "
						+ Main.m.getConfig().getString("Unban").replace("&", "§").replace("%b", p));
			} else {
				sender.sendMessage(Main.m.getConfig().getString("Prefix").replace("&", "§") + " "
						+ Main.m.getConfig().getString("JaUnban").replace("&", "§"));
			}
		}
	}

	public static void Unmute(Player p, CommandSender sender) {
		String Player = p.getName();
		if (Main.m.getConfig().getBoolean("MySQL.ativado") == true) {
			if (CheckMute(p) == true) {
				Data.unmute(Player);
				sender.sendMessage(Main.m.getConfig().getString("Prefix").replace("&", "§") + " "
						+ Main.m.getConfig().getString("Unmute").replace("&", "§").replace("%b", Player));
			} else if (CheckTempMute(p) == true) {
				Data.tempunmute(Player);
				sender.sendMessage(Main.m.getConfig().getString("Prefix").replace("&", "§") + " "
						+ Main.m.getConfig().getString("Unmute").replace("&", "§").replace("%b", Player));
			} else {
				sender.sendMessage(Main.m.getConfig().getString("Prefix").replace("&", "§") + " "
						+ Main.m.getConfig().getString("JaUnmute").replace("&", "§"));
			}
		} else {
			if (CheckMute(p) == true) {
				MuteConfig.fc.set(Player, null);
				MuteConfig.Save();
				sender.sendMessage(Main.m.getConfig().getString("Prefix").replace("&", "§") + " "
						+ Main.m.getConfig().getString("Unmute").replace("&", "§").replace("%b", Player));
			} else if (CheckTempMute(p) == true) {
				TempMuteConfig.fc.set(Player, null);
				TempMuteConfig.Save();
				sender.sendMessage(Main.m.getConfig().getString("Prefix").replace("&", "§") + " "
						+ Main.m.getConfig().getString("Unmute").replace("&", "§").replace("%b", Player));
			} else {
				sender.sendMessage(Main.m.getConfig().getString("Prefix").replace("&", "§") + " "
						+ Main.m.getConfig().getString("JaUnmute").replace("&", "§"));
			}
		}
	}

	public static int getWarns(Player p) {
		String Player = p.getName();
		if (Main.m.getConfig().getBoolean("MySQL.ativado") == true) {
			return Data.getwarns(Player);
		} else {
			return WarnConfig.fc.getInt(Player + ".warns");
		}
	}

	public static void Unwarn(Player p, String Modo, CommandSender sender) {
		String Player = p.getName();
		if (Main.m.getConfig().getBoolean("MySQL.ativado") == true) {
			if (Modo.equalsIgnoreCase("Um")) {
				sender.sendMessage(Main.m.getConfig().getString("Unwarn").replace("&", "§").replace("%q", "1")
						.replace("%sender", sender.getName()).replace("%b", Player));
				Data.removewarn(Player, 1);
			} else if (Modo.equalsIgnoreCase("Todos")) {
				sender.sendMessage(Main.m.getConfig().getString("Unwarn").replace("&", "§")
						.replace("%q", String.valueOf(Data.getwarns(Player))).replace("%sender", sender.getName())
						.replace("%b", Player));
				Data.unwarn(Player);
			}
		} else {
			if (Modo.equalsIgnoreCase("Um")) {
				sender.sendMessage(Main.m.getConfig().getString("Unwarn").replace("&", "§").replace("%q", "1")
						.replace("%sender", sender.getName()).replace("%b", Player));
				WarnConfig.fc.set(Player + ".warns", WarnConfig.fc.getInt(Player + ".warns") - 1);
				WarnConfig.Save();
			} else if (Modo.equalsIgnoreCase("Todos")) {
				sender.sendMessage(Main.m.getConfig().getString("Unwarn").replace("&", "§")
						.replace("%q", WarnConfig.fc.getString(Player + ".warns")).replace("%sender", sender.getName())
						.replace("%b", Player));
				WarnConfig.fc.set(Player + ".warns", 0);
				WarnConfig.Save();
			}
		}
	}

	public static void Warn(Player p, CommandSender sender, String Motivo) {
		String Player = p.getName();

		if (Main.m.getConfig().getBoolean("MySQL.ativado") == true) {
			if (Data.getwarns(Player) < 2) {
				Data.addwarn(Player, 1);
			} else {
				Bukkit.dispatchCommand(Bukkit.getConsoleSender(),
						Main.m.getConfig().getString("ComandoWarn").replace("%b", Player));
				Data.unwarn(Player);
			}
		} else {
			if (APIGeral.getWarns(p) < 2) {
				WarnConfig.fc.set(Player + ".warns", WarnConfig.fc.getInt(Player + ".warns") + 1);
				WarnConfig.Save();
			} else {
				Bukkit.dispatchCommand(Bukkit.getConsoleSender(),
						Main.m.getConfig().getString("ComandoWarn").replace("%b", Player));
				WarnConfig.fc.set(Player + ".warns", 0);
				WarnConfig.Save();
			}
		}

		// Broadcast
		List<String> messagesWarn = Main.m.getConfig().getStringList("Warn");
		String messageWarn = "";
		for (String mWarn : messagesWarn) {
			messageWarn += mWarn.replace("&", "§").replace("%b", Player).replace("%m", Motivo).replace("%sender",
					sender.getName());
			messageWarn += "\n";
		}
		Bukkit.broadcastMessage(messageWarn);
		// Broadcast
	}
}
