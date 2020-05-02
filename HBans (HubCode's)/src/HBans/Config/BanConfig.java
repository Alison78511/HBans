package HBans.Config;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import HBans.Main;

public class BanConfig {

	static File f;
	public static FileConfiguration fc;
	static BanConfig m;

	public static void create() {
		f = new File(Main.m.getDataFolder() + "/bans.db");
		fc = YamlConfiguration.loadConfiguration(f);
	}

	public static void SaveConfig() {
		try {
			fc.save(f);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public BanConfig config() {
		return m;
	}
	

}
