package org.adorsys.adprocmt.rest;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.metamodel.SingularAttribute;

import org.adorsys.adcore.utils.SequenceGenerator;
import org.adorsys.adprocmt.jpa.PrcmtDelivery;
import org.adorsys.adprocmt.jpa.PrcmtDeliveryEvtData;
import org.adorsys.adprocmt.repo.PrcmtDeliveryRepository;
import org.apache.commons.lang3.StringUtils;

@Stateless
public class PrcmtDeliveryEJB {

	@Inject
	private PrcmtDeliveryRepository repository;

	@Inject
	private PrcmtDeliveryEvtDataEJB evtDataEJB;

	public PrcmtDelivery create(PrcmtDelivery entity) {
		if(StringUtils.isBlank(entity.getDlvryNbr())){
			entity.setDlvryNbr(SequenceGenerator.getSequence(SequenceGenerator.DELIVERY_SEQUENCE_PREFIXE));
		}
		entity.setId(entity.getDlvryNbr());
		PrcmtDeliveryEvtData evtData = new PrcmtDeliveryEvtData();
		entity = repository.save(attach(entity));
		entity.copyTo(evtData);
		evtData.setId(entity.getId());
		evtDataEJB.create(evtData);
		return entity;
	}

	public PrcmtDelivery deleteById(String id) {
		PrcmtDelivery entity = repository.findBy(id);
		if (entity != null) {
			repository.remove(entity);
		}
		evtDataEJB.deleteById(id);
		return entity;
	}

	public PrcmtDelivery update(PrcmtDelivery entity) {
		entity = repository.save(attach(entity));
		PrcmtDeliveryEvtData eventData = evtDataEJB.findById(entity.getId());
		if(eventData!=null){
			entity.copyTo(eventData);
			evtDataEJB.update(eventData);
		}
		return entity;
	}

	public PrcmtDelivery findById(String id) {
		return repository.findBy(id);
	}

	public List<PrcmtDelivery> listAll(int start, int max) {
		return repository.findAll(start, max);
	}

	public Long count() {
		return repository.count();
	}

	public List<PrcmtDelivery> findBy(PrcmtDelivery entity, int start, int max,
			SingularAttribute<PrcmtDelivery, ?>[] attributes) {
		return repository.findBy(entity, start, max, attributes);
	}

	public Long countBy(PrcmtDelivery entity,
			SingularAttribute<PrcmtDelivery, ?>[] attributes) {
		return repository.count(entity, attributes);
	}

	public List<PrcmtDelivery> findByLike(PrcmtDelivery entity, int start,
			int max, SingularAttribute<PrcmtDelivery, ?>[] attributes) {
		return repository.findByLike(entity, start, max, attributes);
	}

	public Long countByLike(PrcmtDelivery entity,
			SingularAttribute<PrcmtDelivery, ?>[] attributes) {
		return repository.countLike(entity, attributes);
	}

	private PrcmtDelivery attach(PrcmtDelivery entity) {
		if (entity == null)
			return null;

		return entity;
	}
}
