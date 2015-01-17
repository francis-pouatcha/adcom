package org.adorsys.adsales.repo;

import org.adorsys.adsales.jpa.SlsInvceStatus;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity = SlsInvceStatus.class)
public interface SlsInvceStatusRepository extends EntityRepository<SlsInvceStatus, String>
{
}
