package org.adorsys.adaptmt.repo;

import org.adorsys.adaptmt.jpa.AptAptmtBsPtnr;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity = AptAptmtBsPtnr.class)
public interface AptAptmtBsPtnrRepository extends EntityRepository<AptAptmtBsPtnr, String>
{
}
