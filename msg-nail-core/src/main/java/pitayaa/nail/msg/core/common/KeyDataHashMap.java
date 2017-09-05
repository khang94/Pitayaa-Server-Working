package pitayaa.nail.msg.core.common;

import java.util.HashMap;

import pitayaa.nail.domain.report.elements.KeyData;

public class KeyDataHashMap extends HashMap<KeyData, Integer> {

	private static final long serialVersionUID = 1L;

	@Override
	public Integer put(KeyData keyData, Integer counter) {
		for (Entry<KeyData, Integer> entry : entrySet()) {

			KeyData oldKey = entry.getKey();
			if (oldKey.getName().equalsIgnoreCase(keyData.getName())
					&& oldKey.getUuid().equalsIgnoreCase(keyData.getUuid())) {
				
				if(keyData.getName().equalsIgnoreCase("Body massage")){
					System.out.println("bug");
				}
				remove(oldKey);
				KeyData newKey = keyData;
                return super.put(newKey, counter);
			}
		}
		return super.put(keyData, counter);
	}
}
