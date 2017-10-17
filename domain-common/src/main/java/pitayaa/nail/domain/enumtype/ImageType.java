package pitayaa.nail.domain.enumtype;

import java.util.HashMap;
import java.util.Map;

public enum ImageType {
	COMMON(0),
	SERVICE(1),
	PACKAGE(2),
	PRODUCT(3),
	STAFF(4),
	CUSTOMER(5),
	LOGO(6),
	SMALL_BANNER(7),
	LARGE_BANNER(8),
	CATEGORY(9)

	;

	private Integer value;

	private static Map<Integer, ImageType> values = null;

	public Integer getValue() {
		return value;
	}

	ImageType(Integer value) {
		this.value = value;
	}

	public static ImageType parseValue(Integer value) {
		if (values == null) {
			values = new HashMap<Integer, ImageType>(
					CategoryType.values().length);
			for (ImageType e : ImageType.values())
				values.put(e.getValue(), e);
		}
		return values.get(value);
	}
	
	@Override
	public String toString(){
		return this.value == null? null : this.value.toString();
	}
}
