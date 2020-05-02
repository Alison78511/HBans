package HBans.Comandos;

import java.util.ArrayList;
import java.util.Iterator;
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
import HBans.Config.MotivoReportConfig;
import HBans.Config.ReportesConfig;
import HBans.Mysql.Data;

public class ReportesC implements CommandExecutor {
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player)) {
			return true;
		}
		Player s = (Player) sender;
		if (label.equalsIgnoreCase("reportes")) {
			if (!sender.hasPermission("HBans.reportes")) {
				sender.sendMessage(Main.m.getConfig().getString("Prefix").replace("&", "§") + " "
						+ Main.m.getConfig().getString("SemPermissao"));
				return true;
			}
			if (args.length != 0) {
				sender.sendMessage(Main.m.getConfig().getString("Prefix").replace("&", "§") + " "
						+ Main.m.getConfig().getString("ReportesI").replace("&", "§"));
				return true;
			}
			List<String> list = getReportList();
			int size = 54;
			if (list.size() <= 9) {
				size = 9;
			} else if (list.size() <= 18) {
				size = 18;
			} else if (list.size() <= 27) {
				size = 27;
			} else if (list.size() <= 36) {
				size = 36;
			} else if (list.size() <= 45) {
				size = 45;
			} else if (list.size() <= 54) {
				size = 54;
			}
			String invname = "§4Reportes";

			final Inventory reportgui = Bukkit.createInventory(null, size, invname);

			int slot = 0;
			if (list.size() > 0) {
				Iterator<String> it = list.iterator();
				while (it.hasNext()) {
					final String p = (String) it.next();
					final ItemStack head = new ItemStack(Material.SKULL_ITEM, 1, (short) SkullType.PLAYER.ordinal());
					final SkullMeta sm = (SkullMeta) head.getItemMeta();
					final List<String> lores = new ArrayList<String>();
					sm.setOwner(p);
					sm.setDisplayName("§c" + p);
					lores.add("");
					if (Main.m.getConfig().getBoolean("MySQL.Ativado") == true) {
						lores.add("§7Reporter: §c" + Data.getReporter(p));
						lores.add("§7Motivo: §c" + Data.getReportMotivo(p));
						lores.add("§7Data: §c" + Data.getReportData(p));
					} else {
						lores.add("§7Reporter: §c" + MotivoReportConfig.fc.getString(p + ".Reporter"));
						lores.add("§7Motivo: §c" + MotivoReportConfig.fc.getString(p + ".Motivo"));
						lores.add("§7Data: §c" + MotivoReportConfig.fc.getString(p + ".Data"));
					}
					lores.add("");
					lores.add("§7Botão Esquerdo: §cAceita a denúncia");
					lores.add("§7Botão Direito: §cNega a denúncia");
					lores.add("§7Botão Scroll: §cTeleporta até o player");
					lores.add("");
					slot++;
					final int sl = slot - 1;
					sm.setLore(lores);
					head.setItemMeta(sm);
					reportgui.setItem(sl, head);
				}
			}
			s.openInventory(reportgui);
		}
		return false;
	}

	public static List<String> getReportList() {
		if (Main.m.getConfig().getBoolean("MySQL.Ativado") == true) {
			return Data.getReporteds();
		} else {
			return ReportesConfig.fc.getStringList("Reportados");
		}
	}
}
