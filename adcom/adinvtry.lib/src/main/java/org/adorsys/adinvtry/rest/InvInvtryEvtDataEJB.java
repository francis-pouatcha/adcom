package org.adorsys.adinvtry.rest;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.metamodel.SingularAttribute;

import org.adorsys.adcore.jpa.StandardCstr;
import org.adorsys.adinvtry.jpa.InvInvtryEvtData;
import org.adorsys.adinvtry.jpa.InvInvtryEvtDataCstr;
import org.adorsys.adinvtry.repo.InvInvtryEvtDataCstrRepository;
import org.adorsys.adinvtry.repo.InvInvtryEvtDataRepository;
import org.apache.deltaspike.data.api.QueryResult;

@Stateless
public class InvInvtryEvtDataEJB {

	@Inject
	private InvInvtryEvtDataRepository repository;

	@Inject
	private InvInvtryEvtDataCstrRepository cstrRepo;

	public InvInvtryEvtData create(InvInvtryEvtData entity) {
		return repository.save(attach(entity));
	}

	public InvInvtryEvtData deleteById(String id) {
		InvInvtryEvtData entity = repository.findBy(id);
		if (entity == null)return null;

		// Create the deleted constraint so items can be deleted iteratively.
		List<InvInvtryEvtDataCstr> list = cstrRepo
				.findByEntIdentifAndCstrType(entity.getIdentif(),
						StandardCstr.DELETED.name()).maxResults(1)
				.getResultList();
		Date now = new Date();
		if (list.isEmpty()) {
			InvInvtryEvtDataCstr invtryEvtDataCstr = new InvInvtryEvtDataCstr();
			invtryEvtDataCstr.setCreationDt(now);
			invtryEvtDataCstr.setCstrDt(now);
			invtryEvtDataCstr.setCstrType(StandardCstr.DELETED.name());
			invtryEvtDataCstr.setCstrValue(entity.getIdentif());
			invtryEvtDataCstr.setEntIdentif(entity.getIdentif());
			invtryEvtDataCstr.makeId(true);
			cstrRepo.save(invtryEvtDataCstr);
		}
		repository.remove(entity);
		return entity;
	}

	public List<InvInvtryEvtDataCstr> listDeleted(Date cstrDt, int max) {
		return cstrRepo.findDeleted(StandardCstr.DELETED.name(), cstrDt)
				.maxResults(max).getResultList();
	}

	/**
	 * removeInvtryEvtDataItems.
	 *
	 * @param invtryEvtData
	 *            private void removeInvtryEvtDataItems(InvInvtryEvtData
	 *            invtryEvtData) {
	 *            Contract.checkIllegalArgumentException(invtryEvtData); String
	 *            invtryNbr = invtryEvtData.getInvtryNbr();
	 * 
	 *            List<InvInvtryItemEvtData> invtryItemEvtDatas =
	 *            itemEvtDataEJB.findByInvtryNbr(invtryNbr); for
	 *            (InvInvtryItemEvtData itemEvtData : invtryItemEvtDatas) {
	 *            itemEvtDataEJB.deleteById(itemEvtData.getId()); } }
	 */

	public InvInvtryEvtData update(InvInvtryEvtData entity) {
		return repository.save(attach(entity));
	}

	public InvInvtryEvtData findById(String id) {
		return repository.findBy(id);
	}

	public List<InvInvtryEvtData> listAll(int start, int max) {
		return repository.findAll(start, max);
	}

	public Long count() {
		return repository.count();
	}

	public List<InvInvtryEvtData> findBy(InvInvtryEvtData entity, int start,
			int max, SingularAttribute<InvInvtryEvtData, ?>[] attributes) {
		return repository.findBy(entity, start, max, attributes);
	}

	public Long countBy(InvInvtryEvtData entity,
			SingularAttribute<InvInvtryEvtData, ?>[] attributes) {
		return repository.count(entity, attributes);
	}

	public List<InvInvtryEvtData> findByLike(InvInvtryEvtData entity,
			int start, int max,
			SingularAttribute<InvInvtryEvtData, ?>[] attributes) {
		return repository.findByLike(entity, start, max, attributes);
	}

	public Long countByLike(InvInvtryEvtData entity,
			SingularAttribute<InvInvtryEvtData, ?>[] attributes) {
		return repository.countLike(entity, attributes);
	}

	public InvInvtryEvtData findByIdentif(String identif) {
		QueryResult<InvInvtryEvtData> queryResult = repository
				.findByIdentif(identif);
		List<InvInvtryEvtData> results = queryResult.maxResults(1)
				.getResultList();
		InvInvtryEvtData invInvtry = null;
		if (!results.isEmpty()) {
			invInvtry = results.iterator().next();
		}
		return invInvtry;
	}

	private InvInvtryEvtData attach(InvInvtryEvtData entity) {
		if (entity == null)
			return null;

		return entity;
	}

	public void deleteCstr(String id) {
		InvInvtryEvtDataCstr found = cstrRepo.findBy(id);
		if (found != null)
			cstrRepo.remove(found);
	}
}
