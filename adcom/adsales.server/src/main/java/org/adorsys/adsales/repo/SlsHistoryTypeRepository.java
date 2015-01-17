package org.adorsys.adsales.repo;

import org.adorsys.adsales.jpa.SlsHistoryType;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity = SlsHistoryType.class)
public interface SlsHistoryTypeRepository extends EntityRepository<SlsHistoryType, String>
{
}
