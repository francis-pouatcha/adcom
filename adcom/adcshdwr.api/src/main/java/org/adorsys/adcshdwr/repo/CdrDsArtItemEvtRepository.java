package org.adorsys.adcshdwr.repo;

import org.adorsys.adcshdwr.jpa.CdrDsArtItemEvt;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.QueryResult;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity = CdrDsArtItemEvt.class)
public interface CdrDsArtItemEvtRepository extends EntityRepository<CdrDsArtItemEvt, String>
{
	public QueryResult<CdrDsArtItemEvt> findByDsNbr(String dsNbr);
}
