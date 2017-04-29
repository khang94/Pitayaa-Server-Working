package pitayaa.nail.domain.enumtype;

import java.util.HashMap;
import java.util.Map;

public enum CategoryType {

	SERVICE(1),
	PACKAGE(2),

	PRODUCT(3)
	
	
	;

	private Integer value;

	private static Map<Integer, CategoryType> values = null;

	public Integer getValue() {
		return value;
	}

	CategoryType(Integer value) {
		this.value = value;
	}

	public static CategoryType parseValue(Integer value) {
		if (values == null) {
			values = new HashMap<Integer, CategoryType>(
					CategoryType.values().length);
			for (CategoryType e : CategoryType.values())
				values.put(e.getValue(), e);
		}
		return values.get(value);
	}
	
	@Override
	public String toString(){
		return this.value == null? null : this.value.toString();
	}
}
