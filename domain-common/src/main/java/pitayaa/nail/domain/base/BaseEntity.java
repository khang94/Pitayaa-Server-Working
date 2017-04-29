package pitayaa.nail.domain.base;

import java.util.Date;

import lombok.Data;

@Data
public class BaseEntity {

	private Date createdDate;
	private Date updatedDate;
	private String status;
	private String nameEntity;

}
