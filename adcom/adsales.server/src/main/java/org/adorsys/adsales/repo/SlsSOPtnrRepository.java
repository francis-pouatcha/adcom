package org.adorsys.adsales.repo;

import org.adorsys.adsales.jpa.SlsSOPtnr;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity = SlsSOPtnr.class)
public interface SlsSOPtnrRepository extends EntityRepository<SlsSOPtnr, String>
{
}
