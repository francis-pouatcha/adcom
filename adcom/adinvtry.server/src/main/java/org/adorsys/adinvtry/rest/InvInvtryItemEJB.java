package org.adorsys.adinvtry.rest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.metamodel.SingularAttribute;

import org.adorsys.adinvtry.jpa.InvInvtry;
import org.adorsys.adinvtry.jpa.InvInvtryGap;
import org.adorsys.adinvtry.jpa.InvInvtryItem;
import org.adorsys.adinvtry.jpa.InvInvtryItemEvtData;
import org.adorsys.adinvtry.jpa.InvInvtryItemList;
import org.adorsys.adinvtry.repo.InvInvtryItemRepository;
import org.apache.commons.lang3.StringUtils;

@Stateless
public class InvInvtryItemEJB
{

	@Inject
	private InvInvtryItemRepository repository;

	@Inject
	private InvInvtryItemEvtDataEJB itemEvtDataEJB;
	@Inject
	private InvInvtryItemHelperEJB itemHelperEJB;
	
	public InvInvtryItem create(InvInvtryItem entity)
	{
		String salIndex = entity.getSalIndex();
		List<InvInvtryItem> found = findBySalIndexForInvtrys(salIndex, Arrays.asList(entity.getInvtryNbr()));
		List<InvInvtryItem> compareList = new ArrayList<InvInvtryItem>();
		compareList.add(entity);
		compareList.addAll(found);
		Boolean sameQty = InvInvtryItemList.checkSameQty(compareList);
		Date now = new Date();
		if(!sameQty){
			entity.setConflictDt(now);
			for (InvInvtryItem item : found) {
				// Only save if conflict date was null.
				if(item.getConflictDt()==null){
					item.setConflictDt(now);
					internalUpdate(item);
				}
			}
		} else { // resolve conflict if any
			entity.setConflictDt(null);
			for (InvInvtryItem item : found) {
				// Only save if conflict date was null.
				if(item.getConflictDt()!=null){
					item.setConflictDt(null);
					internalUpdate(item);
				}
			}
		}

		InvInvtryItem invtryItem = repository.save(attach(entity));
		InvInvtryItemEvtData itemEvtData = new InvInvtryItemEvtData();
		invtryItem.copyTo(itemEvtData);
		itemEvtData.setIdentif(invtryItem.getIdentif());
		itemEvtDataEJB.create(itemEvtData);
		return invtryItem;
	}

	public InvInvtryItem deleteById(String id)
	{
		InvInvtryItem entity = repository.findBy(id);
		if (entity != null)
		{
			repository.remove(entity);
			itemEvtDataEJB.deleteById(id);
		}
		return entity;
	}

	public InvInvtryItem update(InvInvtryItem entity)
	{
		// Make sure there is a consistency, if not set conflicting date.
		// 1. Select all inventoru items of this inventory with the salIndex.
		String salIndex = entity.getSalIndex();
		List<InvInvtryItem> found = findBySalIndexForInvtrys(salIndex, Arrays.asList(entity.getInvtryNbr()));
		List<InvInvtryItem> compareList = new ArrayList<InvInvtryItem>();
		for (InvInvtryItem item : found) {
			if(StringUtils.equals(item.getId(), entity.getId())){
				compareList.add(entity);
			} else {
				compareList.add(item);
			}
		}
		// 2. Make sure all non disabled have a number.
		Boolean sameQty = InvInvtryItemList.checkSameQty(compareList);
		InvInvtryItem invtryItem = null;
		// If not all identical qty, set conflict.
		// Unless they are discarded.
		Date now = new Date();
		if(!sameQty){
			for (InvInvtryItem item : compareList) {
				if(StringUtils.equals(item.getId(), entity.getId())){
					if(item.getConflictDt()==null){
						item.setConflictDt(now);
					}
					invtryItem = internalUpdate(item);
				} else {
					// Only save if conflict date was null.
					if(item.getConflictDt()==null){
						item.setConflictDt(now);
						internalUpdate(item);
					}
				}
			}
		} else { // resolve conflict if any
			for (InvInvtryItem item : compareList) {
				if(StringUtils.equals(item.getId(), entity.getId())){
					if(item.getConflictDt()!=null){
						item.setConflictDt(null);
					}
					invtryItem = internalUpdate(item);
				} else {
					// Only save if conflict date was null.
					if(item.getConflictDt()!=null){
						item.setConflictDt(null);
						internalUpdate(item);
					}
				}
			}
		}
		return invtryItem;
	}
	
