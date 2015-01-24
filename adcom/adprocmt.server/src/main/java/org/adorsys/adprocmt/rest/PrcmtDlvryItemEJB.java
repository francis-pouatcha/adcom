package org.adorsys.adprocmt.rest;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.metamodel.SingularAttribute;

import org.adorsys.adprocmt.jpa.PrcmtDlvryItem;
import org.adorsys.adprocmt.jpa.PrcmtDlvryItemEvtData;
import org.adorsys.adprocmt.repo.PrcmtDlvryItemRepository;

/**
 * Stores a delivery item. 
 * 
 * Whenever a delivery item is stored, a copy (event data) is also stored for event processing.
 * This copy is updated and deleted synchronously with the delivery item.
 * 
 * The event data object can also be deleted under certain condition independently of the delivery item object.
 * 
 * @author francis
 *
 */
@Stateless
public class PrcmtDlvryItemEJB {

	@Inject
	private PrcmtDlvryItemRepository repository;
	
	@Inject
	private PrcmtDlvryItemEvtDataEJB evtDataEJB;

	public PrcmtDlvryItem create(PrcmtDlvryItem entity) {
		PrcmtDlvryItemEvtData evtData = new PrcmtDlvryItemEvtData();
		entity = repository.save(attach(entity));
		entity.copyTo(evtData);
		evtData.setId(entity.getId());
		evtDataEJB.create(evtData);
		return entity;
	}

	public PrcmtDlvryItem deleteById(String id) {
		PrcmtDlvryItem entity = repository.findBy(id);
		if (entity != null) {
			repository.remove(entity);
		}
		evtDataEJB.deleteById(id);
		return entity;
	}

	public PrcmtDlvryItem update(PrcmtDlvryItem entity) {
		entity = repository.save(attach(entity));
		PrcmtDlvryItemEvtData eventData = evtDataEJB.findById(entity.getId());
		if(eventData!=null){
			entity.copyTo(eventData);
			evtDataEJB.update(eventData);
		}
		return entity;
	}

	public PrcmtDlvryItem findById(String id) {
		return repository.findBy(id);
	}

	public List<PrcmtDlvryItem> listAll(int start, int max) {
		return repository.findAll(start, max);
	}

	public Long count() {
		return repository.count();
	}

	public List<PrcmtDlvryItem> findBy(PrcmtDlvryItem entity, int start,
			int max, SingularAttribute<PrcmtDlvryItem, ?>[] attributes) {
		return repository.findBy(entity, start, max, attributes);
	}

	public Long countBy(PrcmtDlvryItem entity,
			SingularAttribute<PrcmtDlvryItem, ?>[] attributes) {
		return repository.count(entity, attributes);
	}

	public List<PrcmtDlvryItem> findByLike(PrcmtDlvryItem entity, int start,
			int max, SingularAttribute<PrcmtDlvryItem, ?>[] attributes) {
		return repository.findByLike(entity, start, max, attributes);
	}

	public Long countByLike(PrcmtDlvryItem entity,
			SingularAttribute<PrcmtDlvryItem, ?>[] attributes) {
		return repository.countLike(entity, attributes);
	}

	private PrcmtDlvryItem attach(PrcmtDlvryItem entity) {
		if (entity == null)
			return null;

		return entity;
	}
}
