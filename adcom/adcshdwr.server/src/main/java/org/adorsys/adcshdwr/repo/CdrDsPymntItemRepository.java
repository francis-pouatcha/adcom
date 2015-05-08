package org.adorsys.adcshdwr.repo;

import java.util.List;

import org.adorsys.adcshdwr.jpa.CdrDsPymntItem;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity = CdrDsPymntItem.class)
public interface CdrDsPymntItemRepository extends EntityRepository<CdrDsPymntItem, String>
{
	List<CdrDsPymntItem> findByDsNbr(String dsNbr);
}
