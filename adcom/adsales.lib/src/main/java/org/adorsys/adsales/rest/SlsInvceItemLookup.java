package org.adorsys.adsales.rest;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.metamodel.SingularAttribute;

import org.adorsys.adsales.jpa.SlsInvceItem;
import org.adorsys.adsales.repo.SlsInvceItemRepository;

@Stateless
public class SlsInvceItemLookup {

	@Inject
	private SlsInvceItemRepository repository;

	public SlsInvceItem findById(String id) {
		return repository.findBy(id);
	}

	public List<SlsInvceItem> listAll(int start, int max) {
		return repository.findAll(start, max);
	}

	public Long count() {
		return repository.count();
	}

	public List<SlsInvceItem> findBy(SlsInvceItem entity, int start, int max,
			SingularAttribute<SlsInvceItem, ?>[] attributes) {
		return repository.findBy(entity, start, max, attributes);
	}

	public Long countBy(SlsInvceItem entity,
			SingularAttribute<SlsInvceItem, ?>[] attributes) {
		return repository.count(entity, attributes);
	}

	public List<SlsInvceItem> findByLike(SlsInvceItem entity, int start,
			int max, SingularAttribute<SlsInvceItem, ?>[] attributes) {
		return repository.findByLike(entity, start, max, attributes);
	}

	public Long countByLike(SlsInvceItem entity,
			SingularAttribute<SlsInvceItem, ?>[] attributes) {
		return repository.countLike(entity, attributes);
	}

	public List<SlsInvceItem> findByInvceNbr(String invceNbr, int start, int max) {

		return repository.findByInvceNbr(invceNbr).firstResult(start)
				.maxResults(max).getResultList();
	}

	public List<SlsInvceItem> findByInvceNbr(String invceNbr) {

		return repository.findByInvceNbr(invceNbr).getResultList();
	}

	public Long countByInvceNbr(String invceNbr) {
		return repository.findByInvceNbr(invceNbr).count();
	}
}
