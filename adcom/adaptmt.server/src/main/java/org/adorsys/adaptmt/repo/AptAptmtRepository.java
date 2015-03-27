package org.adorsys.adaptmt.repo;

import org.adorsys.adaptmt.jpa.AptAptmt;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity = AptAptmt.class)
public interface AptAptmtRepository extends EntityRepository<AptAptmt, String>
{
}
