package pitayaa.nail.msg.core.report.business;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import pitayaa.nail.domain.report.elements.KeyData;
import pitayaa.nail.domain.report.elements.ReportCustomerData;

public interface DataAnalyst {

	default List<KeyData> buildKeyDataSorted(HashMap<KeyData , Integer> mapData){
		
		// Get key from hashMap
		List<KeyData> keyDatas = new ArrayList<>();
		
		mapData.keySet().forEach(key ->{
			key.setCounter(mapData.get(key));
			keyDatas.add(key);
		});
		
		// Sort
		KeyData[] keyArray = this.toArraySort(keyDatas);
		keyDatas.clear();
		for(KeyData key : keyArray){
			keyDatas.add(key);
		}
		
		return keyDatas;
	}
	
	default KeyData[] toArraySort(List<KeyData> keyDatas){
		KeyData[] datas = new KeyData[keyDatas.size()];
		
		for(int i = 0 ; i < keyDatas.size(); i++){
			
			datas[i] = new KeyData();
			
			datas[i].setUuid(keyDatas.get(i).getUuid());
			datas[i].setName(keyDatas.get(i).getName());
			datas[i].setCounter(keyDatas.get(i).getCounter());
		}
		
		Arrays.sort(datas);
		
		return datas;
	}
}
