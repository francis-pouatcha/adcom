package org.adorsys.adcshdwr.repo;

import java.util.List;

import org.adorsys.adcshdwr.jpa.CdrDrctSalesEvt;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity = CdrDrctSalesEvt.class)
public interface CdrDrctSalesEvtRepository extends EntityRepository<CdrDrctSalesEvt, String>
{

	public List<CdrDrctSalesEvt> findByEvtName(String evtName);
}
