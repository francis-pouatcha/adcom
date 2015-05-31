package org.adorsys.adinvtry.rest;

import java.util.Date;
import java.util.List;

import javax.ejb.Asynchronous;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.enterprise.event.Event;
import javax.inject.Inject;

import org.adorsys.adinvtry.jpa.InvInvtryItem;
import org.adorsys.adinvtry.jpa.InvInvtryItemList;
import org.adorsys.adinvtry.repo.InvInvtryItemRepository;

@Stateless
public class InvInvtryItemHelperEJB
{

	@Inject
	private InvInvtryItemRepository repository;

	@Inject
	@InvInconsistentInvtryEvent
	private Event<String> inconsistentInvtryEvent;

	private InvInvtryItem internalUpdate(InvInvtryItem invtryItem){
		invtryItem = repository.save(attach(invtryItem));

		return invtryItem;
	}

	public InvInvtryItem findById(String id)
	{
		return repository.findBy(id);
	}

	private InvInvtryItem attach(InvInvtryItem entity)
	{
		if (entity == null)
			return null;

		return entity;
	}
	
	@Asynchronous
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void deleteItems(List<InvInvtryItem> items){
		if(items.size()>100) items = items.subList(0, 99);
		for (InvInvtryItem invtryItem : items) {
			deleteById(invtryItem.getId());
		}
	}

	public InvInvtryItem deleteById(String id)
	{
		InvInvtryItem entity = repository.findBy(id);
		if (entity != null)
		{
			repository.remove(entity);
		}
		return entity;
	}


	/**
	 * Returns the number of item automatically consolidated. This happens when all the inventories of that 
	 * same item are counted with the same quantity. In this case the system automatically marks the latest 
	 * count as enabled by marking everything else disabled.
	 * 
	 * @param invtryNbr
	 * @return
	 */
	@Asynchronous
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void makeConsistent(String invtryNbr, String salIndex){
//		Integer count = 0;
		Long countEnabled = repository.countEnabled(salIndex, invtryNbr);
		if(countEnabled==1) return;
		List<InvInvtryItem> resultList = repository.bySalIndexForInvtry(salIndex, invtryNbr).getResultList();
		Boolean sameQty = InvInvtryItemList.checkSameQty(resultList);
		if(!sameQty) {
			setConflicting(resultList, new Date());
			inconsistentInvtryEvent.fire(invtryNbr);
			return;
		}
		InvInvtryItem oldest = null;
		for (InvInvtryItem invInvtryItem : resultList) {
			// continue if this item is disabled and there is more than one enabled item.
			if(invInvtryItem.getDisabledDt()!=null && countEnabled>1) continue;
			
			// set this as oldest.
			if(oldest==null) {
				oldest  = invInvtryItem;
				continue;
			}
			if(oldest.getAcsngDt()!=null && invInvtryItem.getAcsngDt()!=null && oldest.getAcsngDt().before(invInvtryItem.getAcsngDt())){
				oldest = invInvtryItem;
				continue;
			}
		}
		// enable oldest and 
		if(oldest!=null){
			Date date = new Date();
			for (InvInvtryItem invInvtryItem : resultList) {
				boolean update = false;
				if(oldest.getId()!=invInvtryItem.getId()){
					if(invInvtryItem.getDisabledDt()==null){
						invInvtryItem.setDisabledDt(date);
						update = true;
					}
					if(invInvtryItem.getConflictDt()!=null){
						invInvtryItem.setConflictDt(null);
						update = true;
					}
				}
				if(update){
					internalUpdate(invInvtryItem);
				}
			}
			boolean update = false;
			if(oldest.getDisabledDt()!=null){
				oldest.setDisabledDt(null);
				update = true;
			}
			if(oldest.getConflictDt()!=null){
				oldest.setConflictDt(null);
				update = true;
			}
			if(update){
				internalUpdate(oldest);
			}
		}
	}
	
	private void setConflicting(List<InvInvtryItem> items, Date conflictDt){
		for (InvInvtryItem invInvtryItem : items) {
			if(invInvtryItem.getConflictDt()==null) {
				invInvtryItem.setConflictDt(conflictDt);
				internalUpdate(invInvtryItem);
			}
		}
	}
}
