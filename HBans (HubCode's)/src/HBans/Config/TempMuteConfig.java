package HBans.Config;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import HBans.Main;

public class TempMuteConfig {

	static File f;
	public static FileConfiguration fc;
	static TempMuteConfig m;

	public static void Create() {
		f = new File(Main.m.getDataFolder() + "/tempmute.db");
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
	public TempMuteConfig config() {
		return m;
	}
	

}