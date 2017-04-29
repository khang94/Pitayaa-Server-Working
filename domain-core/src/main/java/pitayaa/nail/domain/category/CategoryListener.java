package pitayaa.nail.domain.category;

import javax.persistence.PostLoad;
import javax.persistence.PostPersist;
import javax.persistence.PostRemove;
import javax.persistence.PostUpdate;
import javax.persistence.PrePersist;
import javax.persistence.PreRemove;
import javax.persistence.PreUpdate;

import pitayaa.nail.domain.account.Account;

public class CategoryListener {
	@PrePersist
	public void CategoryPrePersist(Object ob) {
		//ob.setCreatedDate(new Date());
		//ob.setUpdatedDate(new Date());
		System.out.println(ob.getClass().equals(Account.class));
		if(ob.getClass().getName().equalsIgnoreCase("Category")){
			System.out.println("login");
		}
		if(Category.class.isAssignableFrom(Object.class)){
			System.out.println("test");
		}
		if(ob != null){
			System.out.println("test2");
		}
		System.out.println("Listening Category Pre Persist : " + ob.toString());
	}
	@PostPersist
	public void CategoryPostPersist(Category ob) {
		System.out.println("Listening Category Pre Persist : " + ob.getCategoryName());
	}
	@PostLoad
	public void CategoryPostLoad(Category ob) {
		System.out.println("Listening Category Pre Persist : " + ob.getCategoryName());
	}	
	@PreUpdate
	public void CategoryPreUpdate(Category ob) {
		System.out.println("Listening Category Pre Persist : " + ob.getCategoryName());
	}
	@PostUpdate
	public void CategoryPostUpdate(Category ob) {
		System.out.println("Listening Category Pre Persist : " + ob.getCategoryName());
	}
	@PreRemove
	public void CategoryPreRemove(Category ob) {
		System.out.println("Listening Category Pre Persist : " + ob.getCategoryName());
	}
	@PostRemove
	public void CategoryPostRemove(Category ob) {
		System.out.println("Listening Category Pre Persist : " + ob.getCategoryName());
	}
}