	private InvInvtryItem internalUpdate(InvInvtryItem invtryItem){
		invtryItem = repository.save(attach(invtryItem));
		InvInvtryItemEvtData itemEvtData = itemEvtDataEJB.findById(invtryItem.getId());
		invtryItem.copyTo(itemEvtData);
		itemEvtDataEJB.update(itemEvtData);
		return invtryItem;
	}

	public InvInvtryItem findById(String id)
	{
		return repository.findBy(id);
	}

	public List<InvInvtryItem> listAll(int start, int max)
	{
		return repository.findAll(start, max);
	}

	public Long count()
	{
		return repository.count();
	}

	public List<InvInvtryItem> findBy(InvInvtryItem entity, int start, int max, SingularAttribute<InvInvtryItem, ?>[] attributes)
	{
		return repository.findBy(entity, start, max, attributes);
	}

	public Long countBy(InvInvtryItem entity, SingularAttribute<InvInvtryItem, ?>[] attributes)
	{
		return repository.count(entity, attributes);
	}

	public List<InvInvtryItem> findByLike(InvInvtryItem entity, int start, int max, SingularAttribute<InvInvtryItem, ?>[] attributes)
	{
		return repository.findByLike(entity, start, max, attributes);
	}

	public Long countByLike(InvInvtryItem entity, SingularAttribute<InvInvtryItem, ?>[] attributes)
	{
		return repository.countLike(entity, attributes);
	}

	private InvInvtryItem attach(InvInvtryItem entity)
	{
		if (entity == null)
			return null;

		return entity;
	}
	public List<InvInvtryItem> findByInvtryNbr(String invtryNbr, int start, int max) {
		return repository.findByInvtryNbr(invtryNbr).firstResult(start).maxResults(max).getResultList();
	}

	public Long countByInvtryNbr(String invtryNbr) {
		return repository.countByInvtryNbr(invtryNbr);

	}
	
	public void removeByInvtryNbr(String invtryNbr) {
		repository.removeByInvtryNbr(invtryNbr);
	}
	
	public List<InvInvtryItem> findByInvtryNbrSorted(String invtryNbr, int start, int max) {
		return repository.findByInvtryNbr(invtryNbr).orderAsc("artName").firstResult(start).maxResults(max).getResultList();
	}

	public List<InvInvtryItem> findByInvtryNbrSortedBySectionAndArtName(String invtryNbr, int start, int max) {
		return repository.findByInvtryNbr(invtryNbr).orderAsc("section").orderAsc("artName").firstResult(start).maxResults(max).getResultList();
	}
	
	public List<InvInvtryItem> findByInvtryNbrAndSectionSorted(
			String invtryNbr, String section, int start, int max) {
		return repository.findByInvtryNbrAndSection(invtryNbr, section).orderAsc("artName").firstResult(start).maxResults(max).getResultList();
	}

	public Long countByInvtryNbrAndSection(String invtryNbr, String section) {
		return repository.countByInvtryNbrAndSection(invtryNbr, section);
	}

	public List<InvInvtryItem> findByInvtryNbrInRange(String invtryNbr,
			String rangeStart, String rangeEnd, int start, int max, boolean sorted) {
		if(StringUtils.isBlank(rangeStart)) rangeStart = "a";
		if(StringUtils.isBlank(rangeEnd)) rangeEnd = "z";
		if(sorted){
			return repository.findByInvtryNbrInRange(invtryNbr,rangeStart,rangeEnd).orderAsc("artName").firstResult(start).maxResults(max).getResultList();
		} else {
			return repository.findByInvtryNbrInRange(invtryNbr,rangeStart,rangeEnd).firstResult(start).maxResults(max).getResultList();
		}
	}

	public Long countByInvtryNbrInRange(String invtryNbr, String rangeStart,
			String rangeEnd) {
		if(StringUtils.isBlank(rangeStart)) rangeStart = "a";
		if(StringUtils.isBlank(rangeEnd)) rangeEnd = "z";
		return repository.countByInvtryNbrInRange(invtryNbr,rangeStart,rangeEnd);
	}

