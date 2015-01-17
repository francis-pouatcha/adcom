package org.adorsys.adsales.repo;

import org.adorsys.adsales.jpa.SlsSalesOrder;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity = SlsSalesOrder.class)
public interface SlsSalesOrderRepository extends EntityRepository<SlsSalesOrder, String>
{
}
