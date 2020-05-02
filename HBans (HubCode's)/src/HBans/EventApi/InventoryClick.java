package HBans.EventApi;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import HBans.Main;
import HBans.Config.MotivoReportConfig;
import HBans.Config.ReportesConfig;

public class InventoryClick implements Listener {

	@EventHandler
	public void Execute1(InventoryClickEvent e) {
		if ((e.getCurrentItem() != null) && (e.getCurrentItem().getItemMeta() != null)) {
			Player p = (Player) e.getWhoClicked();
			Inventory Inv = e.getInventory();
			if (Inv.getTitle().equalsIgnoreCase("§4Reportes")) {
				if (e.getClick() == ClickType.LEFT) {
					e.setCancelled(true);
					String b = e.getCurrentItem().getItemMeta().getDisplayName().replace("§c", "");
					Bukkit.dispatchCommand(Bukkit.getConsoleSender(),
							Main.m.getConfig().getString("ComandoReport")
									.replace("%b", e.getCurrentItem().getItemMeta().getDisplayName().replace("§c", ""))
									.replace("%m", MotivoReportConfig.fc.getString(b + ".Motivo")));
					List<String> list = ReportesConfig.fc.getStringList("Reportados");
					list.remove(e.getCurrentItem().getItemMeta().getDisplayName().replace("§c", ""));
					ReportesConfig.fc.set("Reportados", list);
					ReportesConfig.fc.set(e.getCurrentItem().getItemMeta().getDisplayName().replace("§c", ""), null);
					MotivoReportConfig.fc.set(e.getCurrentItem().getItemMeta().getDisplayName().replace("§c", "") + ".Reportes", 0);
					ReportesConfig.SaveConfig();
					p.closeInventory();
					p.chat("/reportes");
					p.sendMessage(Main.m.getConfig().getString("Prefix").replace("&", "§") + " "
							+ Main.m.getConfig().getString("DenunciaAceita").replace("&", "§"));
				} else if (e.getClick() == ClickType.RIGHT) {
					e.setCancelled(true);
					List<String> list = ReportesConfig.fc.getStringList("Reportados");
					list.remove(e.getCurrentItem().getItemMeta().getDisplayName().replace("§c", ""));
					ReportesConfig.fc.set("Reportados", list);
					ReportesConfig.fc.set(e.getCurrentItem().getItemMeta().getDisplayName().replace("§c", ""), null);
					MotivoReportConfig.fc.set(e.getCurrentItem().getItemMeta().getDisplayName().replace("§c", "") + ".Reportes", 0);
					ReportesConfig.SaveConfig();
					p.closeInventory();
					p.chat("/reportes");
					p.sendMessage(Main.m.getConfig().getString("Prefix").replace("&", "§") + " "
							+ Main.m.getConfig().getString("DenunciaNegada").replace("&", "§"));
				} else if (e.getClick() == ClickType.MIDDLE) {
					e.setCancelled(true);
					p.chat("/tp " + e.getCurrentItem().getItemMeta().getDisplayName().replace("§c", ""));
					p.closeInventory();
					p.sendMessage(Main.m.getConfig().getString("Prefix").replace("&", "§") + " "
							+ Main.m.getConfig().getString("DenunciaTeleporte").replace("&", "§"));
				}
			}
		}
	}
	@EventHandler
	public void Execute2(InventoryClickEvent e) {
		if ((e.getCurrentItem() != null) && (e.getCurrentItem().getItemMeta() != null)) {
			Inventory Inv = e.getInventory();
			if (Inv.getTitle().equalsIgnoreCase("§4Status")) {
				e.setCancelled(true);
			}
		}
	}

}
