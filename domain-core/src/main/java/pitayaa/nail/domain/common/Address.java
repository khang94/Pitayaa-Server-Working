package pitayaa.nail.domain.common;

import javax.persistence.Embeddable;

import lombok.Data;

@Data
@Embeddable
public class Address {

	private String address;
	private String city;
	private String state;
	private String country;
	private String zipCode;
	private String street;

}
