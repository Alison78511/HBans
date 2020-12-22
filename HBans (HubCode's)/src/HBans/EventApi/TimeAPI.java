package HBans.EventApi;

public enum TimeAPI {
	SECOND("segundos", 1 / 60), MINUTE("minutos", 1), HOUR("horas", 60), DAY("dias", 60 * 24),
	WEEK("semanas", 60 * 24 * 7), MONTH("meses", 30 * 60 * 24), YEAR("anos", 30 * 60 * 24 * 12);

	public String name;
	public int multi;

	TimeAPI(String n, int mult) {
		name = n;
		multi = mult;
	}

	public static long getTicks(String un, int time) {
		long sec;

		try {
			sec = time * 60;
		} catch (NumberFormatException ex) {
			return 0;
		}

		for (TimeAPI unit : TimeAPI.values()) {
			if (un.startsWith(unit.name)) {
				return (sec *= unit.multi) * 1000;
			}
		}

		return 0;
	}

	public static String getMSG(long endOfBan) {
		String message = "";

		long now = System.currentTimeMillis();
		long diff = endOfBan - now;
		int seconds = (int) (diff / 1000);

		if (seconds >= 60 * 60 * 24) {
			int days = seconds / (60 * 60 * 24);
			seconds = seconds % (60 * 60 * 24);

			if (days > 1) {
				message += days + " Dias ";
			} else {
				message += days + " Dia ";
			}
		}
		if (seconds >= 60 * 60) {
			int hours = seconds / (60 * 60);
			seconds = seconds % (60 * 60);

			if (hours > 1) {
				message += hours + " Horas ";
			} else {
				message += hours + " Hora ";
			}
		}
		if (seconds >= 60) {
			int min = seconds / 60;
			seconds = seconds % 60;

			if (min > 1) {
				message += min + " Minutos ";
			} else {
				message += min + " Minuto ";
			}
		}
		if (seconds >= 0) {
			message += seconds + " Segundos ";
		}

		return message;
	}
}
