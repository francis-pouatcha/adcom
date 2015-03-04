package org.adorsys.adprocmt.rest;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.metamodel.SingularAttribute;

import org.adorsys.adbase.security.SecurityUtil;
import org.adorsys.adprocmt.jpa.ProcmtPOTriggerMode;
import org.adorsys.adprocmt.repo.ProcmtPOTriggerModeRepository;
import org.apache.commons.lang3.StringUtils;

@Stateless
public class ProcmtPOTriggerModeEJB {
	@Inject
	private ProcmtPOTriggerModeRepository repository;
	
	@Inject
	private SecurityUtil securityUtil;


	public ProcmtPOTriggerMode create(ProcmtPOTriggerMode entity) {
		return repository.save(attach(entity));
	}

	public ProcmtPOTriggerMode deleteById(String id) {
		ProcmtPOTriggerMode entity = repository.findBy(id);
		if (entity != null) {
			repository.remove(entity);
		}
		return entity;
	}

	public ProcmtPOTriggerMode update(ProcmtPOTriggerMode entity) {
		return repository.save(attach(entity));
	}

	public ProcmtPOTriggerMode findById(String id) {
		return repository.findBy(id);
	}

	public List<ProcmtPOTriggerMode> listAll(int start, int max) {
		return processI18n(repository.findAll(start, max));
	}
	
	public List<ProcmtPOTriggerMode> listAll() {
		return processI18n(repository.findAll());
	}

	public Long count() {
		return repository.count();
	}

	public List<ProcmtPOTriggerMode> findBy(ProcmtPOTriggerMode entity, int start,
			int max, SingularAttribute<ProcmtPOTriggerMode, ?>[] attributes) {
		return processI18n(repository.findBy(entity, start, max, attributes));
	}

	public Long countBy(ProcmtPOTriggerMode entity,
			SingularAttribute<ProcmtPOTriggerMode, ?>[] attributes) {
		return repository.count(entity, attributes);
	}

	public List<ProcmtPOTriggerMode> findByLike(ProcmtPOTriggerMode entity, int start,
			int max, SingularAttribute<ProcmtPOTriggerMode, ?>[] attributes) {
		return processI18n(repository.findByLike(entity, start, max, attributes));
	}

	public Long countByLike(ProcmtPOTriggerMode entity,
			SingularAttribute<ProcmtPOTriggerMode, ?>[] attributes) {
		return repository.countLike(entity, attributes);
	}

	private ProcmtPOTriggerMode attach(ProcmtPOTriggerMode entity) {
		if (entity == null)
			return null;

		return entity;
	}
	
	public ProcmtPOTriggerMode findByIdentif(String identif) {
		return findById(identif);
	}
	
	
	private List<ProcmtPOTriggerMode> processI18n(List<ProcmtPOTriggerMode> procmtPOTriggerModes){
		String langIso2 = securityUtil.getUserLange();
		List<String> listLangIso2 = securityUtil.getUserLangePrefs();
		
		List<ProcmtPOTriggerMode> listTriggerI18n = new ArrayList<ProcmtPOTriggerMode>();
		for(ProcmtPOTriggerMode trigger:procmtPOTriggerModes){
			if(StringUtils.equals(langIso2,trigger.getLangIso2())){
				listTriggerI18n.add(trigger);
			}else{
				for (String lg : listLangIso2) {
					if (StringUtils.equals(lg, langIso2))
						continue;
					if(StringUtils.equals(lg,trigger.getLangIso2())){
						listTriggerI18n.add(trigger);
						break;
					}
				}
			}
		}
		return listTriggerI18n;
	}
}
