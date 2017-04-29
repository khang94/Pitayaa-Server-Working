package pitayaa.nail.domain.notification.common;

import java.util.Date;

import javax.persistence.Embeddable;

import lombok.Data;

@Data
@Embeddable
public class Metadata {

	private Date createdDate;
	private Date updatedDate;
	private Date deliveredDate;
	private String status;
	private String templateId;
	private String nameModel;

}
