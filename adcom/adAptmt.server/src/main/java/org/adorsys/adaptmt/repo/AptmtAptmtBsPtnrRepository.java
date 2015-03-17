package org.adorsys.adaptmt.repo;

import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Modifying;
import org.apache.deltaspike.data.api.Query;
import org.apache.deltaspike.data.api.Repository;
import org.adorsys.adaptmt.jpa.AptmtAptmtBsPtnr;

@Repository(forEntity = AptmtAptmtBsPtnr.class)
public interface AptmtAptmtBsPtnrRepository extends EntityRepository<AptmtAptmtBsPtnr, String>
{
}
