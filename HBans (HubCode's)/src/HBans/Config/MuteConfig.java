package HBans.Config;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import HBans.Main;

public class MuteConfig {

	static File f;
	public static FileConfiguration fc;
	static MuteConfig m;

	public static void Create() {
		f = new File(Main.m.getDataFolder() + "/mute.db");
		fc = YamlConfiguration.loadConfiguration(f);
	}

	public static void Save() {
		try {
			fc.save(f);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public MuteConfig config() {
		return m;
	}
	

}