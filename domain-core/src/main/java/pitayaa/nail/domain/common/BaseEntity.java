package pitayaa.nail.domain.common;

import java.util.Date;

import lombok.Data;

@Data
public class BaseEntity {
	Date createdDate;
	Date updatedDate;
}
