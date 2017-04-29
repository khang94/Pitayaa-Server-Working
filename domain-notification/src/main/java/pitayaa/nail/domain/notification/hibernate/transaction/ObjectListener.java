package pitayaa.nail.domain.notification.hibernate.transaction;

import javax.persistence.PostLoad;
import javax.persistence.PostPersist;
import javax.persistence.PostRemove;
import javax.persistence.PostUpdate;
import javax.persistence.PrePersist;
import javax.persistence.PreRemove;
import javax.persistence.PreUpdate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



public class ObjectListener extends ObjectListenerHelper{
	private static final Logger LOGGER = LoggerFactory.getLogger(ObjectListener.class);
	
	@PrePersist
	public void ObjectPrePersist(Object ob) {
		LOGGER.info("Listening Object Pre Persist : " + ob.toString());
		this.setDateInsert(ob);
		
	}
	@PostPersist
	public void ObjectPostPersist(Object ob) {
		LOGGER.info("Listening Object Post Persist : " + ob.toString());
	}
	@PostLoad
	public void ObjectPostLoad(Object ob) {
		LOGGER.info("Listening Object Post Load : " + ob.toString());
	}	
	@PreUpdate
	public void ObjectPreUpdate(Object ob) {
		LOGGER.info("Listening Object Pre Update : " + ob.toString());
		this.setDateUpdate(ob);
	}
	@PostUpdate
	public void ObjectPostUpdate(Object ob) {
		LOGGER.info("Listening Object Post Update : " + ob.toString());
	}
	@PreRemove
	public void ObjectPreRemove(Object ob) {
		LOGGER.info("Listening Object Pre Remove : " + ob.toString());
	}
	@PostRemove
	public void ObjectPostRemove(Object ob) {
		LOGGER.info("Listening Object Post Remove : " + ob.toString());
	}
}
