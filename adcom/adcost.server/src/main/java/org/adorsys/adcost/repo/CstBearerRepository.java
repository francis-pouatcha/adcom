package org.adorsys.adcost.repo;

import org.adorsys.adcost.jpa.CstBearer;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity = CstBearer.class)
public interface CstBearerRepository extends EntityRepository<CstBearer, String>
{
}
