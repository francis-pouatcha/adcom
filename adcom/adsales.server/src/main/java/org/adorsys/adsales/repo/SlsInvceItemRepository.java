package org.adorsys.adsales.repo;

import org.adorsys.adsales.jpa.SlsInvceItem;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity = SlsInvceItem.class)
public interface SlsInvceItemRepository extends EntityRepository<SlsInvceItem, String>
{
}
