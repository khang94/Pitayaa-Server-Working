package pitayaa.nail.json.employee;

import java.util.Date;
import java.util.UUID;

import lombok.Data;
import pitayaa.nail.json.common.JsonAddress;
import pitayaa.nail.json.common.JsonContact;
import pitayaa.nail.json.employee.elements.JsonEmployeeDetail;

@Data
public class JsonEmployee {

	private UUID uuid;

	private JsonEmployeeDetail employeeDetail;

	private JsonAddress address;

	private JsonContact contact;

	private String viewId;

	private Date createdDate;

	private Date updatedDate;
	private String additionalInfor;

}