	public List<InvInvtryItem> findByInvtryNbrAndSectionInRange(
			String invtryNbr, String section, String rangeStart, String rangeEnd, int start,
			int max, boolean sorted) {
		if(sorted){
			return repository.findByInvtryNbrAndSection(invtryNbr, section, rangeStart, rangeEnd).orderAsc("artName").firstResult(start).maxResults(max).getResultList();
		} else {
			return repository.findByInvtryNbrAndSection(invtryNbr, section, rangeStart, rangeEnd).firstResult(start).maxResults(max).getResultList();
		}
	}

	public Long countByInvtryNbrAndSectionInRange(String invtryNbr, String section,
			String rangeStart, String rangeEnd) {
		return repository.countByInvtryNbrAndSection(invtryNbr, section, rangeStart, rangeEnd);
	}

	public InvInvtryItem findByIdentif(String identif) {
		List<InvInvtryItem> list = repository.findByIdentif(identif);
		if(list.isEmpty()) return null;
		return list.iterator().next();
	}
	
	public Long countSalIndexForInvtrys(List<String> invNbrs){
		return repository.salIndexForInvtrys(invNbrs).count();
	}  

	public List<String> findSalIndexForInvtrys(List<String> invNbrs, int first, int max){
		return repository.salIndexForInvtrys(invNbrs).firstResult(first).maxResults(max).orderAsc("salIndex").getResultList();
	}
	public List<InvInvtryItem> findBySalIndexForInvtrys(String salIndex, List<String> invNbrs){
		return repository.bySalIndexForInvtrys(salIndex, invNbrs).getResultList();
	}

	public Long countConflictingSalIndexForInvtrys(List<String> invNbrs){
		return repository.conflictingSalIndexForInvtrys(invNbrs).count();
	}  

	public List<String> findConflictingSalIndexForInvtrys(List<String> invNbrs, int first, int max){
		return repository.conflictingSalIndexForInvtrys(invNbrs).firstResult(first).maxResults(max).orderAsc("salIndex").getResultList();
	}

	public InvInvtryGap computeInvtryGap(String invtryNbr) {
		return repository.computeInvtryGap(invtryNbr).getSingleResult();
	}
		
	public InvInvtry validateInventory(InvInvtry invInvtry){
		String invtryNbr = invInvtry.getInvtryNbr();
		long count = repository.salIndexForInvtry(invtryNbr).count();
		int start = 0;
		int max = 100;

		while(start<=count){
			// @WARNIGN increase counter before request to avoid endless loop on error. 
			int firstResult = start;
			start +=max;
			List<String> resultList = repository.salIndexForInvtry(invtryNbr).firstResult(firstResult).maxResults(max).getResultList();
			for (String salIndex : resultList) {
				Long countEnabled = repository.countEnabled(salIndex, invtryNbr);
				if(countEnabled==1) continue;
				
				itemHelperEJB.makeConsistent(invtryNbr, salIndex);
			}
		}

		return invInvtry;
	}

	public InvInvtry recomputeInventory(InvInvtry invInvtry){

		String invtryNbr = invInvtry.getInvtryNbr();
		InvInvtryGap invtryGap = computeInvtryGap(invtryNbr);
		invInvtry.setGapPurchAmtHT(invtryGap.getGapPurchAmtHT());
		invInvtry.setGapSaleAmtHT(invInvtry.getGapSaleAmtHT());
		return invInvtry;
	}
	
	/**
	 * Returns true if the inventory object is consistent. Means that there is only one enabled item for each
	 * inventory item. An inventory can have many inventory items if counted by many employees.
	 * 
	 * @param invtryNbr
	 * @return true : if consistent.
	 */
	public Boolean checkConsistent(String invtryNbr){
		long count = repository.salIndexForInvtry(invtryNbr).count();
		int start = 0;
		int max = 100;
		while(start<=count){
			// @WARNIGN increase counter before request to avoid endless loop on error. 
			int firstResult = start;
			start +=max;
			List<String> resultList = repository.salIndexForInvtry(invtryNbr).firstResult(firstResult).maxResults(max).getResultList();
			for (String salIndex : resultList) {
				Long countEnabled = repository.countEnabled(salIndex, invtryNbr);
				if(countEnabled!=1) return false;
			}
		}
		return true;
	}
}
