package PombaSS.cmds;

import java.util.*;
import org.bukkit.command.*;
import org.bukkit.entity.*;
import org.bukkit.*;
import org.bukkit.event.*;
import org.bukkit.event.player.*;

import PombaSS.uts.*;

public class ss implements CommandExecutor, Listener
{
    public static Configuration config2;
    public static ArrayList<String> bloq;
    public static HashMap<String, String> staff;
    
    static {
        ss.config2 = new Configuration("locs.yml");
        ss.bloq = new ArrayList<String>();
        ss.staff = new HashMap<String, String>();
    }
    
    public boolean onCommand(final CommandSender sender, final Command cmd, final String label, final String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("§cVoce nao e um jogador!");
            return true;
        }
        final Player p = (Player)sender;
        if (cmd.getName().equalsIgnoreCase("ss") || cmd.getName().equalsIgnoreCase("screenshare")) {
            if (p.hasPermission("pomba.ss")) {
                if (args.length == 0) {
                    p.sendMessage("§7Use /ss help");
                    return true;
                }
                final String nick = args[0];
                if (args.length == 1) {
                    if (Bukkit.getPlayer(nick) != null) {
                        if (!ss.config2.contains("locs.")) {
                            p.sendMessage("§cO spawn da SS n\u00e3o esta setado!");
                            p.sendMessage("§cUse /ss lobbyset");
                            return true;
                        }
                        final Player ss = Bukkit.getPlayer(nick);
                        if (PombaSS.cmds.ss.staff.containsKey(p.getName()) && !PombaSS.cmds.ss.bloq.contains(p.getName())) {
                            p.sendMessage("§cVoc\u00ea puxou um jogador pra SS recentemente e n\u00e3o liberou ele!");
                            p.sendMessage("§7Use /ss liberar <jogador>");
                            return true;
                        }
                        if (PombaSS.cmds.ss.bloq.contains(ss.getName())) {
                            p.sendMessage("§7Esse jogador j\u00e1 est\u00e1 em uma screenshare!");
                            return true;
                        }
                        final int v = 3;
                        final String puxou = "§cVoce foi puxado pra &e&lSS!";
                        for (int i = 0; i < v; ++i) {
                            ss.sendMessage(puxou);
                        }
                        p.sendMessage("§a" + ss.getName() + " §7foi puxado com sucesso!");
                        TitleAPI.create(p,"§c" + ss.getName(), "§afoi puxado pra SS!", 20,20,20);
                        final Location loc = new Location(Bukkit.getWorld(PombaSS.cmds.ss.config2.getString("locs..world")), PombaSS.cmds.ss.config2.getDouble("locs..x"), PombaSS.cmds.ss.config2.getDouble("locs..y"), PombaSS.cmds.ss.config2.getDouble("locs..z"));
                        ss.teleport(loc);
                        ss.teleport(ss.getLocation());
                        p.teleport(ss.getLocation());
                        PombaSS.cmds.ss.staff.put(p.getName(), ss.getName());
                        PombaSS.cmds.ss.staff.put(ss.getName(), p.getName());
                        PombaSS.cmds.ss.bloq.add(ss.getName());
                        return true;
                    }
                    else {
                        p.sendMessage("§cEsse jogador n\u00e3o est\u00e1 online!");
                    }
                }
            }
            else {
                p.sendMessage("§cVoc\u00ea n\u00e3o possui permiss\u00e3o!");
            }
        }
        if (cmd.getName().equalsIgnoreCase("ss") && args[0].equalsIgnoreCase("list")) {
            if (p.hasPermission("pomba.ss")) {
                if (args.length == 1) {
                    p.sendMessage("§a§lJOGADORES EM SS:");
                    p.sendMessage(" ");
                    if (ss.bloq.size() >= 1) {
                        for (int j = 0; j < ss.bloq.size(); ++j) {
                            p.sendMessage(" §b" + ss.bloq.get(j));
                        }
                    }
                    else {
                        p.sendMessage(" §cNenhum jogador em SS!");
                    }
                    p.sendMessage(" ");
                    return true;
                }
            }
            else {
                p.sendMessage("§cVoc\u00ea n\u00e3o possui permiss\u00e3o!");
            }
        }
        if (cmd.getName().equalsIgnoreCase("ss") && args[0].equalsIgnoreCase("liberar")) {
            if (p.hasPermission("pomba.ss")) {
                if (args.length != 2) {
                    p.sendMessage("§7Use /ss liberar <jogador>");
                    return true;
                }
                final String name = args[1];
                    if (ss.bloq.contains(name)) {
                        if (ss.staff.containsKey(name)) {
                            if (ss.staff.get(name).equalsIgnoreCase(p.getName())) {
                                p.sendMessage("§aVoc\u00ea liberou §a" + name + " §7da SS!");
                                final Player pname = Bukkit.getPlayer(name);
                                ss.staff.remove(p.getName(), name);
                                ss.staff.remove(name, p.getName());
                                ss.bloq.remove(name);
                                if (pname != null) {
                                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "tp world 0 225 -17 " + p.getName());
                                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "tp world 0 225 -17 " + pname.getName());
                                    pname.sendMessage("§aVoc\u00ea foi liberado da ss");
                                }
                                return true;
                            }
                            p.sendMessage("§cSomente quem puxou esse jogador pra SS pode libera-lo.");
                        }
                    }
                    else {
                        p.sendMessage("§cEsse jogador n\u00e3o est\u00e1 em SS!");
                    }
            }
            else {
                p.sendMessage("§cVoc\u00ea n\u00e3o possui permiss\u00e3o!");
            }
        }
        if (cmd.getName().equalsIgnoreCase("ss")  && args[0].equalsIgnoreCase("lobbyset")) {
            if (p.hasPermission("pomba.ss")) {
                if (args.length == 1) {
                    p.sendMessage("§7Voc\u00ea setou o lobby do SS com §asucesso!");
                    ss.config2.set("locs..world", p.getWorld().getName());
                    ss.config2.set("locs..x", p.getLocation().getX());
                    ss.config2.set("locs..y", p.getLocation().getY());
                    ss.config2.set("locs..z", p.getLocation().getZ());
                    ss.config2.saveConfig();
                    return true;
                }
            }
            else {
                p.sendMessage("§cVoc\u00ea n\u00e3o possui permiss\u00e3o!");
            }
        }
        if (cmd.getName().equalsIgnoreCase("ss") && args[0].equalsIgnoreCase("help")) {
            if (!p.hasPermission("pomba.ss")) {
                p.sendMessage("§cVoc\u00ea n\u00e3o possui permiss\u00e3o!");
                p.sendMessage("§aPlugin de SS criado por flationdev! O MAIS BRABO!");
                return true;
            }
            if (args.length == 1) {
                p.sendMessage(" ");
                p.sendMessage("§7Use /ss <jogador> - §bPara puxar um jogador pra SS");
                p.sendMessage("§7Use /ss lobbyset - §bPara setar o spawn do SS");
                p.sendMessage("§7Use /ss list - §bPara ver a lista dos jogadores em SS");
                p.sendMessage("§7Use /ss liberar - §bPara liberar um jogador da SS");
                p.sendMessage(" ");
                return true;
            }
        }
        return false;
    }
    
    @EventHandler(priority = EventPriority.HIGHEST)
    public void quit(final PlayerQuitEvent e) {
        final Player p = e.getPlayer();
        if (ss.bloq.contains(p.getName()) && ss.staff.containsKey(p.getName())) {
            final String namep = ss.staff.get(p.getName());
            final Player staffa = Bukkit.getPlayer(namep);
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "ban " + namep + " Deslogar em ScreenShare");
            ss.bloq.remove(p.getName());
            ss.staff.remove(staffa.getName(), p.getName());
            ss.staff.remove(p.getName(), staffa.getName());
        }
    }
    
    @EventHandler(priority = EventPriority.HIGHEST)
    public void quitb(final PlayerCommandPreprocessEvent e) {
        final Player p = e.getPlayer();
        if (ss.bloq.contains(p.getName())) {
            e.setCancelled(true);
            p.sendMessage("§cVoc\u00ea esta em SS e n\u00e3o pode executar um comando at\u00e9 ser liberado!");
        }
    }
}
