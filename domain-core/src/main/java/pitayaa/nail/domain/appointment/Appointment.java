package pitayaa.nail.domain.appointment;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import lombok.Data;
import pitayaa.nail.domain.appointment.elements.NotificationSignal;
import pitayaa.nail.domain.customer.Customer;
import pitayaa.nail.domain.employee.Employee;
import pitayaa.nail.domain.hibernate.transaction.ObjectHibernateListener;
import pitayaa.nail.domain.packages.PackageModel;
import pitayaa.nail.domain.promotion.Promotion;
import pitayaa.nail.domain.service.ServiceModel;

@Data
@Entity
@EntityListeners(ObjectHibernateListener.class)
public class Appointment  {

	@Id
	@GenericGenerator(name = "uuid-gen", strategy = "uuid2")
	@GeneratedValue(generator = "uuid-gen")
	@Type(type = "pg-uuid")
	private UUID uuid;

	@ElementCollection
	private List<NotificationSignal> notifyToCustomer;

	@ElementCollection
	private List<NotificationSignal> notifyToEmployee;

	@ManyToOne(cascade = CascadeType.ALL)
	private Customer customer;

	@ManyToOne(cascade = CascadeType.ALL)
	private Employee employee;

	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<ServiceModel> servicesGroup;

	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<PackageModel> packagesGroup;

	@OneToOne(cascade = CascadeType.ALL)
	private Promotion promotion;

	private String appointmentType;

	private Boolean isConfirm;

	private String status;

	@Temporal(TemporalType.TIMESTAMP)
	private Date createdDate;

	@Temporal(TemporalType.TIMESTAMP)
	private Date updatedDate;
	private String createdBy;
	private String updatedBy;

	private Date startTime;
	private Date endTime;

	private String timezone;
	private String salonId;

}
