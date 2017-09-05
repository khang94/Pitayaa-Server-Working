package pitayaa.nail.domain.report.elements;

import lombok.Data;

@Data
public class KeyData implements Comparable<KeyData>{
	
	private String uuid;
	private String name;
	private Integer counter;
	
	public KeyData(){
		this.uuid = "";
		this.name = "";
		this.counter = 0;
	}
	
	public int compareTo(KeyData keyData) {

		int compareCounter = ((KeyData) keyData).getCounter();

		//ascending order
		//return this.counter - compareCounter;

		//descending order
		return compareCounter - this.counter;

	}
}
