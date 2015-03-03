package org.adorsys.adcore.jpa;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.PrePersist;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.time.DateUtils;

/**
 * A lease can be set to prevent a process from deleting temporal data.
 * The lease setter, does this to notified the record owning application
 * that the lease owner is still consuming those data.
 * 
 * The lease is only valid if it is not expired.
 * 
 * @author francis
 *
 */
public abstract class AbstractLease extends AbstractTimedData {

	private static final long serialVersionUID = -3518962027500923774L;
	
	/*
	 * The thread currently processing this request.
	 */
	@Column
	@NotNull
	private String processOwner;
	
	/*
	 * The name of the application processing the data.
	 */
	@Column
	@NotNull
	private String handlerName;
	
	private String synchPoint;
	private String processingItem;
	private String processingStatus;

	/**
	 * We automatically set the lease to 5 minutes.
	 * @see org.adorsys.adcore.jpa.AbstractTimedData#prePersist()
	 */
	@PrePersist
	public void prePersist() {
		setIdentif(handlerName);
		setId(handlerName);
		Date now = new Date();
		if(getValidFrom()==null) setValidFrom(now);
		if(getValidTo()==null) setValidTo(DateUtils.addMinutes(now, 5));
	}
	
	public void extend(String processOwner, Integer seconds){
		if(seconds==null)seconds=300;
		setProcessOwner(processOwner);
		setValidTo(DateUtils.addSeconds(new Date(), seconds));
	}
	public void extend(String processOwner){
		extend(processOwner,null);
	}
	
	public boolean expired(Date date){
		return date.after(getValidTo());
	}

	public String getProcessOwner() {
		return processOwner;
	}

	public void setProcessOwner(String processOwner) {
		this.processOwner = processOwner;
	}

	public String getHandlerName() {
		return handlerName;
	}

	public void setHandlerName(String handlerName) {
		this.handlerName = handlerName;
	}

	public String getSynchPoint() {
		return synchPoint;
	}

	public void setSynchPoint(String synchPoint) {
		this.synchPoint = synchPoint;
	}

	public String getProcessingItem() {
		return processingItem;
	}

	public void setProcessingItem(String processingItem) {
		this.processingItem = processingItem;
	}

	public String getProcessingStatus() {
		return processingStatus;
	}

	public void setProcessingStatus(String processingStatus) {
		this.processingStatus = processingStatus;
	}
}
