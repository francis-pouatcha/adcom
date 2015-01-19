package org.adorsys.adcshdwr.repo;

import org.adorsys.adcshdwr.jpa.CdrDsArtItem;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity = CdrDsArtItem.class)
public interface CdrDsArtItemRepository extends EntityRepository<CdrDsArtItem, String>
{
}
