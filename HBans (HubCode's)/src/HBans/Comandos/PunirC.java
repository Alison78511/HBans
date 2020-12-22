package HBans.Comandos;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import HBans.Main;
import HBans.EventApi.ChatObject;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;

public class PunirC implements CommandExecutor {

	public boolean onCommand(CommandSender s, Command cmd, String lb, String[] args) {
		if (s instanceof Player) {
			Player p = (Player) s;
			if (cmd.getName().equalsIgnoreCase("punir")) {
				if (!p.hasPermission("hbans.punir")) {
					p.sendMessage(Main.m.getConfig().getString("Prefix").replace("&", "§") + " "
							+ Main.m.getConfig().getString("SemPermissao").replace("&", "§"));
					return true;
				}
				if (args.length == 0) {
					p.sendMessage((Main.m.getConfig().getString("Prefix").replace("&", "§") + " "
							+ Main.m.getConfig().getString("PunirI").replace("&", "§")));
					return true;
				}
				if (args.length == 1) {
					p.sendMessage((Main.m.getConfig().getString("Prefix").replace("&", "§") + " "
							+ Main.m.getConfig().getString("PunirI").replace("&", "§")));
					return true;
				}
				Player targeton = Bukkit.getPlayer(args[0]);
				if (args.length == 2) {
					if (!args[1].startsWith("http://") && !args[1].startsWith("https://")) {
						p.sendMessage((Main.m.getConfig().getString("Prefix").replace("&", "§") + " "
								+ Main.m.getConfig().getString("LinkI").replace("&", "§")));
						return true;
					}
				}
				if (targeton == null) {
					p.sendMessage(Main.m.getConfig().getString("Prefix").replace("&", "§") + " "
							+ Main.m.getConfig().getString("PlayerOff").replace("&", "§"));
					return true;
				}
				if (targeton.hasPermission("hbans.Imune.*")) {
					p.sendMessage(Main.m.getConfig().getString("Prefix").replace("&", "§") + " "
							+ Main.m.getConfig().getString("PlayerImune").replace("&", "§"));
					return true;
				}
				p.sendMessage("");
				p.sendMessage("     §cTipo De Punições     ");
				p.sendMessage("");
				ArrayList<ChatObject> list = new ArrayList<>();
				for (String motivo : Main.m.getConfig().getConfigurationSection("Punir").getKeys(false)) {
					String comando = Main.m.getConfig().getString("Punir." + motivo + ".Comando").replace("%pr", args[1]).replace("%b", targeton.getName());
					String perm = Main.m.getConfig().getString("Punir." + motivo + ".Permissao");
					if (p.hasPermission(perm)) {
						list.add(new ChatObject("§f• " + motivo + ".",
								new HoverEvent(HoverEvent.Action.SHOW_TEXT,
										new ComponentBuilder("§7Clique para punir o player.").create()),
								new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, comando)));
					}
				}
				list.add(new ChatObject(" ", null, null));
				way(p, list);
				return true;
			}
		}
		return false;
	}

	public static void way(Player p, List<ChatObject> obj) {
		ArrayList<TextComponent> list = new ArrayList<>();
		for (ChatObject co : obj) {
			TextComponent c = new TextComponent(TextComponent.fromLegacyText(co.getMensagem()));
			c.setHoverEvent(co.getHoverEvent());
			c.setClickEvent(co.getClickEvent());
			list.add(c);
		}
		list.forEach(a -> p.spigot().sendMessage(a));
	}
}
