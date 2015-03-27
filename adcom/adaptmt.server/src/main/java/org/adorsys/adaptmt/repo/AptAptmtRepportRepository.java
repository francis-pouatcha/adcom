package org.adorsys.adaptmt.repo;

import org.adorsys.adaptmt.jpa.AptAptmtRepport;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity = AptAptmtRepport.class)
public interface AptAptmtRepportRepository extends EntityRepository<AptAptmtRepport, Long>
{
}
