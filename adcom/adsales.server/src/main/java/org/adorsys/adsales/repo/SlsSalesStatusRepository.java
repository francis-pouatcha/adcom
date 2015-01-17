package org.adorsys.adsales.repo;

import org.adorsys.adsales.jpa.SlsSalesStatus;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity = SlsSalesStatus.class)
public interface SlsSalesStatusRepository extends EntityRepository<SlsSalesStatus, String>
{
}
