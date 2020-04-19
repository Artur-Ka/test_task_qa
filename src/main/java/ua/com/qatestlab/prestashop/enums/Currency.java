package ua.com.qatestlab.prestashop.enums;

/**
 * 
 * @author A. Karpenko
 * @date 16 апр. 2020 г. 19:50:25
 */
public enum Currency {
	EUR("€"),
	UAH("₴"),
	USD("$");

	private final String symbol;

	Currency(String symbol) {
		this.symbol = symbol;
	}

	public String getSymbol() {
		return symbol;
	}

	public static Currency getByText(String text) {
		final String substring = text.substring(8);
		for (Currency c : Currency.values()) {
			if (c.toString().equals(substring)) {
				return c;
			}
		}
		return null;
	}

	public static Currency getBySymbol(String symbol) {
		for (Currency c : Currency.values()) {
			if (c.getSymbol().equals(symbol)) {
				return c;
			}
		}
		return null;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		sb.append(name());
		sb.append(" ");
		sb.append(getSymbol());
		sb.append(" ");
		sb.append("");
		return sb.toString();
	}
}
