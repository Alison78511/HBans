package HBans.EventApi;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class BanEvent extends Event {

	private String player;
	private String motivo;
	private String staff;
	private static final HandlerList handlers = new HandlerList();

	public BanEvent(String player, String motivo, String staff) {
		this.player = player;
		this.motivo = motivo;
		this.staff = staff;
	}

	public String getPlayer() {
		return player;
	}

	public String getMotivo() {
		return motivo;
	}

	public String getStaff() {
		return staff;
	}

	public HandlerList getHandlers() {
	    return handlers;
	}
	
	public static HandlerList getHandlerList() {
	    return handlers;
	}
}
