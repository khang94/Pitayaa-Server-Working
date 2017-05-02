package pitayaa.nail.domain.setting.sms;

import javax.persistence.Embeddable;

import lombok.Data;

@Data
@Embeddable
public class TemplateDetail {
	
	private String template1;
	private String template2;
	private String template3;
	private String template4;
	
	private Integer templateActive;

}
