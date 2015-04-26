package org.adorsys.adaptmt.rest;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.persistence.metamodel.SingularAttribute;

import org.adorsys.adaptmt.jpa.AptAptmt;
import org.adorsys.adaptmt.repo.AptAptmtRepository;
import org.adorsys.adaptmt.shedules.AptAptmtOngoingEvent;
import org.adorsys.adbase.jpa.Login;
import org.adorsys.adbase.security.SecurityUtil;
import org.adorsys.adcore.utils.SequenceGenerator;
import org.apache.commons.lang3.time.DateUtils;

@Stateless
public class AptAptmtEJB {

	@Inject
	private AptAptmtRepository repository;

	@Inject
	private SecurityUtil securityUtil;

	@Inject
	AptAptmtBsPtnrEJB aptAptmtBsPtnrEJB;

	@Inject
	@AptAptmtOngoingEvent
	private Event<AptAptmt> aptAptmtOngoinEvent;

	public AptAptmt create(AptAptmt entity) {

		Date now = new Date();
		if (checkAptAptmtDate(entity.getAppointmentDate(), now))
			entity.setCreateDate(now);
		else
			entity.setCreateDate(DateUtils.addWeeks(now, 1));

		entity.setAptmtnNbr(SequenceGenerator
				.getSequence(SequenceGenerator.APPOINTMENT_NUMBER_SEQUENCE_PREFIXE));

		Login login = securityUtil.getConnectedUser();
		entity.setCreatedUserId(login.getIdentif());

		AptAptmt aptAptmt = repository.save(attach(entity));
		aptAptmtOngoinEvent.fire(aptAptmt);

		return aptAptmt;
	}

	public AptAptmt deleteById(String id) {
		AptAptmt entity = repository.findBy(id);
		if (entity != null) {
			repository.remove(entity);
		}
		return entity;
	}

	public AptAptmt update(AptAptmt entity) {
		return repository.save(attach(entity));
	}

	public AptAptmt findById(String id) {
		return repository.findBy(id);
	}

	public List<AptAptmt> listAll(int start, int max) {
		return repository.findAll(start, max);
	}

	public Long count() {
		return repository.count();
	}

	public List<AptAptmt> findBy(AptAptmt entity, int start, int max,
			SingularAttribute<AptAptmt, ?>[] attributes) {
		return repository.findBy(entity, start, max, attributes);
	}

	public Long countBy(AptAptmt entity,
			SingularAttribute<AptAptmt, ?>[] attributes) {
		return repository.count(entity, attributes);
	}

	public List<AptAptmt> findByLike(AptAptmt entity, int start, int max,
			SingularAttribute<AptAptmt, ?>[] attributes) {
		return repository.findByLike(entity, start, max, attributes);
	}

	public Long countByLike(AptAptmt entity,
			SingularAttribute<AptAptmt, ?>[] attributes) {
		return repository.countLike(entity, attributes);
	}

	private AptAptmt attach(AptAptmt entity) {
		if (entity == null)
			return null;

		return entity;
	}

	public boolean checkAptAptmtDate(Date aptAptmtDate,
			Date aptAptmtCreationDate) {

		if (aptAptmtDate.before(aptAptmtCreationDate))
			return false;
		if (aptAptmtDate.equals(aptAptmtCreationDate))
			return false;

		return true;
	}

	public List<AptAptmt> findPreviousAptAptmt(String id) {
		return repository.findPreviousAptAptmt(id).maxResults(2)
				.getResultList();
	}

	public List<AptAptmt> findNextAptAptmt(String id) {
		return repository.findNextAptAptmt(id).maxResults(2).getResultList();
	}

	public void findAptmtBsPtnr(String aptmtIdentify) {

		// AptAptmtBsPtnr aptBsPtnr = aptAptmtBsPtnrEJB
		// .findAptmtBsPtnr(aptmtIdentify);
		/*
		 * List<BpBnsPtnr> bpBnsPtnrs = new ArrayList<BpBnsPtnr>();
		 * 
		 * for (AptAptmtBsPtnr aptbpBnsPtnr : aptBsPtnr) { String identif =
		 * aptbpBnsPtnr.getBsPtnrIdentify();
		 * bpBnsPtnrs.add(bpBnsPtnrEJB.findById(identif)); }
		 */
		// return aptBsPtnr;
	}

}
