package HBans.Mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.event.Listener;

public class Data implements Listener {
	public static Connection con;
	public static Statement statement;

	public static void CriarTabela() {
		try {
			PreparedStatement ps = con.prepareStatement(
					"CREATE TABLE IF NOT EXISTS bans (player VARCHAR(100), staff VARCHAR(100), motivo VARCHAR(100))");
			PreparedStatement ps11 = con.prepareStatement(
					"CREATE TABLE IF NOT EXISTS ipbans (ip VARCHAR(100), player VARCHAR(100), staff VARCHAR(100), motivo VARCHAR(100))");
			PreparedStatement ps2 = con.prepareStatement(
					"CREATE TABLE IF NOT EXISTS mutes (player VARCHAR(100), staff VARCHAR(100), motivo VARCHAR(100))");
			PreparedStatement ps3 = con.prepareStatement(
					"CREATE TABLE IF NOT EXISTS tempbans (player VARCHAR(100), staff VARCHAR(100), motivo VARCHAR(100), tempo Long)");
			PreparedStatement ps4 = con.prepareStatement(
					"CREATE TABLE IF NOT EXISTS tempmutes (player VARCHAR(100), staff VARCHAR(100), motivo VARCHAR(100), tempo Long)");
			PreparedStatement ps5 = con
					.prepareStatement("CREATE TABLE IF NOT EXISTS warns (player VARCHAR(100), warns int)");
			PreparedStatement ps6 = con.prepareStatement(
					"CREATE TABLE IF NOT EXISTS reports (player VARCHAR(100), reporter VARCHAR(100), motivo VARCHAR(100), data VARCHAR(100))");
			ps.executeUpdate();
			ps11.executeUpdate();
			ps2.executeUpdate();
			ps3.executeUpdate();
			ps4.executeUpdate();
			ps5.executeUpdate();
			ps6.executeUpdate();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public static void setpwarns(String player) {
		PreparedStatement stm = null;
		try {
			stm = con.prepareStatement("INSERT INTO warns (player, warns) VALUES ( ?, ?)");
			stm.setString(1, player.toLowerCase());
			stm.setInt(2, 0);
			stm.executeUpdate();
		} catch (SQLException e) {
		}
	}

	public static void setwarns(String player, int quantia) {
		if (checkwarn(player)) {
			PreparedStatement stm = null;
			try {
				stm = con.prepareStatement("UPDATE `warns` SET `warns` = ? WHERE `player` = ?");
				stm.setInt(1, quantia);
				stm.setString(2, player.toLowerCase());
				stm.executeUpdate();
			} catch (SQLException e) {
			}
		} else {
			setpwarns(player);
		}
	}

	public static int getwarns(String player) {
		if (checkwarn(player)) {
			PreparedStatement stm = null;
			try {
				stm = con.prepareStatement("SELECT * FROM `warns` WHERE `player` = ?");
				stm.setString(1, player.toLowerCase());
				ResultSet rs = stm.executeQuery();
				while (rs.next()) {
					return rs.getInt("warns");
				}
				return 0;
			} catch (SQLException e) {
				return 0;
			}
		} else {
			setpwarns(player);
			return 0;
		}
	}

	public static void addwarn(String player, int quantia) {
		if (checkwarn(player)) {
			setwarns(player, getwarns(player) + quantia);
		} else {
			setpwarns(player);
			setwarns(player, getwarns(player) + quantia);
		}
	}

	public static void removewarn(String player, int quantia) {
		if (checkwarn(player)) {
			setwarns(player, getwarns(player) - quantia);
		} else {
			setpwarns(player);
			setwarns(player, getwarns(player) - quantia);
		}
	}

	public static boolean checkban(String p) {
		try {
			PreparedStatement ps = con.prepareStatement("SELECT * FROM bans WHERE player= ?");
			ps.setString(1, p);
			ResultSet rs = ps.executeQuery();
			boolean user = rs.next();
			rs.close();
			rs.close();
			return user;
		} catch (Exception ex) {
		}
		return false;
	}

	public static boolean checkipban(String p) {
		try {
			PreparedStatement ps = con.prepareStatement("SELECT * FROM ipbans WHERE player= ?");
			ps.setString(1, p);
			ResultSet rs = ps.executeQuery();
			boolean user = rs.next();
			rs.close();
			rs.close();
			return user;
		} catch (Exception ex) {
		}
		return false;
	}

	public static boolean checkbanip(String ip) {
		try {
			PreparedStatement ps = con.prepareStatement("SELECT * FROM ipbans WHERE ip= ?");
			ps.setString(1, ip);
			ResultSet rs = ps.executeQuery();
			boolean user = rs.next();
			rs.close();
			rs.close();
			return user;
		} catch (Exception ex) {
		}
		return false;
	}

	public static boolean checkreport(String p) {
		try {
			PreparedStatement ps = con.prepareStatement("SELECT * FROM reports WHERE player= ?");
			ps.setString(1, p);
			ResultSet rs = ps.executeQuery();
			boolean user = rs.next();
			rs.close();
			rs.close();
			return user;
		} catch (Exception ex) {
		}
		return false;
	}

	public static boolean checkmute(String p) {
		try {
			PreparedStatement ps = con.prepareStatement("SELECT * FROM mutes WHERE player= ?");
			ps.setString(1, p);
			ResultSet rs = ps.executeQuery();
			boolean user = rs.next();
			rs.close();
			rs.close();
			return user;
		} catch (Exception ex) {
		}
		return false;
	}

	public static boolean checktempban(String p) {
		try {
			PreparedStatement ps = con.prepareStatement("SELECT * FROM tempbans WHERE player= ?");
			ps.setString(1, p);
			ResultSet rs = ps.executeQuery();
			boolean user = rs.next();
			rs.close();
			rs.close();
			return user;
		} catch (Exception ex) {
		}
		return false;
	}

	public static boolean checktempmute(String p) {
		try {
			PreparedStatement ps = con.prepareStatement("SELECT * FROM tempmutes WHERE player= ?");
			ps.setString(1, p);
			ResultSet rs = ps.executeQuery();
			boolean user = rs.next();
			rs.close();
			rs.close();
			return user;
		} catch (Exception ex) {
		}
		return false;
	}

	public static boolean checkwarn(String p) {
		try {
			PreparedStatement ps = con.prepareStatement("SELECT * FROM warns WHERE player= ?");
			ps.setString(1, p);
			ResultSet rs = ps.executeQuery();
			boolean user = rs.next();
			rs.close();
			rs.close();
			return user;
		} catch (Exception ex) {
		}
		return false;
	}

	public static void report(String p, String reporter, String motivo, String data) {
		try {
			PreparedStatement ps = Data.con
					.prepareStatement("INSERT INTO reports (player, reporter, motivo, data) VALUES ( ?, ?, ?, ?)");
			ps.setString(1, p);
			ps.setString(2, reporter);
			ps.setString(3, motivo);
			ps.setString(4, data);
			ps.executeUpdate();
			ps.close();
		} catch (Exception ex) {
			ex.toString();
		}
	}

	public static void tempban(String p, String staff, String motivo, long endOfBan) {
		try {
			PreparedStatement ps = Data.con
					.prepareStatement("INSERT INTO tempbans (player, staff, motivo, tempo) VALUES ( ?, ?, ?, ?)");
			ps.setString(1, p);
			ps.setString(2, staff);
			ps.setString(3, motivo);
			ps.setLong(4, endOfBan);
			ps.executeUpdate();
			ps.close();
		} catch (Exception ex) {
			ex.toString();
		}
	}

	public static void tempmute(String p, String staff, String motivo, long tempo) {
		try {
			PreparedStatement ps = Data.con
					.prepareStatement("INSERT INTO tempmutes (player, staff, motivo, tempo) VALUES ( ?, ?, ?, ?)");
			ps.setString(1, p);
			ps.setString(2, staff);
			ps.setString(3, motivo);
			ps.setLong(4, tempo);
			ps.executeUpdate();
			ps.close();
		} catch (Exception ex) {
			ex.toString();
		}
	}

	public static void ban(String p, String staff, String motivo) {
		try {
			PreparedStatement ps = Data.con
					.prepareStatement("INSERT INTO bans (player, staff, motivo) VALUES ( ?, ?, ?)");
			ps.setString(1, p);
			ps.setString(2, staff);
			ps.setString(3, motivo);
			ps.executeUpdate();
			ps.close();
		} catch (Exception ex) {
			ex.toString();
		}
	}

	public static void ipban(String ip, String p, String staff, String motivo) {
		try {
			PreparedStatement ps = Data.con
					.prepareStatement("INSERT INTO bans (ip, player, staff, motivo) VALUES ( ?, ?, ?, ?)");
			ps.setString(1, ip);
			ps.setString(2, p);
			ps.setString(3, staff);
			ps.setString(4, motivo);
			ps.executeUpdate();
			ps.close();
		} catch (Exception ex) {
			ex.toString();
		}
	}

	public static void mute(String p, String staff, String motivo) {
		try {
			PreparedStatement ps = Data.con
					.prepareStatement("INSERT INTO mutes (player, staff, motivo) VALUES ( ?, ?, ?)");
			ps.setString(1, p);
			ps.setString(2, staff);
			ps.setString(3, motivo);
			ps.executeUpdate();
			ps.close();
		} catch (Exception ex) {
			ex.toString();
		}
	}

	public static void tempunban(String p) {
		try {
			PreparedStatement ps = Data.con.prepareStatement("DELETE FROM `tempbans` WHERE `player` = ?");
			ps.setString(1, p.toLowerCase());
			ps.executeUpdate();
		} catch (Exception ex) {
			ex.toString();
		}
	}

	public static void tempunmute(String p) {
		try {
			PreparedStatement ps = Data.con.prepareStatement("DELETE FROM `tempmutes` WHERE `player` = ?");
			ps.setString(1, p.toLowerCase());
			ps.executeUpdate();
		} catch (Exception ex) {
			ex.toString();
		}
	}

	public static void unmute(String p) {
		try {
			PreparedStatement ps = Data.con.prepareStatement("DELETE FROM `mutes` WHERE `player` = ?");
			ps.setString(1, p.toLowerCase());
			ps.executeUpdate();
		} catch (Exception ex) {
			ex.toString();
		}
	}

	public static void unban(String p) {
		try {
			PreparedStatement ps = Data.con.prepareStatement("DELETE FROM `bans` WHERE `player` = ?");
			ps.setString(1, p.toLowerCase());
			ps.executeUpdate();
		} catch (Exception ex) {
			ex.toString();
		}
	}

	public static void ipunban(String p) {
		try {
			PreparedStatement ps = Data.con.prepareStatement("DELETE FROM `bans` WHERE `player` = ?");
			ps.setString(1, p.toLowerCase());
			ps.executeUpdate();
		} catch (Exception ex) {
			ex.toString();
		}
	}

	public static void unbanip(String ip) {
		try {
			PreparedStatement ps = Data.con.prepareStatement("DELETE FROM `bans` WHERE `ip` = ?");
			ps.setString(1, ip.toLowerCase());
			ps.executeUpdate();
		} catch (Exception ex) {
			ex.toString();
		}
	}

	public static void unwarn(String p) {
		try {
			PreparedStatement ps = Data.con.prepareStatement("DELETE FROM `warns` WHERE `player` = ?");
			ps.setString(1, p.toLowerCase());
			ps.executeUpdate();
		} catch (Exception ex) {
			ex.toString();
		}
	}

	public static void unreport(String p) {
		try {
			PreparedStatement ps = Data.con.prepareStatement("DELETE FROM `reports` WHERE `player` = ?");
			ps.setString(1, p.toLowerCase());
			ps.executeUpdate();
		} catch (Exception ex) {
			ex.toString();
		}
	}

	public static String getBanMotivo(String p) {
		if (checkban(p)) {
			try {
				PreparedStatement ps = con.prepareStatement("SELECT * FROM `bans` WHERE `player` = ?");
				ps.setString(1, p.toLowerCase());
				ResultSet rs = ps.executeQuery();
				while (rs.next()) {
					return rs.getString("motivo");
				}
				return "ERRO 404";
			} catch (SQLException e) {
				return "ERRO 404";
			}
		} else {
			return "ERRO 404";
		}
	}
	
	public static String getTempMuteMotivo(String p) {
		if (checktempmute(p)) {
			try {
				PreparedStatement ps = con.prepareStatement("SELECT * FROM `tempmutes` WHERE `player` = ?");
				ps.setString(1, p.toLowerCase());
				ResultSet rs = ps.executeQuery();
				while (rs.next()) {
					return rs.getString("motivo");
				}
				return "ERRO 404";
			} catch (SQLException e) {
				return "ERRO 404";
			}
		} else {
			return "ERRO 404";
		}
	}
	
	public static String getTempMuteStaff(String p) {
		if (checktempmute(p)) {
			try {
				PreparedStatement ps = con.prepareStatement("SELECT * FROM `tempmutes` WHERE `player` = ?");
				ps.setString(1, p.toLowerCase());
				ResultSet rs = ps.executeQuery();
				while (rs.next()) {
					return rs.getString("staff");
				}
				return "ERRO 404";
			} catch (SQLException e) {
				return "ERRO 404";
			}
		} else {
			return "ERRO 404";
		}
	}

	public static String getBanStaff(String p) {
		if (checkban(p)) {
			try {
				PreparedStatement ps = con.prepareStatement("SELECT * FROM `bans` WHERE `player` = ?");
				ps.setString(1, p.toLowerCase());
				ResultSet rs = ps.executeQuery();
				while (rs.next()) {
					return rs.getString("staff");
				}
				return "ERRO 404";
			} catch (SQLException e) {
				return "ERRO 404";
			}
		} else {
			return "ERRO 404";
		}
	}

	public static String getipBanMotivo(String ip) {
		if (checkban(ip)) {
			try {
				PreparedStatement ps = con.prepareStatement("SELECT * FROM `bans` WHERE `ip` = ?");
				ps.setString(1, ip.toLowerCase());
				ResultSet rs = ps.executeQuery();
				while (rs.next()) {
					return rs.getString("motivo");
				}
				return "ERRO 404";
			} catch (SQLException e) {
				return "ERRO 404";
			}
		} else {
			return "ERRO 404";
		}
	}

	public static String getipBanStaff(String ip) {
		if (checkban(ip)) {
			try {
				PreparedStatement ps = con.prepareStatement("SELECT * FROM `bans` WHERE `ip` = ?");
				ps.setString(1, ip.toLowerCase());
				ResultSet rs = ps.executeQuery();
				while (rs.next()) {
					return rs.getString("staff");
				}
				return "ERRO 404";
			} catch (SQLException e) {
				return "ERRO 404";
			}
		} else {
			return "ERRO 404";
		}
	}

	public static String getipBanNick(String ip) {
		if (checkban(ip)) {
			try {
				PreparedStatement ps = con.prepareStatement("SELECT * FROM `bans` WHERE `ip` = ?");
				ps.setString(1, ip.toLowerCase());
				ResultSet rs = ps.executeQuery();
				while (rs.next()) {
					return rs.getString("player");
				}
				return "ERRO 404";
			} catch (SQLException e) {
				return "ERRO 404";
			}
		} else {
			return "ERRO 404";
		}
	}

	public static String getReportMotivo(String p) {
		if (checkreport(p)) {
			try {
				PreparedStatement ps = con.prepareStatement("SELECT * FROM `reports` WHERE `player` = ?");
				ps.setString(1, p.toLowerCase());
				ResultSet rs = ps.executeQuery();
				while (rs.next()) {
					return rs.getString("motivo");
				}
				return "ERRO 404";
			} catch (SQLException e) {
				return "ERRO 404";
			}
		} else {
			return "ERRO 404";
		}
	}

	public static String getReporter(String p) {
		if (checkreport(p)) {
			try {
				PreparedStatement ps = con.prepareStatement("SELECT * FROM `reports` WHERE `player` = ?");
				ps.setString(1, p.toLowerCase());
				ResultSet rs = ps.executeQuery();
				while (rs.next()) {
					return rs.getString("reporter");
				}
				return "ERRO 404";
			} catch (SQLException e) {
				return "ERRO 404";
			}
		} else {
			return "ERRO 404";
		}
	}

	public static String getReportData(String p) {
		if (checkreport(p)) {
			try {
				PreparedStatement ps = con.prepareStatement("SELECT * FROM `reports` WHERE `player` = ?");
				ps.setString(1, p.toLowerCase());
				ResultSet rs = ps.executeQuery();
				while (rs.next()) {
					return rs.getString("data");
				}
				return "ERRO 404";
			} catch (SQLException e) {
				return "ERRO 404";
			}
		} else {
			return "ERRO 404";
		}
	}

	public static String getMuteMotivo(String p) {
		if (checkmute(p)) {
			try {
				PreparedStatement ps = con.prepareStatement("SELECT * FROM `mutes` WHERE `player` = ?");
				ps.setString(1, p.toLowerCase());
				ResultSet rs = ps.executeQuery();
				while (rs.next()) {
					return rs.getString("motivo");
				}
				return "ERRO 404";
			} catch (SQLException e) {
				return "ERRO 404";
			}
		} else {
			return "ERRO 404";
		}
	}

	public static String getMuteStaff(String p) {
		if (checkmute(p)) {
			try {
				PreparedStatement ps = con.prepareStatement("SELECT * FROM `mutes` WHERE `player` = ?");
				ps.setString(1, p.toLowerCase());
				ResultSet rs = ps.executeQuery();
				while (rs.next()) {
					return rs.getString("staff");
				}
				return "ERRO 404";
			} catch (SQLException e) {
				return "ERRO 404";
			}
		} else {
			return "ERRO 404";
		}
	}

	public static String getTempBanMotivo(String p) {
		if (checktempban(p)) {
			try {
				PreparedStatement ps = con.prepareStatement("SELECT * FROM `tempbans` WHERE `player` = ?");
				ps.setString(1, p.toLowerCase());
				ResultSet rs = ps.executeQuery();
				while (rs.next()) {
					return rs.getString("motivo");
				}
				return "ERRO 404";
			} catch (SQLException e) {
				return "ERRO 404";
			}
		} else {
			return "ERRO 404";
		}
	}

	public static String getTempBanStaff(String p) {
		if (checktempban(p)) {
			try {
				PreparedStatement ps = con.prepareStatement("SELECT * FROM `tempbans` WHERE `player` = ?");
				ps.setString(1, p.toLowerCase());
				ResultSet rs = ps.executeQuery();
				while (rs.next()) {
					return rs.getString("staff");
				}
				return "ERRO 404";
			} catch (SQLException e) {
				return "ERRO 404";
			}
		} else {
			return "ERRO 404";
		}
	}

	public static long getTempBanTempo(String p) {
		if (checktempban(p)) {
			try {
				PreparedStatement ps = con.prepareStatement("SELECT * FROM `tempbans` WHERE `player` = ?");
				ps.setString(1, p.toLowerCase());
				ResultSet rs = ps.executeQuery();
				while (rs.next()) {
					return rs.getLong("tempo");
				}
				return 0;
			} catch (SQLException e) {
				return 0;
			}
		} else {
			return 0;
		}
	}

	public static long gettempmuteTempo(String p) {
		if (checktempmute(p)) {
			try {
				PreparedStatement ps = con.prepareStatement("SELECT * FROM `tempmutes` WHERE `player` = ?");
				ps.setString(1, p.toLowerCase());
				ResultSet rs = ps.executeQuery();
				while (rs.next()) {
					return rs.getLong("tempo");
				}
				return 0;
			} catch (SQLException e) {
				return 0;
			}
		} else {
			return 0;
		}
	}

	public static List<String> getReporteds() {
		PreparedStatement stm = null;
		List<String> tops = new ArrayList<String>();
		try {
			stm = con.prepareStatement("SELECT * FROM `reports`");
			ResultSet rs = stm.executeQuery();
			int i = 0;
			while (rs.next()) {
				if (i <= 54) {
					i++;
					tops.add(rs.getString("player"));
				}
			}
		} catch (SQLException e) {
		}
		return tops;
	}
}
