package org.adorsys.adsales.repo;

import org.adorsys.adsales.jpa.SlsSttlmtOp;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity = SlsSttlmtOp.class)
public interface SlsSttlmtOpRepository extends EntityRepository<SlsSttlmtOp, String>
{
}
