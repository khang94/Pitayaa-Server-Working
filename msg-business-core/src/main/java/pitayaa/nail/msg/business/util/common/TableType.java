package pitayaa.nail.msg.business.util.common;

import java.util.HashMap;
import java.util.List;

public class TableType {
	private static TableType instance;
	private HashMap<String, HashMap<String, String>> hm;
	private List<String> listSeqName;

	public static TableType getInstance() {
		if (instance == null) {
			instance = new TableType();
		}

		return instance;
	}

	public void setHashMap(HashMap<String, HashMap<String, String>> hashmap) {
		this.hm = hashmap;
	}

	public HashMap<String, HashMap<String, String>> getHashMap() {
		return hm;
	}

	public List<String> getListSeqName() {
		return listSeqName;
	}

	public void setListSeqName(List<String> listSeqName) {
		this.listSeqName = listSeqName;
	}
}
