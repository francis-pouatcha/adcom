package org.adorsys.adsales.repo;

import org.adorsys.adsales.jpa.SlsRoleInSales;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity = SlsRoleInSales.class)
public interface SlsRoleInSalesRepository extends EntityRepository<SlsRoleInSales, String>
{
}
