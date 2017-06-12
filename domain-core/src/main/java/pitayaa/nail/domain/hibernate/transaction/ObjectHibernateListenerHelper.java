package pitayaa.nail.domain.hibernate.transaction;

import java.util.Date;

import org.springframework.stereotype.Service;

import pitayaa.nail.domain.account.Account;
import pitayaa.nail.domain.account.elements.AccountLicense;
import pitayaa.nail.domain.appointment.Appointment;
import pitayaa.nail.domain.category.Category;
import pitayaa.nail.domain.customer.Customer;
import pitayaa.nail.domain.employee.Employee;
import pitayaa.nail.domain.license.License;
import pitayaa.nail.domain.license.elements.Price;
import pitayaa.nail.domain.packages.PackageModel;
import pitayaa.nail.domain.promotion.Promotion;
import pitayaa.nail.domain.salon.Salon;
import pitayaa.nail.domain.salon.elements.SalonLicense;
import pitayaa.nail.domain.service.ServiceModel;
import pitayaa.nail.domain.setting.SettingSms;
import pitayaa.nail.domain.view.View;

@Service
public class ObjectHibernateListenerHelper {

	// Set create & update dated every time call post request
	protected Object setDateInsert(Object ob) {

		if (ob.getClass().equals(Account.class)) {
			Account account = (Account) ob;
			account.setRegisteredDate(new Date());
			account.setUpdatedDate(new Date());
		} else if (ob.getClass().equals(AccountLicense.class)) {
			AccountLicense accountLicense = (AccountLicense) ob;
			accountLicense.setCreatedDate(new Date());
			accountLicense.setUpdatedDate(new Date());

		} else if (ob.getClass().equals(Appointment.class)) {
			Appointment appointment = (Appointment) ob;
			appointment.setCreatedDate(new Date());
			appointment.setUpdatedDate(new Date());

		} else if (ob.getClass().equals(Category.class)) {
			Category category = (Category) ob;
			category.setCreatedDate(new Date());
			category.setUpdatedDate(new Date());

		} else if (ob.getClass().equals(Customer.class)) {
			Customer customer = (Customer) ob;
			customer.setCreatedDate(new Date());
			customer.setUpdatedDate(new Date());

		} else if (ob.getClass().equals(Employee.class)) {
			Employee employee = (Employee) ob;
			employee.setCreatedDate(new Date());
			employee.setUpdatedDate(new Date());

		} else if (ob.getClass().equals(License.class)) {
			License license = (License) ob;
			license.setCreatedDate(new Date());
			license.setUpdatedDate(new Date());

		} else if (ob.getClass().equals(PackageModel.class)) {
			PackageModel packageModel = (PackageModel) ob;
			packageModel.setCreatedDate(new Date());
			packageModel.setUpdatedDate(new Date());

		} else if (ob.getClass().equals(Promotion.class)) {
			Promotion promotion = (Promotion) ob;
			promotion.setCreatedDate(new Date());
			promotion.setUpdatedDate(new Date());

		} else if (ob.getClass().equals(Salon.class)) {
			Salon salon = (Salon) ob;
			salon.setCreatedDate(new Date());
			salon.setUpdatedDate(new Date());

		} else if (ob.getClass().equals(SalonLicense.class)) {
			SalonLicense salonLicense = (SalonLicense) ob;
			salonLicense.setCreatedDate(new Date());
			salonLicense.setUpdatedDate(new Date());

		} else if (ob.getClass().equals(ServiceModel.class)) {
			ServiceModel serviceModel = (ServiceModel) ob;
			serviceModel.setCreatedDate(new Date());
			serviceModel.setUpdatedDate(new Date());
			
		} else if (ob.getClass().equals(Price.class)) {
			Price price = (Price) ob;
			price.setCreatedDate(new Date());
			price.setUpdatedDate(new Date());
		} else if (ob.getClass().equals(View.class)) {
			View view = (View) ob;
			view.setCreatedDate(new Date());
			view.setUpdatedDate(new Date());
		} else if (ob.getClass().equals(SettingSms.class)){
			SettingSms settingSms = (SettingSms) ob;
			settingSms.setCreatedDate(new Date());
			settingSms.setUpdatedDate(new Date());
		}
		return ob;
	}
	
	// Set update dated every time call post request
		public Object setDateUpdate(Object ob) {

			if (ob.getClass().equals(Account.class)) {
				Account account = (Account) ob;
				account.setUpdatedDate(new Date());
			} else if (ob.getClass().equals(AccountLicense.class)) {
				AccountLicense accountLicense = (AccountLicense) ob;
				accountLicense.setUpdatedDate(new Date());

			} else if (ob.getClass().equals(Appointment.class)) {
				Appointment appointment = (Appointment) ob;
				appointment.setUpdatedDate(new Date());

			} else if (ob.getClass().equals(Category.class)) {
				Category category = (Category) ob;
				category.setUpdatedDate(new Date());

			} else if (ob.getClass().equals(Customer.class)) {
				Customer customer = (Customer) ob;
				customer.setUpdatedDate(new Date());

			} else if (ob.getClass().equals(Employee.class)) {
				Employee employee = (Employee) ob;
				employee.setUpdatedDate(new Date());

			} else if (ob.getClass().equals(License.class)) {
				License license = (License) ob;
				license.setUpdatedDate(new Date());

			} else if (ob.getClass().equals(PackageModel.class)) {
				PackageModel packageModel = (PackageModel) ob;
				packageModel.setUpdatedDate(new Date());

			} else if (ob.getClass().equals(Promotion.class)) {
				Promotion promotion = (Promotion) ob;
				promotion.setUpdatedDate(new Date());

			} else if (ob.getClass().equals(Salon.class)) {
				Salon salon = (Salon) ob;
				salon.setUpdatedDate(new Date());

			} else if (ob.getClass().equals(SalonLicense.class)) {
				SalonLicense salonLicense = (SalonLicense) ob;
				salonLicense.setUpdatedDate(new Date());

			} else if (ob.getClass().equals(ServiceModel.class)) {
				ServiceModel serviceModel = (ServiceModel) ob;
				serviceModel.setUpdatedDate(new Date());
				
			} else if (ob.getClass().equals(Price.class)) {
				Price price = (Price) ob;
				price.setUpdatedDate(new Date());
			} else if (ob.getClass().equals(View.class)) {
				View view = (View) ob;
				view.setUpdatedDate(new Date());
			} else if (ob.getClass().equals(SettingSms.class)){
				SettingSms settingSms = (SettingSms) ob;
				settingSms.setUpdatedDate(new Date());
			}
			return ob;
		}

}
