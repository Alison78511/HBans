package HBans.Comandos;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.SkullType;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import HBans.Main;
import HBans.Config.BanConfig;
import HBans.Config.MuteConfig;
import HBans.Config.ReportesConfig;
import HBans.Config.TempBanConfig;
import HBans.Config.TempMuteConfig;
import HBans.Config.WarnConfig;
import HBans.EventApi.TimeAPI;
import HBans.Mysql.Data;

public class VerificarC implements CommandExecutor {
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player)) {
			return true;
		}
		Player p = (Player) sender;
		if (label.equalsIgnoreCase("verificar")) {
			if (!sender.hasPermission("HBans.verificar")) {
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
			String invname = "§4Status";

			final Inventory reportgui = Bukkit.createInventory(null, 9, invname);

			final ItemStack head = new ItemStack(Material.SKULL_ITEM, 1, (short) SkullType.PLAYER.ordinal());
			final SkullMeta sm = (SkullMeta) head.getItemMeta();
			final List<String> lores = new ArrayList<String>();
			sm.setOwner(t.getName());
			sm.setDisplayName("§cStatus de " + t.getName());
			if (Main.m.getConfig().getBoolean("MySQL.Ativado") == true) {
				lores.add("");
				if (Data.checkban(t.getName())) {
					lores.add("§7Banido: §cSim");
					lores.add("§7Motivo: §c" + Data.getBanMotivo(t.getName()));
					lores.add("§7Staffer: §c" + Data.getBanStaff(t.getName()));
				} else {
					lores.add("§7Banido: §aNão");
				}
				lores.add("");
				if (Data.checkmute(t.getName())) {
					lores.add("§7Mutado: §cSim");
					lores.add("§7Motivo: §c" + Data.getMuteMotivo(t.getName()));
					lores.add("§7Staffer: §c" + Data.getMuteStaff(t.getName()));
				} else {
					lores.add("§7Mutado: §aNão");
				}
				lores.add("");
				if (Data.checktempban(t.getName())) {
					String time1 = TimeAPI.getMSG(Data.getTempBanTempo(t.getName()));
					lores.add("§7Temporariamente Banido: §cSim");
					lores.add("§7Motivo: §c" + Data.getTempBanMotivo(t.getName()));
					lores.add("§7Staffer: §c" + Data.getTempBanStaff(t.getName()));
					lores.add("§7Será desmutado em §c " + time1);
				} else {
					lores.add("§7Temporariamente Banido: §aNão");
				}
				lores.add("");

				if (Data.checktempmute(t.getName())) {
					String time2 = TimeAPI.getMSG(Data.gettempmuteTempo(t.getName()));
					lores.add("§7Temporariamente Mutado: §cSim");
					lores.add("§7Motivo: §c" + Data.getTempMuteMotivo(t.getName()));
					lores.add("§7Staffer: §c" + Data.getTempMuteStaff(t.getName()));
					lores.add("§7Será desmutado em §c " + time2);
				} else {
					lores.add("§7Temporariamente Mutado: §aNão");
				}
				lores.add("");
				if (Data.checkreport(t.getName())) {
					lores.add("§7Reportado: §cSim");
				} else {
					lores.add("§7Reportado: §aNão");
				}
				if (Data.getwarns(t.getName()) > 0) {
					lores.add("§7Warns: §c" + Data.getwarns(t.getName()));
				} else {
					lores.add("§7Warns: §a0");
				}
				sm.setLore(lores);
			} else {
				lores.add("");
				if (BanConfig.fc.getBoolean(t.getName() + ".banido") == true) {
					lores.add("§7Banido: §cSim");
					lores.add("§7Motivo: §c" + BanConfig.fc.getString(t.getName() + ".motivo"));
					lores.add("§7Staffer: §c" + BanConfig.fc.getString(t.getName() + ".Staff"));
				} else {
					lores.add("§7Banido: §aNão");
				}
				lores.add("");
				if (MuteConfig.fc.getBoolean(t.getName() + ".mutado") == true) {
					lores.add("§7Mutado: §cSim");
					lores.add("§7Motivo: §c" + MuteConfig.fc.getString(t.getName() + ".motivo"));
					lores.add("§7Staffer: §c" + MuteConfig.fc.getString(t.getName() + ".Staff"));
				} else {
					lores.add("§7Mutado: §aNão");
				}
				lores.add("");
				if (TempBanConfig.fc.getBoolean(t.getName() + ".banido") == true) {
					String time3 = TimeAPI.getMSG(Long.valueOf(TempBanConfig.fc.getString(t.getName() + ".Tempo")));
					lores.add("§7Temporariamente Banido: §cSim");
					lores.add("§7Motivo: §c" + TempBanConfig.fc.getString(t.getName() + ".Motivo"));
					lores.add("§7Staffer: §c" + TempBanConfig.fc.getString(t.getName() + ".Staff"));
					lores.add("§7Será desbanido em §c" + time3);
				} else {
					lores.add("§7Temporariamente Banido: §aNão");
				}
				lores.add("");

				if (TempMuteConfig.fc.getBoolean(t.getName() + ".mutado") == true) {
					String time4 = TimeAPI.getMSG(Long.valueOf(TempMuteConfig.fc.getString(t.getName() + ".Tempo")));
					lores.add("§7Temporariamente Mutado: §cSim");
					lores.add("§7Motivo: §c" + TempMuteConfig.fc.getString(t.getName() + ".Motivo"));
					lores.add("§7Staffer: §c" + TempMuteConfig.fc.getString(t.getName() + ".Staff"));
					lores.add("§7Será desmutado em §c" + time4);
				} else {
					lores.add("§7Temporariamente Mutado: §aNão");
				}
				lores.add("");
				if (ReportesConfig.fc.getStringList("Reportados").contains(t.getName())) {
					lores.add("§7Reportado §cSim");
				} else {
					lores.add("§7Reportado §aNão");
				}
				if (WarnConfig.fc.getInt(t.getName() + ".warns") > 0) {
					lores.add("§7Warns §c" + WarnConfig.fc.getInt(t.getName() + ".warns"));
				} else {
					lores.add("§7Warns §a0");
				}
				sm.setLore(lores);
			}
			head.setItemMeta(sm);
			reportgui.setItem(4, head);
			p.openInventory(reportgui);
		}
		return false;

	}
}
