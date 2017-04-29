package pitayaa.nail.domain.notification.common;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.Data;

@Data
@Embeddable
public class KeyValueModel {
	
	@Column(insertable = false , updatable = false)
	private String key;
	
	@Column(insertable = false , updatable = false)
	private String value;

}
