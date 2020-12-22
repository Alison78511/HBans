package HBans;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.security.auth.login.LoginException;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import HBans.Comandos.BanC;
import HBans.Comandos.DupeipC;
import HBans.Comandos.IpBanC;
import HBans.Comandos.IpUnbanC;
import HBans.Comandos.KickC;
import HBans.Comandos.MuteC;
import HBans.Comandos.PunirC;
import HBans.Comandos.ReportarC;
import HBans.Comandos.ReportesC;
import HBans.Comandos.TempBanC;
import HBans.Comandos.TempMuteC;
import HBans.Comandos.UnbanC;
import HBans.Comandos.UnmuteC;
import HBans.Comandos.UnwarnC;
import HBans.Comandos.VerificarC;
import HBans.Comandos.WarnC;
import HBans.Config.BanConfig;
import HBans.Config.IpBanConfig;
import HBans.Config.IpBanConfig2;
import HBans.Config.IpConfig;
import HBans.Config.MotivoReportConfig;
import HBans.Config.MuteConfig;
import HBans.Config.ReportesConfig;
import HBans.Config.StatusConfig;
import HBans.Config.TempBanConfig;
import HBans.Config.TempMuteConfig;
import HBans.Config.WarnConfig;
import HBans.EventApi.APIGeral;
import HBans.EventApi.InventoryClick;
import HBans.EventApi.JoinBan;
import HBans.EventApi.MessageNormal;
import HBans.Mysql.Data;
import net.dv8tion.jda.api.AccountType;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;

public class Main extends JavaPlugin {

	public static Main m;
	public static JDA jda;
	public static Connection conn = null;

	@SuppressWarnings("deprecation")
	public void onEnable() {
		getConfig().options().copyDefaults(true);
		saveDefaultConfig();

		if (getConfig().getBoolean("Discord") == true) {
			if (getConfig().getString("Bot.Token").equalsIgnoreCase("XXX-XXX-XXX-XXX")) {
				Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "Token inválido, plugin desabilitado");
				return;
			} else if (getConfig().getString("Bot.Canal").equalsIgnoreCase("XXX-XXX-XXX-XXX")) {
				Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "Canal inválido, plugin desabilitado");
				return;
			} else if (getConfig().getString("Bot.Canal-Reportes").equalsIgnoreCase("XXX-XXX-XXX-XXX")) {
				Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "Canal inválido, plugin desabilitado");
				return;
			}

			try {
				jda = new JDABuilder(AccountType.BOT).setToken(getConfig().getString("Bot.Token")).build();
			} catch (LoginException e) {
				e.printStackTrace();
			}
			jda.getPresence().setPresence(OnlineStatus.DO_NOT_DISTURB, Activity.playing(getConfig().getString("Bot.Presence")));
		}

		if (getConfig().getBoolean("MySQL.Ativado") == true) {
			try {
				try {

					Class.forName("com.mysql.jdbc.Driver");

					conn = (Connection) DriverManager.getConnection(
							"jdbc:mysql://" + getConfig().getString("MySQL.Host") + ":"
									+ Integer.valueOf(getConfig().getString("MySQL.Port")) + "/" + getConfig().getString("MySQL.DataBase"),
							getConfig().getString("MySQL.User"), getConfig().getString("MySQL.Password"));

				} catch (ClassNotFoundException | SQLException e) {

					e.printStackTrace();

				}
				Data.statement = conn.createStatement();
				Data.CriarTabela();
			} catch (Exception e2) {
				Bukkit.getConsoleSender().sendMessage("§cFalha ao conectar ao MySQL!");
			}
		}
		m = this;
		BanConfig.create();
		BanConfig.SaveConfig();
		IpBanConfig.create();
		IpBanConfig.SaveConfig();
		IpBanConfig2.create();
		IpBanConfig2.SaveConfig();
		ReportesConfig.create();
		ReportesConfig.SaveConfig();
		MotivoReportConfig.create();
		MotivoReportConfig.SaveConfig();
		MuteConfig.Create();
		MuteConfig.Save();
		WarnConfig.Create();
		WarnConfig.Save();
		TempMuteConfig.Create();
		TempMuteConfig.Save();
		TempBanConfig.create();
		TempBanConfig.SaveConfig();
		StatusConfig.Create();
		StatusConfig.Save();
		IpConfig.create();
		IpConfig.SaveConfig();
		Bukkit.getPluginManager().registerEvents(new MessageNormal(), this);
		Bukkit.getPluginManager().registerEvents(new JoinBan(), this);
		Bukkit.getPluginManager().registerEvents(new InventoryClick(), this);
		Bukkit.getPluginManager().registerEvents(new APIGeral(), this);
		getCommand("ban").setExecutor(new BanC());
		getCommand("ipban").setExecutor(new IpBanC());
		getCommand("unban").setExecutor(new UnbanC());
		getCommand("ipunban").setExecutor(new IpUnbanC());
		getCommand("mute").setExecutor(new MuteC());
		getCommand("mutar").setExecutor(new MuteC());
		getCommand("unmute").setExecutor(new UnmuteC());
		getCommand("unwarn").setExecutor(new UnwarnC());
		getCommand("kick").setExecutor(new KickC());
		getCommand("tempban").setExecutor(new TempBanC());
		getCommand("tempmute").setExecutor(new TempMuteC());
		getCommand("warn").setExecutor(new WarnC());
		getCommand("reportes").setExecutor(new ReportesC());
		getCommand("reportar").setExecutor(new ReportarC());
		getCommand("verificar").setExecutor(new VerificarC());
		getCommand("punir").setExecutor(new PunirC());
		getCommand("dupeip").setExecutor(new DupeipC());
		new BukkitRunnable() {
			public void run() {
				if (APIGeral.getBan() == 0)
					return;
				if (Main.m.getConfig().getBoolean("WatchDog.Ativado") == true) {
					for (String Status : Main.m.getConfig().getStringList("WatchDog.Mensagem")) {
						Bukkit.broadcastMessage(Status.replace("&", "§").replace("%pb", String.valueOf(APIGeral.getBan()))
								.replace("%pm", String.valueOf(APIGeral.getMute())));
					}
				}
			}
		}.runTaskTimer(this, 0L, Integer.valueOf(Main.m.getConfig().getString("WatchDog.Tempo")) * 1200L);
	}
}
