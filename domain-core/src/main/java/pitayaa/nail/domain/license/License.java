package pitayaa.nail.domain.license;

import java.util.Date;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

import lombok.Data;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import pitayaa.nail.domain.hibernate.transaction.ObjectHibernateListener;
import pitayaa.nail.domain.license.elements.LicenseDetail;

@Data
@Entity
@EntityListeners(ObjectHibernateListener.class)
public class License {

	@Id
	@GenericGenerator(name = "uuid-gen", strategy = "uuid2")
	@GeneratedValue(generator = "uuid-gen")
	@Type(type = "pg-uuid")
	private UUID uuid;

	@Version
	Long version;

	@OneToOne(cascade = CascadeType.ALL)
	private LicenseDetail licenseDetail;

	private String licenseName;

	private int numEmployee;

	private int numDevices;

	private int numClientProfiles;

	private int numFreeEmail;

	private int numFreeSms;

	private int numShop;

	private boolean isTrial;

	private String timezone;

	@Temporal(TemporalType.TIMESTAMP)
	private Date createdDate;

	@Temporal(TemporalType.TIMESTAMP)
	private Date updatedDate;

	private String status;

	//
	/*
	 * id bigint NOT NULL, license_id bigint NOT NULL, price double precision
	 * DEFAULT 0, -- gia status smallint NOT NULL DEFAULT 0, -- 0: inactive; 1:
	 * active create_date timestamp without time zone DEFAULT
	 * ('now'::text)::timestamp without time zone, update_date timestamp without
	 * time zone, term smallint, -- ki han license term_type smallint, -- loai
	 * ki han: 1:ngay, 2: tuan, 3 thang, 4: nam price_type smallint, -- loai
	 * gia: 1: gia kich hoat; 2: gia gia han CONSTRAINT license_price_pkey
	 * PRIMARY KEY (id)
	 */

	/*
	 * id bigint NOT NULL, license_name character varying(250), -- Ten license
	 * num_employee bigint, -- so nhan vien toi da num_ipad bigint, -- so ipad
	 * toi da num_client_profile bigint, -- so khach hang toi da num_free_email
	 * bigint, -- so free email toi da num_free_sms bigint, -- so tin nhan toi
	 * da num_shop bigint, -- so luong shop toi da is_sync_data_server smallint,
	 * -- 0: khong dong bo data len server; 1: dong bo data len server is_trial
	 * smallint, -- 0: license version; 1: trial version status smallint, -- 0:
	 * inactive; 1: active create_date timestamp without time zone DEFAULT
	 * ('now'::text)::timestamp without time zone, update_date timestamp without
	 * time zone, CONSTRAINT license_pkey PRIMARY KEY (id)
	 */

}
