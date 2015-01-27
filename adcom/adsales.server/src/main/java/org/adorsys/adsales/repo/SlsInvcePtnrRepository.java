package org.adorsys.adsales.repo;

import org.adorsys.adsales.jpa.SlsInvcePtnr;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity = SlsInvcePtnr.class)
public interface SlsInvcePtnrRepository extends EntityRepository<SlsInvcePtnr, String>
{
}
