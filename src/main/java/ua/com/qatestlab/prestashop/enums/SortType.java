package ua.com.qatestlab.prestashop.enums;

/**
 * 
 * @author A. Karpenko
 * @date 16 апр. 2020 г. 22:06:26
 */
public enum SortType {
	POSITION_ASC("Релевантность"),
	NAME_ASCK("Названию: от А к Я"),
	NAME_DESC("Названию: от Я к А"),
	PRICE_ASC("Цене: от низкой к высокой"),
	PRICE_DESC("Цене: от высокой к низкой");
	
	private final String desc;
	
	SortType(String desc) {
		this.desc = desc;
	}
	
	@Override
	public String toString() {
		return desc;
	}
}
